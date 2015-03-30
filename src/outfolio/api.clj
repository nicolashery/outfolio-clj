(ns outfolio.api
  (:require [compojure.core :refer [defroutes GET POST PUT DELETE]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.util.response :refer [response status]]

            [outfolio.db :as db]))

(status (response {}) 204)

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

(defn get-card [request]
  (let [card-id (get-in request [:params :card-id])
        card (db/get-card card-id)]
    (if (nil? card)
      (error-response 404 "CardNotFound" "No matching card for that id")
      (response card))))

(defn post-card [request]
  (let [owner (:user request)
        ;; TODO: some validation of request body
        card (-> (:body request)
                 (assoc :owner owner)
                 (db/create-card))]
    (response card)))

(defn put-card [request]
  (let [card-id (get-in request [:params :card-id])
        card-updates (:body request)
        card (db/get-card card-id)]
    (if (nil? card)
      (error-response 404 "CardNotFound" "No matching card for that id")
      (response (db/update-card card-id card-updates)))))

(defn delete-card [request]
  (let [card-id (get-in request [:params :card-id])
        card (db/get-card card-id)]
    (if (nil? card)
      (error-response 404 "CardNotFound" "No matching card for that id")
      (do
        (db/remove-card card-id)
        (status (response "") 204)))))

(defroutes routes-bare
  (GET "/api/cards" [] get-cards)
  (GET "/api/cards/:card-id" [] get-card)
  (POST "/api/cards" [] post-card)
  (PUT "/api/cards/:card-id" [] put-card)
  (DELETE "/api/cards/:card-id" [] delete-card))

(def routes
  (-> routes-bare
      (wrap-auth)
      (wrap-json-body)
      (wrap-json-response)))
