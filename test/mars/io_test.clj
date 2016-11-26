(ns mars.io-test
  (:require [clojure.test :refer :all]
            [mars.io :as io]))

(deftest parsing-data
  (testing "gets data to one rover"
    (is (= [[1 5 :n]] (io/parse "5 5\n1 2 N\nMMM")))
    (is (= [[:error :out-of-field [1 5 :n]]] (io/parse "5 5\n1 2 N\nMMMM")))
    (is (= [[:error :out-of-field [1 0 :s]]] (io/parse "5 5\n1 2 N\nRRMMM"))))

  (testing "gets data to multiple rovers"
    (is (= [[1 5 :n] [2 4 :w]] (io/parse "5 5\n1 2 N\nMMM\n1 3 E\nMLML"))))

  (testing "checking for parse error"
    (is (= [[:invalid-format]] (io/parse "5 5 5")))))

(deftest output-generator
  (testing "generating output"
    (is (= "1 5 N\n3 3 S" (io/generate-output [[1 5 :n] [3 3 :s]])))
    (is (= "ERROR OUT-OF-FIELD 1 5 N\n3 3 S"
           (io/generate-output [[:error :out-of-field 1 5 :n] [3 3 :s]])))))
