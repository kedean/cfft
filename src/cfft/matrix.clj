(ns cfft.matrix)

; Note that these functions assume uniform matrices as input. Non-matrix sequences, such as [[1 2 3] [1 2]] will produce undefined behavior.

(defn matrix-apply [transformer mat]
  "Applies the function 'transformer' to every element of the matrix mat. The function should take the original matrix as the first argument, the atomic element as the second, and a collection of indices as the third argument, in consecutive order."
  (
    (fn element-apply [el indices]
      (if
        (sequential? el)
        ;we need to matrix-apply to the submatrix
        (map-indexed
          (fn [index sub-el]
            (element-apply sub-el (conj indices index)))
          el)
        ;we're at the deepest level, apply the transformer
        (transformer mat el indices)
      )
    ) mat (list)))

(defn matrix-summation [summer transformer mat]
  "Computes the summation of a matrix mat using the function 'transformer' at each element and the summing function 'summer' to sum them. The function should take the original matrix as the first argument, the atomic element as the second, the counter values as the third, and the counter limits as the fourth. The last two arguments are in order of dimension."
  (
    (fn element-summation [el counters limits]
      (if
        (sequential? el)
        ;we need to sum the current submatrix
        (let [newLimits (cons (count el) limits)]
          (reduce
            summer
            (map-indexed
              (fn [index sub-el]
                 (element-summation
                   sub-el
                   (conj counters index)
                   newLimits))
               el)
            )
          )
        ;we're at the deepest level, apply the transformer
        (transformer mat el counters limits)
        )
      )
    mat (list) (list)))
