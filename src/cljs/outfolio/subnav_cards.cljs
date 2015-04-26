(ns outfolio.subnav-cards
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom]

            [outfolio.demo :as demo]
            [outfolio.routes :as r]))

(defn delete-card [card-id cards]
  (filterv #(not= (:_id %) card-id) cards))

(defn handle-delete-card [confirm? data owner e]
  (.preventDefault e)
  (if confirm?
    (let [{:keys [card cards]} data
          card-id (:_id card)
          success? (demo/delete-card card-id)]
      (om/update! card nil)
      (om/transact! cards [] (partial delete-card card-id))
      (r/navigate! (r/cards-path)))
    (om/set-state! owner :confirm? true)))

(defn delete-card-view [data owner]
  (reify
    om/IInitState
    (init-state [_]
      {:confirm? false})
    om/IWillReceiveProps
    (will-receive-props [_ next-props]
      (let [prev-props (om/get-props owner)
            changed-route? (not=
                             (:route-key next-props)
                             (:route-key prev-props))]
        (when changed-route?
          (om/set-state! owner :confirm? false))))
    om/IRenderState
    (render-state [_ {:keys [confirm?]}]
      (dom/a
        #js {:href "#" :onClick (partial handle-delete-card confirm? data owner)}
        (if confirm? "Delete?" "Delete")))))

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
                     (dom/li nil (om/build
                                   delete-card-view
                                   (select-keys data [:card :cards :route-key])))]
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
