(ns outfolio.api
  (:require [compojure.core :refer [defroutes GET]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.util.response :refer [response status]]

            [outfolio.model :as model]))

;; Fake auth middleware
(defn wrap-auth [handler]
  (fn [request]
    (handler (assoc request :user {:_id "1" :name "Don Draper"}))))

;; Standardize error response
(defn error-response [status-code error-name error-description]
  (status (response {:error {:name error-name
                     :description error-description}})
          status-code))

(defn get-cards [request]
  (let [user-id (get-in request [:user :_id])]
    (response (model/get-cards-for-user user-id))))

(defn get-card [request]
  (let [card-id (get-in request [:params :card-id])
        card (model/get-card card-id)]
    (if (nil? card)
      (error-response 404 "CardNotFound" "No matching card for that id")
      (response card))))

(defroutes routes-bare
  (GET "/api/cards" [] get-cards)
  (GET "/api/cards/:card-id" [] get-card))

(def routes
  (-> routes-bare
      (wrap-auth)
      (wrap-json-body)
      (wrap-json-response)))
