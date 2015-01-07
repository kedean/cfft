(defproject cfft "0.1.0"
  :description "A pure clojure implementation of the forward and inverse discrete fourier transforms"
  :url "https://github.com/kedean/cfft"
  :license {:name "MIT License"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :main ^:skip-aot cfft.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
