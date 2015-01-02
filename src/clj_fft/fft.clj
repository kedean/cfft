(ns clj-fft.fft
  (:use clj-fft.complex)
  (:use clj-fft.matrix)
  (:import clj_fft.complex.Complex)
  (:import java.lang.Math)
  )

(defn euler-expt [n]
  "Computes the euler exponent of n as a complex number"
  (Complex. (Math/cos n) (Math/sin n)))

;Exists only as an optimized version of the euler-expt function
(defn euler-expt-and-mult [n v]
  "Computes the euler exponent of n and multiplies the resulting complex by the real number v"
  (let [left (Math/cos n) right (Math/sin n)]
      (Complex. (* left v) (* right v))))

;Stored for the performance benefits
(def pos2pi (* 2.0 Math/PI))
(def neg2pi (* -2.0 Math/PI))

;Helpers to perform summations
(defn sum [l] (reduce + l))
(defn complex-sum [l] (reduce complex-add l))

(defn fft-nd-sample [matIndices mat el counters limits]
  "Calculates part of the sum of one sample."
  (complex-mult (euler-expt
    (*
      neg2pi
      (sum
        (map
          (fn [m k M] (/ (* m k) M))
          counters matIndices limits))))
    el))

(defn ifft-nd-sample [matIndices mat el counters limits]
  "Calculates part of the sum of one inverse sample."
  (complex-mult
    (complex-mult (euler-expt
        (*
          pos2pi
          (sum
            (map
              (fn [m k M] (/ (* m k) M))
              counters matIndices limits))))
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
