(ns outfolio.subnav-cards
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom]

            [outfolio.routes :as r]))

(defn subnav-cards-view [data]
  (om/component
    (let [route-key (:route-key data)
          authenticated (:authenticated data)
          card-owner (if
                       (route-key #{:shared :shared-card})
                       (:owner data))
          card (if
                 (route-key #{:card :card-edit :card-share :shared-card})
                 (:card data))
          card-owner-id (:_id card-owner)
          card-id (:_id card)]
      (dom/div
        #js {:className "container"}
        (dom/div
          #js {:className "subnavbar group"}
          (dom/ul
            #js {:className "subnav"}
            (if authenticated
              (dom/li
                #js {:className (if (= route-key :cards) "active")}
                (dom/a #js {:href (r/cards-path)} "Cards")))
            (if card-owner
              (dom/li
                #js {:className (if (= route-key :shared) "active")}
                (dom/a
                  #js {:href (r/shared-path {:share-id card-owner-id})}
                  (:name card-owner)))))
          (apply dom/ul #js {:className "subnav subnav-right"}
                 (cond
                   (and authenticated (nil? card-owner) card)
                    [(dom/li
                       #js {:className (if (= route-key :card) "active")}
                       (dom/a
                         #js {:href (r/card-path {:id card-id})}
                         "Card"))
                     (dom/li
                       #js {:className (if (= route-key :card-edit) "active")}
                       (dom/a
                         #js {:href (r/card-edit-path {:id card-id})}
                         "Edit"))
                     (dom/li
                       #js {:className (if (= route-key :card-share) "active")}
                       (dom/a
                         #js {:href (r/card-share-path {:id card-id})}
                         "Share"))
                     (dom/li nil (dom/a
                                   #js {:href "#"} "Delete"))]
                    (and authenticated (nil? card-owner))
                     [(dom/li
                        #js {:className (if (= route-key :cards-new) "active")}
                        (dom/a #js {:href (r/cards-new-path)} "New"))
                      (dom/li
                        #js {:className (if (= route-key :cards-share) "active")}
                        (dom/a #js {:href (r/cards-share-path)} "Share"))
                      (dom/li nil (dom/a #js {:href "#"} "Refresh"))]
                    (and card-owner card)
                     [(dom/li
                        #js {:className (if (= route-key :shared-card) "active")}
                        (dom/a
                          #js {:href (r/shared-card-path
                                       {:share-id card-owner-id
                                        :card-id card-id})}
                          "Card"))]
                     :else
                      [(dom/li nil (dom/a #js {:href "#"} "Refresh"))])))))))
