(ns shouter.views.users
  (:require [shouter.views.layout :as layout]
            [hiccup.core :refer [h]]
            [hiccup.form :as form]))

(defn signup-form []
  [:div 
   [:h3 "Create account"]
   (form/form-to [:post "/users/create"]
                 [:div, {:class "form-group"}
                  (form/label "username" "Your username")
                  (form/text-field {:class "form-control"} "username")]
                 [:div, {:class "form-group"}
                  (form/label "password" "Your password")
                  (form/password-field {:class "form-control"} "password")]
                 [:div, {:class "form-group"}
                  (form/submit-button {:class "btn btn-primary"} "Sign up")])])

(defn signup []
  (layout/common "Sign up"
                 (signup-form)))
