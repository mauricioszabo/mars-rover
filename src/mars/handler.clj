(ns mars.handler
  (:require [mars.io :as io]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.adapter.jetty :as jetty])
  (:gen-class))

(defroutes app-routes
  (PUT "/move" {:keys [body]}
    (let [body (slurp body)]
      (-> body io/parse io/generate-output)))

  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes (dissoc api-defaults :params)))

(defn -main [& [port]]
  (let [port (Integer. (or port (System/getenv "PORT") 3001))]
    (jetty/run-jetty #'app {:port port :join? false})))
