(ns outfolio.view
  (:require [hiccup.page :refer [html5]]
            [hiccup.core :refer [html]]))

(defn layout [title & body]
  (html5
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
    [:title title]]
   [:body body]))

(defn home []
  (layout
   "Outfolio"
   [:h1 "Oufolio"]
    [:p
      [:a {:href "#"} "Sign in with Google"]]))
