(ns clj-fft.matrix-test
  (:require [clojure.test :refer :all]
            [clj-fft.matrix :refer :all])
  )

(deftest test-matrix-apply
  (testing "Apply a function to every lowest element of a matrix"
    (do
      ;test a 1d matrix
      (assert
          (=
            (list 1 2 3)
            (matrix-apply (fn [mat el indices] (+ 1 el)) (list 0 1 2))
          )
      )
      ;test a 2d matrix
      (assert
        (=
          (list (list 1 2 3) (list 2 3 4))
          (matrix-apply (fn [mat el indices] (+ 1 el)) (list (list 0 1 2) (list 1 2 3)))
        )
      )
      ;test a 3d matrix
      (assert
        (=
          (list
            (list
              (list 1 2 3) (list 2 3 4)
            )
            (list
              (list 3 4 5) (list 4 5 6)
            )
          )
          (matrix-apply
            (fn [mat el indices] (+ 1 el))
            (list
              (list
                (list 0 1 2) (list 1 2 3)
              )
              (list
                (list 2 3 4) (list 3 4 5)
              )
            )
          )
        )
      )
    )
  )
)

(deftest test-matrix-summation
  (testing "Apply a prepass function and a reducer function to sum up numbers in a matrix"
    (do
      ;1d tests
      (assert
        (=
          ;hardcoded output
          9
          ;sum up the values by adding one to each then doing addition
          (matrix-summation
            +
            (fn
              [mat el counters limits]
              (do
                ;make sure limits are correct first
                (assert
                  (=
                    limits
                    (list 3)))
                (+ el 1)) ;the list becomes (2 3 4) before addition occurs
              )
            (list 1 2 3)
          )
        )
      )
      ;1d mat, check the counters that are being given by concatenating them all
      (assert
        (=
          (list 0 1 2) ;this list is just the indices of items
          (matrix-summation
            concat
            (fn
              [mat el counters limits]
              counters
            )
            (list 1 2 3)
          )
        )
      )
      ;2d matrix
      (assert
        (=
          ;hardcoded output
          27
          ;sum up the values by adding one to each then doing addition
          (matrix-summation
            +
            (fn
              [mat el counters limits]
              (do
                ;make sure limits are correct first
                (assert
                  (=
                    limits
                    (list 3 2)))
                (+ el 1)) ;the list becomes (2 3 4) before addition occurs
              )
            (list (list 1 2 3) (list 4 5 6))
          )
        )
      )
      ;2d mat, check the counters that are being given by concatenating them all
      (assert
        (=
          (list 0 0 1 0 2 0 0 1 1 1 2 1) ;this list is just the indices of items
          (matrix-summation
            concat
            (fn
              [mat el counters limits]
              counters
            )
            (list (list 1 2 3) (list 4 5 6))
          )
        )
      )
    )
  )
)
