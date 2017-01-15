(defproject cfft "0.1.1"
  :description "A pure clojure implementation of the forward and inverse discrete fourier transforms"
  :url "https://github.com/kedean/cfft"
  :license {:name "MIT License"}
  :dependencies [[org.clojure/clojure "1.8.0"], [criterium "0.4.4"]]
  :main ^:skip-aot cfft.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
