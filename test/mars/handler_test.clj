(ns mars.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [mars.handler :as handler]))

(defn move-rover [movements]
  (-> (mock/request :put "/move")
      (mock/body movements)
      handler/app
      :body))

(deftest backend
  (testing "delegates movements to parser"
    (is (= "1 3 N\n5 1 E"
           (move-rover "5 5\n1 2 N\nLMLMLMLMM\n3 3 E\nMMRMMRMRRM"))))

  (testing "parser error is propagated"
    (is (= "INVALID-FORMAT" (move-rover "5 5\n1 2")))))
