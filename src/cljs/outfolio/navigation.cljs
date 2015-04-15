(ns outfolio.navigation
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom]))

(defn navigation [user]
  (om/component
    (dom/div
      #js {:className "navbar"}
      (dom/div
        #js {:className "container"}
        (dom/div
          #js {:className "navbar-inner group"}
          (dom/div
            #js {:className "logo"}
            (dom/a #js {:href "#/home"} "Outfolio"))
          (dom/ul
            #js {:className "nav"}
            (dom/li
              nil
              (dom/a #js {:href "#/cards"} "Cards")))
          (dom/ul
            #js {:className "nav nav-right"}
            (dom/li nil (:name user))
            (dom/li
              nil
              (dom/a #js {:href "#/logout"} "Sign out"))))))))
