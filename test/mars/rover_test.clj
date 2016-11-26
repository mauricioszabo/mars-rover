(ns mars.rover-test
  (:require [clojure.test :refer :all]
            [mars.rover :as rover]))

(deftest movement
  (testing "rover moves"
    (is (= [10 11 :n] (rover/move [10 10 :n] "M")))
    (is (= [10 9 :s] (rover/move [10 10 :s] "M")))
    (is (= [11 10 :e] (rover/move [10 10 :e] "M")))
    (is (= [9 10 :w] (rover/move [10 10 :w] "M"))))

  (testing "multiple movements"
    (is (= [10 0 :e] (rover/process-movements [0 0 :e] [20 20] "MMMMMMMMMM")))
    (is (= [5 5 :n] (rover/process-movements [0 0 :e] [20 20] "MMMMMLMMMMM")))))

(deftest rotation
  (testing "rover rotates"
    (is (= [10 10 :w] (rover/move [10 10 :n] "L")))
    (is (= [10 10 :e] (rover/move [10 10 :n] "R")))))

(deftest error-cases
  (testing "checks if rover is out of field"
    (is (= [:error :out-of-field 0 0 :s]
           (rover/process-movements [0 0 :s] [10 10] "M")))
    (is (= [:error :out-of-field 0 10 :n]
           (rover/process-movements [0 10 :n] [10 10] "M")))))
