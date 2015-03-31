(ns outfolio.core
  (:require [clojure.tools.logging :refer [info]]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [org.httpkit.server :refer [run-server]]
            [ring.middleware.defaults :refer :all]

            [outfolio.api :as api]))

(defroutes app-routes
  api/api-routes
  (GET "/" [] "Hello World!")
  (route/resources "/")
  (route/not-found "Not Found"))

(def app-defaults
  ;; Allow CORS for POST requests, security handled by auth token in request
  (assoc-in site-defaults [:security :anti-forgery] false))

(defn app []
  (wrap-defaults app-routes app-defaults))

(defn start [port]
  (run-server (app) {:port port}))

(defn -main []
  (let [port (Integer. (or (System/getenv "PORT") "8080"))]
    (start port)
    (info (str "Server listening on port " port))))
