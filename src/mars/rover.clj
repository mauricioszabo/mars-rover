(ns mars.rover)

(defn- forward [rover]
  (case (last rover)
    :s (update rover 1 dec)
    :n (update rover 1 inc)
    :w (update rover 0 dec)
    :e (update rover 0 inc)))

(def ^:private lefts {:n :w, :w :s, :s :e, :e :n})
(def ^:private rights (->> lefts (map (comp vec reverse)) (into {})))
(defn- rotate [rover pos]
  (update rover 2
          #(if (= pos :l)
             (% lefts)
             (% rights))))

(defn move [rover cmd]
  (case cmd
    "M" (forward rover)
    "L" (rotate rover :l)
    "R" (rotate rover :r)
    [:error :invalid-command cmd]))

(defn- check-pos [[height width] rover]
  (let [[left bottom] rover]
    (if (or (neg? left) (neg? bottom)
            (> left height) (> bottom width))
      [:error :out-of-field]
      rover)))

(defn process-movements [rover field-size movements]
  (let [[success failure] (->> movements
                               (re-seq #".")
                               (reductions #(check-pos field-size (move %1 %2)) rover)
                               (split-with #(-> % first (not= :error))))
        last-rover-pos (last success)]
    (concat (first failure) last-rover-pos)))
