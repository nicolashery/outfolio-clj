(ns outfolio.cards
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom]

            [outfolio.card-small :refer [card-small-view]]))

(defn cards-view [cards]
  (om/component
    (dom/div
      #js {:className "container"}
      (if (= 0 (count cards))
        (dom/div #js {:className "nocards"} "Click on 'New' to add new cards.")
        (dom/div
          #js {:className "cards-wrapper"}
          (apply dom/ol #js {:className "cards group"}
                 (om/build-all card-small-view cards)))))))
