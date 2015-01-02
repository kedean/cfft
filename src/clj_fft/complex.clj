(ns clj-fft.complex
  (:import java.lang.Math)
)

(defprotocol AbstractComplex
  (real [num])
  (imag [num]))

(deftype Complex [real_component imaginary_component]
  AbstractComplex
  (toString [num] (str "(" real_component " + " imaginary_component "j)"))
  (real [num] real_component)
  (imag [num] imaginary_component)
)

;Since all numeric types are extended with AbstractComplex, the complex operations can be used with any numeric OR a real complex
(extend Number
  AbstractComplex
  {:real (fn [num] num)
    :imag (fn [num] 0)})

(defn magnitude [^Complex num] (Math/sqrt (+ (Math/pow (real num) 2) (Math/pow (imag num) 2))))
(defn phase [^Complex num] (Math/atan (/ (imag num) (real num))))

(defn complex-add [first second]
  (Complex.
    (+ (real first) (real second))
    (+ (imag first) (imag second))
    ))

(defn complex-sub [first second]
  (Complex.
    (- (real first) (real second))
    (- (imag first) (imag second))
    ))

(defn complex-equals [first second]
  (and
    (= (real first) (real second))
    (= (imag first) (imag second))))

(defn complex-mult [first second]
  (Complex.
    (-
      (* (real first) (real second))
      (* (imag first) (imag second))
      )
    (+
      (* (imag first) (real second))
      (* (real first) (imag second))
      )))
