(ns cfft.core
  (:use cfft.complex)
  (:use cfft.matrix)
  (:import java.lang.Math)
  )

(defn euler-expt [n]
  "Computes the euler exponent of n as a complex number"
  (complex (Math/cos n) (Math/sin n)))

;Stored for the performance benefits
(def pos2pi (* 2.0 Math/PI))
(def neg2pi (* -2.0 Math/PI))

;Helpers to perform summations
(defn sum [l] (reduce + l))
(defn complex-sum [l] (reduce complex-add l))

(defn fft-sample-mapper [m k M] (/ (* m k) M))

(defn fft-nd-sample [matIndices mat el counters limits]
  "Calculates part of the sum of one sample."
  (complex-mult (euler-expt
    (*
      neg2pi
      (sum
        (map fft-sample-mapper counters matIndices limits))))
    el))

(defn ifft-nd-sample [matIndices mat el counters limits]
  "Calculates part of the sum of one inverse sample."
  (complex-mult
    (complex-mult (euler-expt
        (*
          pos2pi
          (sum
            (map fft-sample-mapper counters matIndices limits))))
        el)
    (reduce * (map (fn [n] (/ 1 n)) limits)))
    )

;Functions to be used publically by other files
(defn fft [m]
  "Computes the fourier transform of an n-dimensional matrix m"
  (matrix-apply
    (fn [mat el indices]
      (matrix-summation complex-add (partial fft-nd-sample indices) mat))
    m))

(defn ifft [m]
  "Computes the inverse fourier transform of an n-dimensional matrix m"
  (matrix-apply
    (fn [mat el indices]
      (matrix-summation complex-add (partial ifft-nd-sample indices) mat))
    m))
