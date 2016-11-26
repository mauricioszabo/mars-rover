(ns mars.inputs-test
  (:require [clojure.test :refer :all]
            [mars.inputs :as inputs]))

(deftest parsing-data
  (testing "gets data to one rover"
    (is (= [[1 5 :n]] (inputs/parse "5 5\n1 2 N\nMMM")))
    (is (= [[:error :out-of-field [1 5 :n]]] (inputs/parse "5 5\n1 2 N\nMMMM"))))

  (testing "gets data to multiple rovers"
    (is (= [[1 5 :n] [2 4 :w]] (inputs/parse "5 5\n1 2 N\nMMM\n1 3 E\nMLML"))))

  (testing "checking for parse error"
    (is (= :invalid-format (inputs/parse "5 5 5")))))
