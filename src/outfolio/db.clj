(ns outfolio.db
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:import org.bson.types.ObjectId))

(defonce db (let [uri (get (System/getenv) "MONGODB_URL" "mongodb://127.0.0.1/outfolio")
                  {:keys [conn db]} (mg/connect-via-uri uri)]
              db))

(defn to-object-id
  "Convert to ObjectId if possible, return nil or provided default otherwise"
  ([x] (ObjectId/massageToObjectId x))
  ([x default] (or (to-object-id x) default)))

(defn new-object-id! []
  (ObjectId.))

(defn card-object-id-to-string [card]
  "Convert ObjectIds in a card map to strings"
  (if (nil? card)
    nil
    (-> card
        (update-in [:_id] str)
        (update-in [:owner :_id] str))))

(defn get-cards-owned-by [user-id]
  (map card-object-id-to-string
       (mc/find-maps db "cards" {:owner {:_id (to-object-id user-id)}})))

(defn get-card [card-id]
  (card-object-id-to-string
   (mc/find-map-by-id db "cards" (to-object-id card-id ""))))

(defn create-card [card-attributes]
  (let [card (-> card-attributes
                 (assoc :_id (new-object-id!))
                 (update-in [:owner :_id] to-object-id))]
    (mc/insert db "cards" card)
    (card-object-id-to-string card)))
