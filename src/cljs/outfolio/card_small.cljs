(ns outfolio.card-small
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom]))

(defn card-small-view [card]
  (om/component
    (dom/li nil
      (dom/div
        #js {:className "card card-small"}
        (dom/a
          #js {:href (str "#/card/" (:_id card)) :className "card-link"}
          (dom/div
            #js {:className "card-content"}
            (dom/div
              #js {:className "card-main"}
              (dom/div #js {:className "name"} (:name card))
              (dom/div #js {:className "address"} (:address card))
              (dom/div #js {:className "notes"} (:notes card)))
            (dom/div
              #js {:className "card-footer group"}
              (dom/div #js {:className "city"} (:city card)))))))))
