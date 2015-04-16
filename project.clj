(defproject outfolio "0.1.0-SNAPSHOT"
  :description "A small example Clojure and ClojureScript web app"
  :url "https://github.com/nicolashery/outfolio-clj"
  :license {:name "MIT"
            :url "http://opensource.org/licenses/MIT"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-3058"]
                 [com.novemberain/monger "2.1.0"]
                 [compojure "1.3.2"]
                 [hiccup "1.0.5"]
                 [http-kit "2.1.18"]
                 [ring/ring-defaults "0.1.4"]
                 [ring/ring-json "0.3.1"]
                 [figwheel "0.2.6"]
                 [cljsjs/react "0.13.1-0"]
                 [org.omcljs/om "0.8.8"]
                 [secretary "1.2.3"]
                 [org.clojure/tools.logging "0.3.1"]]

  :plugins [[lein-cljsbuild "1.0.5"]
            [lein-figwheel "0.2.6"]]

  :clean-targets ^{:protect false} ["resources/public/js/out"
                                    "resources/public/js/app.js"]

  :cljsbuild {:builds {:app {:source-paths ["src/cljs"]
                             :compiler {:main outfolio.core
                                        :asset-path "js/out"
                                        :output-to "resources/public/js/app.js"
                                        :output-dir "resources/public/js/out"
                                        :source-map true
                                        :source-map-timestamp true
                                        :cache-analysis true
                                        :optimizations :none
                                        :pretty-print true}}}}

  :main outfolio.core)
