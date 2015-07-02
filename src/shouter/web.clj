(ns shouter.web
  (:require [compojure.core :refer [defroutes ANY]]
            [ring.adapter.jetty :as ring]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.middleware.reload :as reload]
            [cemerick.friend :as friend]
            [shouter.controllers.shouts :as shouts]
            [shouter.controllers.session :as session]
            [shouter.views.layout :as layout]
            [shouter.models.migration :as schema]
            [shouter.models.user :as user]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds]))
  (:gen-class))


(defroutes routes
  shouts/routes
  session/routes
  (friend/logout (ANY "/logout" request (ring.util.response/redirect "/"))) 
  (route/resources "/")
  (route/not-found (layout/four-oh-four)))

(def application 
  (handler/site
    (friend/authenticate 
      routes
      {:allow-anon? true
       :login-uri "/login"
       :default-landing-uri "/"
       :credential-fn (partial creds/bcrypt-credential-fn user/all)
       :workflows [(workflows/interactive-form)]})))

(def reloadable-application
  (reload/wrap-reload application))

(defn start [port]
  (ring/run-jetty reloadable-application {:port port
                                          :join? false}))

(defn -main []
  (schema/migrate)
  (let [port (Integer. (or (System/getenv "PORT") "8080"))]
    (start port)))
