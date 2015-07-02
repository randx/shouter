(ns shouter.views.shouts
  (:require [shouter.views.layout :as layout]
            [hiccup.core :refer [h]]
            [hiccup.form :as form]))

(defn shout-form []
  [:div 
   (form/form-to [:post "/"]
                 [:div, {:class "form-group"}
                  (form/label "shout" "What do you want to shout?")
                  (form/text-area {:class "form-control"} "shout")]
                 [:div, {:class "form-group"}
                  (form/submit-button {:class "btn btn-primary"} "SHOUT!")])])

(defn display-shouts [shouts]
  [:ul, {:class "shouts"}
   (map
    (fn [shout] [:li {:class "shout"} (h (:body shout))])
    shouts)])

(defn index [shouts]
  (layout/common "SHOUTER"
                 (shout-form)
                 [:hr]
                 [:h2 "Shouts"]
                 (display-shouts shouts)))
