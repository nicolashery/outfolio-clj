(defproject outfolio "0.1.0-SNAPSHOT"
  :description "A small example Clojure and ClojureScript web app"
  :url "https://github.com/nicolashery/outfolio-clj"
  :license {:name "MIT"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [com.novemberain/monger "2.1.0"]
                 [compojure "1.3.2"]
                 [hiccup "1.0.5"]
                 [http-kit "2.1.18"]
                 [ring/ring-defaults "0.1.4"]
                 [ring/ring-json "0.3.1"]
                 [org.clojure/tools.logging "0.3.1"]]
  :main outfolio.core)
