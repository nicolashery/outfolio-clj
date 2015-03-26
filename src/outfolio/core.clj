(ns outfolio.core
  (:require [clojure.tools.logging :refer [info]]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [org.httpkit.server :refer [run-server]]
            [ring.middleware.defaults :refer :all]))

(defroutes app-routes
  (GET "/" [] "Hello World!")
  (route/resources "/")
  (route/not-found "Not Found"))

(defn app []
  (wrap-defaults app-routes site-defaults))

(defn start [port]
  (run-server (app) {:port port}))

(defn -main []
  (let [port (Integer. (or (System/getenv "PORT") "8080"))]
    (start port)
    (info (str "Server listening on port " port))))
