(ns outfolio.subnav-cards
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom]))

(defn subnav-cards-view [data]
  (om/component
    (let [authenticated (:authenticated data)
          card-owner (:owner data)
          card (:card data)]
      (dom/div
        #js {:className "container"}
        (dom/div
          #js {:className "subnavbar group"}
          (dom/ul
            #js {:className "subnav"}
            (if authenticated
              (dom/li nil (dom/a #js {:href "#/cards"} "Cards")))
            (if card-owner
              (dom/li nil (dom/a
                            #js {:href (str "#/shared/" (:_id card-owner))}
                            (:name card-owner)))))
          (apply dom/ul #js {:className "subnav subnav-right"}
                 (cond
                   (and authenticated (nil? card-owner) card)
                    [(dom/li nil (dom/a
                                   #js {:href (str "#/card" (:_id card))}
                                   "Card"))
                     (dom/li nil (dom/a
                                   #js {:href (str "#/card" (:_id card) "/edit")}
                                   "Edit"))
                     (dom/li nil (dom/a
                                   #js {:href (str "#/card" (:_id card) "/share")}
                                   "Share"))
                     (dom/li nil (dom/a
                                   #js {:href "#"} "Delete"))]
                    (and authenticated (nil? card-owner))
                     [(dom/li nil (dom/a #js {:href "#/cards/new"} "New"))
                      (dom/li nil (dom/a #js {:href "#/cards/share"} "Share"))
                      (dom/li nil (dom/a #js {:href "#"} "Refresh"))]
                    (and card-owner card)
                     [(dom/li nil (dom/a
                                    #js {:href (str
                                                 "#/shared/"
                                                 (:_id card-owner)
                                                 "/card/"
                                                 (:_id card))}
                                    "Card"))]
                     :else
                      [(dom/li nil (dom/a #js {:href "#"} "Refresh"))])))))))
