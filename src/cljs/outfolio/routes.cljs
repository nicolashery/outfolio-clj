(ns outfolio.routes
  (:require [secretary.core :as secretary :refer-macros [defroute]]
            [goog.events :as events]
            [goog.history.EventType :as EventType]

            [outfolio.state :as state]
            [outfolio.demo :as demo])
  (:import goog.History))

(defonce history (History.))

(defn navigate! [token]
  (let [h history
        ; Remove leading "#", if any
        token (clojure.string/replace-first token "#" "")]
    (.setToken h token)))

(defroute cards-path "/cards" []
  (state/put!
    :route-key :cards
    :cards (demo/get-cards)))

(defroute index-path "/" []
  (navigate! (cards-path)))

(defroute cards-new-path "/cards/new" []
  (state/put! :route-key :cards-new))

(defroute cards-share-path "/cards/share" []
  (state/put! :route-key :cards-share))

(defroute card-path "/card/:id" [id]
  (state/put!
    :route-key :card
    :card (demo/get-card id)))

(defroute card-edit-path "/card/:id/edit" [id]
  (state/put! :route-key :card-edit))

(defroute card-share-path "/card/:id/share" [id]
  (state/put! :route-key :card-share))

(defroute shared-path "/shared/:share-id" [share-id]
  (state/put! :route-key :shared))

(defroute shared-card-path "/shared/:share-id/card/:card-id"
  [share-id card-id]
  (state/put! :route-key :shared-card))

(defn hook-browser-navigation! []
  (let [h history
      f (fn [he] ;; goog.History.Event
          (let [token (.-token he)]
            (if (seq token) ;; preferred over (not (empty? token))
              (secretary/dispatch! token)
              (navigate! "/"))))]
  (events/listen h EventType/NAVIGATE f)
  (doto h (.setEnabled true))))

(defn init! []
  (secretary/set-config! :prefix "#")
  (hook-browser-navigation!))
