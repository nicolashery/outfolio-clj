(ns outfolio.api
  (:require [compojure.core :refer [defroutes GET POST PUT DELETE]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.util.response :refer [response status]]

            [outfolio.db :as db]))

;; Fake auth middleware
(defn wrap-auth [handler]
  (fn [request]
    (handler (assoc request :user {:_id "5515a24e78309661a4aa7e0d" :name "Don Draper"}))))

;; Standardize error response
(defn error-response [status-code error-name error-description]
  (status (response {:error {:name error-name
                     :description error-description}})
          status-code))

(defn get-cards [request]
  (let [user-id (get-in request [:user :_id])]
    (response (db/get-cards-owned-by user-id))))

(defn post-card [request]
  (let [owner (:user request)
        ;; TODO: some validation of request body
        card (-> (:body request)
                 (assoc :owner owner)
                 (db/create-card))]
    (status (response card) 201)))

;; Middleware to get card from card-id in request params
;; and check ownership with current user
(defn wrap-card [handler]
  (fn [request]
    (let [user-id (get-in request [:user :_id])
          card-id (get-in request [:params :card-id])
          card (db/get-card card-id)
          card-owned-by-user? (= user-id (get-in card [:owner :_id]))
          card (if card-owned-by-user? card)]
      (if (nil? card)
        (error-response 404 "CardNotFound" "No matching card for that id")
        (handler (assoc request :card card))))))

(defn get-card [request]
  (response (:card request)))

(defn put-card [request]
  (let [card-id (get-in request [:card :_id])
        card-updates (:body request)]
    (response (db/update-card card-id card-updates))))

(defn delete-card [request]
  (let [card-id (get-in request [:card :_id])]
    (do
      (db/remove-card card-id)
      (status (response "") 204))))

(defroutes api-routes*
  (GET "/api/cards" [] get-cards)
  (POST "/api/cards" [] post-card)
  (GET "/api/cards/:card-id" [] (wrap-card get-card))
  (PUT "/api/cards/:card-id" [] (wrap-card put-card))
  (DELETE "/api/cards/:card-id" [] (wrap-card delete-card)))

(def api-routes
  (-> api-routes*
      (wrap-auth)
      (wrap-json-body)
      (wrap-json-response)))
