(ns ^:figwheel-always outfolio.core
  (:require cljsjs.react
            [om.core :as om :include-macros true]
            [om.dom :as dom]
            [figwheel.client :as fw]

            [outfolio.demo :as demo]
            [outfolio.navigation :refer [navigation-view]]
            [outfolio.subnav-cards :refer [subnav-cards-view]]
            [outfolio.cards :refer [cards-view]]))

(enable-console-print!)

(defonce app-state (atom {:authenticated true
                          :user nil
                          :cards []
                          :card nil
                          :owner nil}))

(defonce init-data
  (let [user (demo/get-user)
        cards (demo/get-cards)]
    (swap! app-state assoc :user user)
    (swap! app-state assoc :cards cards)))

(om/root
  (fn [data owner]
    (om/component (dom/div
                    nil
                    (om/build navigation-view (:user data))
                    (om/build
                      subnav-cards-view
                      (select-keys data [:authenticated :owner :card]))
                    (om/build cards-view (:cards data)))))
  app-state
  {:target (. js/document (getElementById "app"))})

(fw/start)
