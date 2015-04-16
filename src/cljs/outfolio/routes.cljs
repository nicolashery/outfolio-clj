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
  (state/assoc!
    :content-key :cards
    :cards (demo/get-cards)))

(defroute index-path "/" []
  (navigate! (cards-path)))

(defroute cards-new-path "/cards/new" []
  (println "cards-new-path"))

(defroute cards-share-path "/cards/share" []
  (println "cards-share-path"))

(defroute card-path "/card/:id" [id]
  (state/assoc!
    :content-key :card
    :card (demo/get-card id)))

(defroute card-edit-path "/card/:id/edit" [id]
  (println "card-edit-path"))

(defroute card-share-path "/card/:id/share" [id]
  (println "card-share-path"))

(defroute shared-path "/shared/:share-id" [share-id]
  (println "shared-path"))

(defroute shared-card-path "/shared/:share-id/card/:card-id"
  [share-id card-id]
  (println "shared-card-path"))

(defn hook-browser-navigation! []
  (let [h history
      f (fn [he] ;; goog.History.Event
          (.log js/console "navigate %o" (clj->js he))
          (let [token (.-token he)]
            (if (seq token) ;; preferred over (not (empty? token))
              (secretary/dispatch! token)
              (navigate! "/"))))]
  (events/listen h EventType/NAVIGATE f)
  (doto h (.setEnabled true))))

(defn init! []
  (secretary/set-config! :prefix "#")
  (hook-browser-navigation!))
