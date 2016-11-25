(ns mars.rover)

(defn- forward [rover]
  (case (last rover)
    :n (update rover 1 dec)
    :s (update rover 1 inc)
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
