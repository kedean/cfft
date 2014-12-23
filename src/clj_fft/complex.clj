(ns clj-fft.complex
  (:import java.lang.Math)
)

(defprotocol AbstractComplex
  (real [num])
  (imag [num])
  (magnitude [num])
  (phase [num])
)

(deftype Complex [real_component imaginary_component]
  AbstractComplex
  (toString [num] (str "(" real_component " + " imaginary_component "j)"))
  (real [num] real_component)
  (imag [num] imaginary_component)
  (magnitude [num] (Math/sqrt (+ (* real_component real_component) (* imaginary_component imaginary_component))))
  (phase [num] (Math/atan (/ imaginary_component real_component)))
)

(defn add [^Complex first ^Complex second]
  (Complex.
    (+ (real first) (real second))
    (+ (imag first) (imag second))
    ))

(defn sub [^Complex first ^Complex second]
  (Complex.
    (- (real first) (real second))
    (- (imag first) (imag second))
    ))

(defn mult [^Complex first ^Complex second]
  (Complex.
    (-
      (* (real first) (real second))
      (* (imag first) (imag second))
      )
    (+
      (* (imag first) (real second))
      (* (real first) (imag second))
      )))

(defn mult [^Complex first ^Integer second]
  (Complex.
    (* (real first) second)
    (* (imag first) second)
    ))
