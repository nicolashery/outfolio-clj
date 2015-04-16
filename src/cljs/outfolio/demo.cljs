(ns outfolio.demo)

; Demo data

(def demo-user
  {:_id "1"
   :name "Don Draper"
   :email "dondraper@example.com"})

(def demo-cards
  [{:_id "1"
    :name "The Dead Poet"
    :address "450 Amsterdam Ave (& 81st St)"
    :city "New York"
    :notes "Irish pub, small room, good beer selection, music jukebox"
    :owner {:_id "1" :name "Don Draper"}}
   {:_id "2"
    :name "Solas"
    :address "232 E 9th Street (& 2nd Ave)"
    :city "New York"
    :notes "Dancing bar, mainstream music, large room, usually not a long line to get in"
    :owner {:_id "1" :name "Don Draper"}}
   {:_id "3"
    :name "Sweet Leaf"
    :address "10-93 Jackson Avenue (& 49th Ave), Long Island City"
    :city "New York"
    :notes "Great little coffee shop in Long Island City, very good coffee, nice decor with leather seats & vinyl records"
    :owner {:_id "1" :name "Don Draper"}}
   {:_id "4"
    :name "Cloister Cafe"
    :address "238 East 9th Street (& 2nd Ave)"
    :city "New York"
    :notes "Hookah bar, nice decor & outside seating, great hookah, decent food too"
    :owner {:_id "1" :name "Don Draper"}}
   {:_id "5"
    :name "The 55 Bar"
    :address "55 Christopher Street (& 7th Ave)"
    :city "New York"
    :notes "Small jazz bar, 2 shows usually 7pm & 10pm, arrive early, good atmosphere"
    :owner {:_id "1" :name "Don Draper"}}
   {:_id "6"
    :name "Piccolo Cafe"
    :address "238 Madison Ave (& 37th St)"
    :city "New York"
    :notes "Small italian, great for lunch, good pasta & sandwiches, not expensive"
    :owner {:_id "1" :name "Don Draper"}}
   {:_id "7"
    :name "Dominie's Hoek"
    :address "48-17 Vernon Boulevard (& 49th Ave), Long Island City"
    :city "New York"
    :notes "Bar, outdoor sitting area, cheaper than Manhattan bars, kitchen with your usual bar food"
    :owner {:_id "1" :name "Don Draper"}}
   {:_id "8"
    :name "230 Fifth"
    :address "230 5th Ave (& 27th St)"
    :city "New York"
    :notes "Rooftop bar, amazing view of Empire State, a little expensive, arrive early for a good seat"
    :owner {:_id "1" :name "Don Draper"}}])

; Fake remote API

(defn get-user []
  demo-user)

(defn get-cards []
  demo-cards)

(defn get-card [card-id]
  (first (filter #(= card-id (:_id %)) demo-cards)))
