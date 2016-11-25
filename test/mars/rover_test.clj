(ns mars.rover-test
  (:require [clojure.test :refer :all]
            [mars.rover :as rover]))

(deftest movement
  (testing "rover moves"
    (is (= [10 9 :n] (rover/move [10 10 :n] "M")))
    (is (= [10 11 :s] (rover/move [10 10 :s] "M")))
    (is (= [11 10 :e] (rover/move [10 10 :e] "M")))
    (is (= [9 10 :w] (rover/move [10 10 :w] "M")))))

(deftest rotation
  (testing "rover rotates"
    (is (= [10 10 :w] (rover/move [10 10 :n] "L")))
    (is (= [10 10 :e] (rover/move [10 10 :n] "R")))))
