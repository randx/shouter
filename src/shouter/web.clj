(ns shouter.web
  (:require [compojure.core :refer [defroutes]]
            [ring.adapter.jetty :as ring]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.middleware.reload :as reload]
            [shouter.controllers.shouts :as shouts]
            [shouter.views.layout :as layout]
            [shouter.models.migration :as schema])
  (:gen-class))

(defroutes routes
  shouts/routes
  (route/resources "/")
  (route/not-found (layout/four-oh-four)))

(def application 
  (handler/site routes))

(def reloadable-application
  (reload/wrap-reload application))

(defn start [port]
  (ring/run-jetty reloadable-application {:port port
                                          :join? false}))

(defn -main []
  (schema/migrate)
  (let [port (Integer. (or (System/getenv "PORT") "8080"))]
    (start port)))
