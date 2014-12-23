(ns clj-fft.matrix)

(defn typed-list? [l]
  "True iff every item in the list is the same type"
  (apply = (map type l)))

(defn equiv-list? [l]
  "True iff every item in the list is equal"
  (apply = l))

(def dimensions (memoize (fn [l]
  (do
    (assert (typed-list? l) "Elements of matrix must be of same type")
    (let [c (count l)]
      (if
        (coll? (first l))
        (let [dimlist (map dimensions l)]
          (do
            (assert (equiv-list? dimlist) "Elements of matrix must be of equal dimensions")
            (cons c (first dimlist)))
        )
        (list c)))
  ))))
