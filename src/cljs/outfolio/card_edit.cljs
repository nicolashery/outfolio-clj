(ns outfolio.card-edit
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom]

            [outfolio.demo :as demo]
            [outfolio.routes :as r]))

(defn form-values [owner]
  {:name (.-value (om/get-node owner "name"))
   :address (.-value (om/get-node owner "address"))
   :city (.-value (om/get-node owner "city"))
   :notes (.-value (om/get-node owner "notes"))})

(defn update-cards [updated-card cards]
  (mapv
   (fn [card]
     (if (= (:_id card) (:_id updated-card))
       updated-card
       card))
   cards))

(defn save-card! [data card-attributes]
  (let [{:keys [cards card]} data
        card-id (:_id card)
        updated-card (demo/update-card card-id card-attributes)]
    (om/update! card updated-card)
    (om/transact! cards [] (partial update-cards updated-card))))

(defn navigate-back! [card]
  (r/navigate! (r/card-path {:id (:_id card)})))

(defn handle-save [data owner e]
  (.preventDefault e)
  (let [card-attributes (form-values owner)]
    (save-card! data card-attributes)
    (navigate-back! (:card data))))

(defn handle-cancel [data owner e]
  (.preventDefault e)
  (navigate-back! (:card data)))

(defn card-edit-view [data owner]
  (let [card (:card data)]
    (om/component
      (dom/div
        #js {:className "card card-single card-edit"}
        (dom/div
          #js {:className "card-content"}
          (dom/form
            nil
            (dom/label nil "Name")
            (dom/input
              #js {:ref "name" :type "text" :defaultValue (:name card)})
            (dom/label nil "Address")
            (dom/input
              #js {:ref "address" :type "text" :defaultValue (:address card)})
            (dom/label nil "City")
            (dom/input
              #js {:ref "city" :type "text" :defaultValue (:city card)})
            (dom/label nil "Notes")
            (dom/textarea
              #js {:ref "notes" :defaultValue (:notes card)})
            (dom/div
              #js {:className "form-actions-inline"}
              (dom/button
                #js {:className "btn"
                     :onClick (partial handle-save data owner)}
                "Save")
              (dom/button
                #js {:className "btn"
                     :onClick (partial handle-cancel data owner)}
                "Cancel"))))))))
