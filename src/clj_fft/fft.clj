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
(def neg2pi (* -2.0 Math/PI))

;Helpers to perform summations
(defn sum [l] (reduce + l))
(defn complex-sum [l] (reduce add l))

(defn fft-1d [^clojure.lang.ISeq m]
  "Computes the fourier transform of a 1-dimensional list l"
  (let [
          N (count m)
          neg2piN (/ neg2pi N)]
    (map
      (fn fft-sample-1d [k]
        "Computes the 1d fourier transform of a single sample"
        (let [neg2pi_kN (* k neg2piN)]
        (reduce add
          (map-indexed
            (fn [i v]
              (euler-expt-and-mult
                (* neg2pi_kN i)
                v))
            m))))
      (range N))))

(defn fft-nd-sample [kVals MVals el sumIndices]
  "Calculates part of the sum of one sample with sample indices k, at the summation indices in sumIndices and with summation lengths in MVals, "
  (euler-expt-and-mult
    (*
      neg2pi
      (sum
        (map
          (fn [m k M] (/ (* m k) M))
          sumIndices kVals MVals)))
    el))

(defn fft-nd-sample-map [l kVals MVals item indices]
  "Recursively calculates the sample for the indices in kVals"
  (if
    (coll? item)
    (let [newMVals (cons (count item) MVals)]
      (complex-sum
        (map-indexed
          (fn [i el] (fft-nd-sample-map l kVals newMVals el (cons i indices)))
          item)))
    (fft-nd-sample kVals MVals item indices)
  )
)

(defn fft-nd [l levelList parentDimIndices]
  "Recursively computes the fourier transform of an n-dimensional matrix"
  (let [dims (dimensions levelList) numdims (count dims)]
    (if
      (= numdims 1)
      (map (fn [i] (fft-nd-sample-map l (cons i parentDimIndices) (list) l (list))) (range (count levelList)))
      (map-indexed (fn [i sublist] (fft-nd l sublist (cons i parentDimIndices))) levelList))))


;Function to be used publically by other files
(defn fft [m]
  "Computes the fourier transform of an n-dimensional matrix m"
  (let [dims (dimensions m) numdims (count dims)]
      (if
        (= numdims 1)
        (fft-1d m)
        (fft-nd m m (list)))))
