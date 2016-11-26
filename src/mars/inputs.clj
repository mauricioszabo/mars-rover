(ns mars.inputs
  (:require [clojure.string :as str]
            [mars.rover :as rover]
            [instaparse.core :as insta]))

(def ^:private grammar (insta/parser "
  start := board rover*
  board := #'\\d+' <' '> #'\\d+'
  rover := <'\\n'> initial <'\\n'> movements
  initial := #'\\d+' <' '> #'\\d+' <' '> #'[EWNS]'
  movements := #'[LRM]+'
"))

(def ^:private rewrite-rules
  {:board (fn [ & pos] (mapv #(Integer/parseInt %) pos))
   :rover (fn [[_ x y pos] [_ movements]]
            [[(Integer/parseInt x) (Integer/parseInt y) (-> pos str/lower-case keyword)]
             movements])})

(defn- move-rovers [[board & rovers]]
  (let [process (fn [[rover movements]] (rover/process-movements rover board movements))]
    (mapv process rovers)))

(defn parse [inputs]
  (let [parsed (->> inputs grammar (insta/transform rewrite-rules))]
    (if (insta/failure? parsed)
      :invalid-format
      (move-rovers (drop 1 parsed)))))
