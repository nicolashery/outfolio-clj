(ns ^:figwheel-always outfolio.core
  (:require cljsjs.react
            [om.core :as om :include-macros true]
            [om.dom :as dom]
            [figwheel.client :as fw]

            [outfolio.state :as state]
            [outfolio.routes :as routes]
            [outfolio.demo :as demo]
            [outfolio.navigation :refer [navigation-view]]
            [outfolio.subnav-cards :refer [subnav-cards-view]]
            [outfolio.content :refer [content-view]]))

(enable-console-print!)

(defonce init
  (do
    (let [user (demo/get-user)
          cards (demo/get-cards)]
      (state/assoc! :user user :cards cards))
    (routes/init!)))

(om/root
  (fn [data owner]
    (om/component (dom/div
                    nil
                    (om/build
                      navigation-view
                      (select-keys data [:route-key :user]))
                    (om/build
                      subnav-cards-view
                      (select-keys data
                                   [:route-key :authenticated :owner :card]))
                    (om/build content-view data))))
  state/app-state
  {:target (. js/document (getElementById "app"))})

(fw/start)
