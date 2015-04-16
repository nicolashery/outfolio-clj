(ns outfolio.card
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom]))

(defn card [card]
  (om/component
    (dom/div
      #js {:className "card card-single card-show"}
      (dom/div
        #js {:className "card-content"}
        (dom/div
          #js {:className "card-main"}
          (dom/div #js {:className "name"} (:name card))
          (dom/div #js {:className "address"} (:address card))
          (dom/div #js {:className "notes"} (:notes card)))
        (dom/div
          #js {:className "card-footer group"}
          (dom/div #js {:className "city"} (:city card)))))))
