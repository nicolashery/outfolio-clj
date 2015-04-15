(ns ^:figwheel-always outfolio.core
  (:require cljsjs.react
            [om.core :as om :include-macros true]
            [om.dom :as dom]
            [figwheel.client :as fw]))

(enable-console-print!)

(defonce app-state (atom {:clicks 0}))

(om/root
  (fn [data owner]
    (om/component (dom/h1 nil "Outfolio")))
  app-state
  {:target (. js/document (getElementById "app"))})

(fw/start)
