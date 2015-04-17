(ns outfolio.state)

(defonce app-state (atom {:authenticated true
                          :user nil
                          :cards []
                          :card nil
                          :owner nil
                          ; UI state
                          :route-key nil}))

(defn assoc! [& args]
  (apply swap! app-state assoc args))
