(ns clj-fft.core-test
  (:require [clojure.test :refer :all]
            [clj-fft.core :refer :all])
  (:use clj-fft.complex)
  (:import java.lang.Math))

(defn round [n places]
  (/
    (Math/round
      (* n places))
    places))

(def error-tolerance 0.0001)

(deftest fft-test-1d
  (testing "Check the result of transforming a 1d sequence"
  (let [
    expected-out
    (list
      (complex 10 0)
      (complex -2 2)
      (complex -2 0)
      (complex -2 -2)
      )
      actual-out
      (fft (list 1 2 3 4))
      ]
      (doall (map
        (fn [exp act]
                (let [diff (complex-sub exp act)]
                (do
                  (assert
                    (< (real diff) error-tolerance))
                    (assert
                      (< (imag diff) error-tolerance)))))
                      expected-out actual-out))
                      )))

(deftest fft-test-3x3
  (testing "Check the result of transforming a 3x3 matrix"
    (let [
        expected-out
        (list
          [(complex 45.0 0)         (complex -4.5 2.59807621)  (complex -4.5 -2.59807621)]
          [(complex -13.5 7.79422863)    (complex 0.0 0)           (complex 0.0 0)]
          [(complex -13.5 -7.79422863)   (complex 0.0 0)           (complex 0.0 0)]
        )
        actual-out
        (fft (list [1 2 3] [4 5 6] [7 8 9]))
      ]
      (doall (map
        (fn [exp-level-1 act-level-1]
          (doall
          (map
            (fn [exp act]
              (let [diff (complex-sub exp act)]
                (do
                  (assert
                    (< (real diff) error-tolerance))
                  (assert
                    (< (imag diff) error-tolerance)))))
            exp-level-1 act-level-1
            )))
        expected-out actual-out))
      )))

(deftest ifft-test-1d
  (testing "Check the results of inverting a 1d sequence, by transforming then inverting, and checking for differences"
    (let [
      original-list
      (list 1 2 3)
      actual-out
      (ifft (fft original-list))
      ]
      (doall
        (map
          (fn [exp act]
                  (let [diff (complex-sub exp act)]
                  (do
                    (assert
                      (< (real diff) error-tolerance))
                      (assert
                        (< (imag diff) error-tolerance)))))
            original-list actual-out
          )
        )
      )
    )
  )

(deftest fft-complex-input
  (testing "fft should take complex inputs as well."
    (fft (list (complex 1 0) (complex 2 0) (complex 3 1)))
  ))

(deftest fft-real-input
  (testing "fft should take real inputs."
    (fft (list 1 2 3))
  ))

(deftest ifft-complex-input
  (testing "ifft should take complex inputs as well."
    (ifft (list (complex 1 0) (complex 2 0) (complex 3 1)))
  ))

(deftest ifft-real-input
  (testing "ifft should take real inputs."
    (ifft (list 1 2 3))
  ))
