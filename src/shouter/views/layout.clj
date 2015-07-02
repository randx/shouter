(ns shouter.views.layout
  (:require [hiccup.page :as h]
            [cemerick.friend :as friend]))

(defn common [title & body]
  (h/html5
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
    [:meta {:name "viewport" :content
            "width=device-width, initial-scale=1, maximum-scale=1"}]
    [:title title]
    (h/include-css "/stylesheets/bootstrap.min.css"
                 "/stylesheets/application.css")]
   [:body
    [:header {:class "navbar navbar-default navbar-static-top"}
     [:div {:class "navbar-header"}
      [:a {:class "navbar-brand" :href "/"} "Shout"]]
     (if (friend/current-authentication)
       [:ul {:class "nav navbar-nav navbar-right"}
        [:li
         [:a {:class "username" :href "#"} (get (friend/current-authentication) :username "anonymous")]]
        [:li
         [:a {:class "login" :href "/logout"} "Logout"]]]
       [:ul {:class "nav navbar-nav navbar-right"}
        [:li
         [:a {:class "login" :href "/login"} "Login"]]])]
    [:main {:id "content"} 
     [:div {:class "container"} body]]]))

(defn four-oh-four []
  (common "Page Not Found"
          [:div {:id "four-oh-four"}
           "The page you requested could not be found"]))
