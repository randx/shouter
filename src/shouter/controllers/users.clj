(ns shouter.controllers.users
  (:require [compojure.core :refer [defroutes GET POST]]
            [clojure.string :as str]
            [ring.util.response :as ring]
            [cemerick.friend :as friend]
            [cemerick.friend.credentials :as creds]
            [shouter.views.users :as view]
            [shouter.models.user :as model]))


(defn create-user [username password]
  username {:username username
            :password (creds/hash-bcrypt password)
            :roles #{::user}})

(defn singup []
  (view/signup))

(defn create [username password]
  (if (not-any? str/blank? [username password])
    (let [user (create-user username password)]
      (model/create username password)
      (friend/merge-authentication
        (ring/redirect "/")
        user))))

(defroutes routes
  (GET  "/signup" [] (singup))
  (POST "/users/create" [username password] 
        (create username password)))
