(ns user
  (:require [mars.handler :as handler]))

(defn start-system []
  (let [server (handler/-main)]
    (defn stop-system []
      (.stop server))
    nil))
