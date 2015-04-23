(ns outfolio.cards-new
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom]

            [outfolio.demo :as demo]
            [outfolio.routes :as r]))

(defn form-values [owner]
  {:_id "123"
   :name (.-value (om/get-node owner "name"))
   :address (.-value (om/get-node owner "address"))
   :city (.-value (om/get-node owner "city"))
   :notes (.-value (om/get-node owner "notes"))})

(defn add-card! [cards card-attributes]
  (let [new-card (demo/create-card card-attributes)]
    (om/transact! cards #(conj % new-card))
    (r/navigate! (r/cards-path))))

(defn clear-form! [owner]
  (set! (.-value (om/get-node owner "name")) "")
  (set! (.-value (om/get-node owner "address")) "")
  (set! (.-value (om/get-node owner "city")) "")
  (set! (.-value (om/get-node owner "notes")) ""))

(defn handle-add [cards owner e]
  (.preventDefault e)
  (let [card-attributes (form-values owner)]
    (add-card! cards card-attributes)
    (clear-form! owner)))

(defn handle-cancel [owner e]
  (.preventDefault e)
  (clear-form! owner)
  (r/navigate! (r/cards-path)))

(defn cards-new-view [cards owner]
  (om/component
    (dom/div
      #js {:className "card card-single card-new"}
      (dom/div
        #js {:className "card-content"}
        (dom/form
          nil
          (dom/label nil "Name")
          (dom/input
            #js {:ref "name" :type "text" :placeholder "ex: The Dead Poet"})
          (dom/label nil "Address")
          (dom/input
            #js {:ref "address" :type "text" :placeholder "ex: 450 Amsterdam Ave (& 81st St)"})
          (dom/label nil "City")
          (dom/input
            #js {:ref "city" :type "text" :placeholder "ex: New York"})
          (dom/label nil "Notes")
          (dom/textarea
            #js {:ref "notes" :placeholder "ex: Irish pub, small room, good beer, nice decoration, jukebox"})
          (dom/div
            #js {:className "form-actions-inline"}
            (dom/button
              #js {:className "btn"
                   :onClick (partial handle-add cards owner)}
              "Add")
            (dom/button
              #js {:className "btn"
                   :onClick (partial handle-cancel owner)}
              "Cancel")))))))
