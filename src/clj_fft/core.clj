(ns clj-fft.core
  (:use clj-fft.complex)
  (:use clojure.pprint)
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
  #_(println "This is just a library!")
  (pprint (fft [[1 2 3] [4 5 6] [7 8 9]]))
)
