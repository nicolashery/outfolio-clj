(ns outfolio.navigation
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom]

            [outfolio.routes :as r]))

(defn navigation-view [{:keys [route-key user]}]
  (om/component
    (dom/div
      #js {:className "navbar"}
      (dom/div
        #js {:className "container"}
        (dom/div
          #js {:className "navbar-inner group"}
          (dom/div
            #js {:className "logo"}
            (dom/a #js {:href (r/index-path)} "Outfolio"))
          (dom/ul
            #js {:className "nav"}
            (dom/li
              #js {:className (if (= route-key :cards) "active")}
              (dom/a #js {:href (r/cards-path)} "Cards")))
          (dom/ul
            #js {:className "nav nav-right"}
            (dom/li nil (:name user))
            (dom/li
              nil
              (dom/a #js {:href "#"} "Sign out"))))))))
