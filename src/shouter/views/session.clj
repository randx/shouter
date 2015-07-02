(ns shouter.views.session
  (:require [shouter.views.layout :as layout]
            [hiccup.core :refer [h]]
            [hiccup.form :as form]))

(defn login-form []
  [:div 
   [:h3 "Login"]
   (form/form-to [:post "/login"]
                 [:div, {:class "form-group"}
                  (form/label "username" "Your username")
                  (form/text-field {:class "form-control"} "username")]
                 [:div, {:class "form-group"}
                  (form/label "password" "Your password")
                  (form/password-field {:class "form-control"} "password")]
                 [:div, {:class "form-group"}
                  (form/submit-button {:class "btn btn-primary"} "Login")])])

(defn login []
  (layout/common "Login"
                 (login-form)))
