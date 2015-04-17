(ns outfolio.content
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom]

            [outfolio.cards :refer [cards-view]]
            [outfolio.card :refer [card-view]]))

(defn content-view [data]
  (om/component
    (case (:route-key data)
      :cards (om/build cards-view (:cards data))
      :card (om/build card-view (:card data))
      (dom/div nil "Not found"))))
