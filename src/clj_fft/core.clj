(ns clj-fft.core
  (:use clj-fft.complex)
  (:use clojure.pprint)
  (:use clj-fft.matrix)
  (:import clj_fft.complex.Complex)
  (:use clj-fft.fft)
  (:import java.awt.image.BufferedImage)
  (:import javax.imageio.ImageIO)
  (:import java.io.File)
  (:import java.awt.Color)
  (:gen-class))

(defn -main
  "Fourier transforms!"
  [& args]
  (println "This is just a library")
)
