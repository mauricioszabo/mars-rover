(ns mars.io
  (:require [clojure.string :as str]
            [mars.rover :as rover]
            [instaparse.core :as insta]))

(def ^:private grammar (insta/parser "
  start := board rover*  <('\\n' trailing-space)?>
  board := #'\\d+' <' '> #'\\d+' <trailing-space>
  rover := <'\\n'> initial <trailing-space> <'\\n'> movements <trailing-space>
  initial := #'\\d+' <' '> #'\\d+' <' '> #'[EWNS]'
  movements := #'[LRM]+'
  trailing-space := #' *'
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
      [[:invalid-format]]
      (move-rovers (drop 1 parsed)))))

(defn- rover->str [rover]
  (let [internal->str #(cond-> %
                               (keyword? %) (-> name str/upper-case))]
    (->> rover (map internal->str) (str/join " "))))

(defn generate-output [rovers]
  (->> rovers (map rover->str) (str/join "\n")))
