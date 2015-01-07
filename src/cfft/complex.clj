(ns cfft.complex
  (:import java.lang.Math)
)

(defn complex [real imag] {:real real :imag imag})
(defn real [n] (get n :real n))
(defn imag [n] (get n :imag 0))

(defn magnitude [num] (Math/sqrt (+ (Math/pow (real num) 2) (Math/pow (imag num) 2))))
(defn phase [num] (Math/atan (/ (imag num) (real num))))

(defn complex-add [first second]
  (complex
    (+ (real first) (real second))
    (+ (imag first) (imag second))
    ))

(defn complex-sub [first second]
  (complex
    (- (real first) (real second))
    (- (imag first) (imag second))
    ))

(defn complex-equals [first second]
  (and
    (= (real first) (real second))
    (= (imag first) (imag second))))

(defn complex-mult [first second]
  (complex
    (-
      (* (real first) (real second))
      (* (imag first) (imag second))
      )
    (+
      (* (imag first) (real second))
      (* (real first) (imag second))
      )))
