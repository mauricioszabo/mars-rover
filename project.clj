(defproject mars "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :min-lein-version "2.7.1"

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [ring/ring-defaults "0.2.1"]
                 [ring/ring-jetty-adapter "1.2.1"]
                 [instaparse "1.4.3"]]

  :source-paths ["src"]
  :uberjar-name "mars-standalone.jar"

  :profiles {:dev {:source-paths ["dev"]
                   :dependencies [[ring/ring-mock "0.3.0"]]}
             :uberjar {:aot :all}}
  :main mars.handler)
