(ns outfolio.model)

;; Simple in-memory "database" for now
(def db (atom {:users {}
               :cards {}}))

;; Initial sample data
(reset! db {
  :users {"1" {:_id "1"
               :name "Don Draper"}}
  :cards {"1" {:_id "1"
               :name "The Dead Poet"
               :address "450 Amsterdam Ave (& 81st St)"
               :city "New York"
               :notes "Irish pub, small room, good beer selection, music jukebox"
               :owner {:_id "1" :name "Don Draper"}}
          "2" {:_id "2"
               :name "Solas"
               :address "232 E 9th Street (& 2nd Ave)"
               :city "New York"
               :notes "Dancing bar, mainstream music, large room, usually not a long line to get in"
               :owner {:_id "1" :name "Don Draper"}}}})


(defn card-owned-by? [user-id]
  (fn [card]
    (= user-id (get-in card [:owner :_id]))))

(defn get-cards-for-user [user-id]
  (->> (:cards @db)
       (vals)
       (filter (card-owned-by? user-id))
       (vec)))

(defn get-card [card-id]
  (get (:cards @db) card-id))
