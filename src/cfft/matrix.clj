(ns cfft.matrix)

; Note that these functions assume uniform matrices as input. Non-matrix sequences, such as [[1 2 3] [1 2]] will produce undefined behavior.

(defn matrix-apply [f mat & args]
  "Applies the function f to every element of the matrix mat. The function should take the original matrix as the first argument, the atomic element as the second, and a collection of indices as the third argument, in consecutive order."
  (
    (fn element-apply [el indices]
      (if
        (or (seq? el) (vector? el))
        ;we need to matrix-apply to the submatrix
        (map-indexed
          (fn [index sub-el]
            (element-apply sub-el (cons index indices)))
          el)
        ;we're at the deepest level, apply f!
        (if
          (nil? (some #{:all-args} args))
          (f el) ;otherwise just pass the one item
          (f mat el indices) ;the :all-args argument means they want to use every argument we have
        )
      )
    ) mat (list)))

(defn matrix-summation [r f mat & args]
  "Computes the summation of a matrix mat using the function f at each element and the summing function r to sum them. The function should take the original matrix as the first argument, the atomic element as the second, the counter values as the third, and the counter limits as the fourth. The last two arguments are in order of dimension."
  (
    (fn element-summation [el counters limits]
      (if
        (or (seq? el) (vector? el))
        ;we need to sum the current submatrix
        (reduce
          r
          (let
            [newLimits (cons (count el) limits)]
            (map-indexed
              (fn [index sub-el]
                (element-summation sub-el (cons index counters) newLimits))
              el)
          ))
        ;we're at the deepest level, apply f!
        (if
          (nil? (some #{:all-args} args))
          (f el) ;otherwise just pass the one item
          (f mat el counters limits) ;the :all-args argument means they want to use every argument we have
          )
        )
      )
    mat (list) (list)))
