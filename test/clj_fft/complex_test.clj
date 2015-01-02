(ns clj-fft.complex-test
  (:require [clojure.test :refer :all]
            [clj-fft.complex :refer :all])
  (:import clj_fft.complex.Complex)
  (:import java.lang.Math))

(deftest test-new-complex
  (testing "Construct a basic complex number"
    (Complex. 1 2)
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
        (magnitude (Complex. 3 5))))))

(deftest test-complex-phase
  (testing "Complex numbers have phase"
    (assert
      (=
        (Math/atan (/ 5.0 3.0))
        (phase (Complex. 3 5))))))

(deftest test-complex-equals
  (testing "Complex equals should check equality"
    (do
      (assert (complex-equals (Complex. 1 2) (Complex. 1 2)))
      (assert (not (complex-equals (Complex. 1 2) (Complex. 4 2))))
      (assert (not (complex-equals (Complex. 1 2) (Complex. 1 5))))
    )))

(deftest test-complex-add
  (testing "Complex addition"
    (do
      (assert
        (complex-equals
          (Complex. 3 5)
          (complex-add
            (Complex. 2 1)
            (Complex. 1 4)
          )
        )
      )

      (assert
        (complex-equals
          (Complex. 5 5)
          (complex-add
            (Complex. 2 5)
            3
          )
        )
      )

      (assert
        (complex-equals
          (Complex. 5 5)
          (complex-add
            3
            (Complex. 2 5)
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
          (Complex. 0 3)
          (complex-sub
            (Complex. 2 4)
            (Complex. 2 1)
          )
        )
      )

      (assert
        (complex-equals
          (Complex. -1 5)
          (complex-sub
            (Complex. 2 5)
            3
          )
        )
      )

      (assert
        (complex-equals
          (Complex. 1 -5)
          (complex-sub
            3
            (Complex. 2 5)
          )
        )
      )
    )
  )
)
