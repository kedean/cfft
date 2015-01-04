(ns clj-fft.complex-test
  (:require [clojure.test :refer :all]
            [clj-fft.complex :refer :all])
  (:import java.lang.Math))

(deftest test-new-complex
  (testing "Construct a basic complex number"
    (complex 1 2)
  ))

(deftest test-int-is-complex
  (testing "Integers function as complex numbers"
    (do
      (assert (= 5 (real 5)))
      (assert (= 0 (imag 5)))
    )
  )
)

(deftest test-decimal-is-complex
  (testing "Decimal values function as complex numbers"
    (do
      (assert (= 5.4 (real 5.4)))
      (assert (= 0 (imag 5.4)))
    )
  )
)

(deftest test-complex-magnitude
  (testing "Complex numbers have magnitude"
    (assert
      (=
        (Math/sqrt 34)
        (magnitude (complex 3 5))))))

(deftest test-complex-phase
  (testing "Complex numbers have phase"
    (assert
      (=
        (Math/atan (/ 5.0 3.0))
        (phase (complex 3 5))))))

(deftest test-complex-equals
  (testing "Complex equals should check equality"
    (do
      (assert (complex-equals (complex 1 2) (complex 1 2)))
      (assert (not (complex-equals (complex 1 2) (complex 4 2))))
      (assert (not (complex-equals (complex 1 2) (complex 1 5))))
    )))

(deftest test-complex-add
  (testing "Complex addition"
    (do
      (assert
        (complex-equals
          (complex 3 5)
          (complex-add
            (complex 2 1)
            (complex 1 4)
          )
        )
      )

      (assert
        (complex-equals
          (complex 5 5)
          (complex-add
            (complex 2 5)
            3
          )
        )
      )

      (assert
        (complex-equals
          (complex 5 5)
          (complex-add
            3
            (complex 2 5)
          )
        )
      )
    )
  )
)

(deftest test-complex-sub
  (testing "Complex subtraction"
    (do
      (assert
        (complex-equals
          (complex 0 3)
          (complex-sub
            (complex 2 4)
            (complex 2 1)
          )
        )
      )

      (assert
        (complex-equals
          (complex -1 5)
          (complex-sub
            (complex 2 5)
            3
          )
        )
      )

      (assert
        (complex-equals
          (complex 1 -5)
          (complex-sub
            3
            (complex 2 5)
          )
        )
      )
    )
  )
)
