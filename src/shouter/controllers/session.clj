(ns shouter.controllers.session
  (:require [compojure.core :refer [defroutes GET POST]]
            [clojure.string :as str]
            [ring.util.response :as ring]
            [shouter.views.session :as view]))

(defn login []
  (view/login))

(defroutes routes
  (GET  "/login" [] (login)))
