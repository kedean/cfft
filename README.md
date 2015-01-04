# clj_fft

A pure Clojure implementation of Fourier transforms.

![Travis CI Build Status](https://travis-ci.org/kedean/clj-fft.svg?branch=master)

## Usage

clj_fft is intended as a library, import it into your clojure project and call the fft or ifft function on any matrix.

## Examples

Calling clj_fft with:
````(fft [1 2 3])````

will yield:
````(<Complex (6.0 + -0.0j)>
<Complex (-1.5000000000000009 + 0.8660254037844382j)>
<Complex (-1.4999999999999987 + -0.8660254037844404j)>)````

The fft and ifft functions will take any matrix containing numbers or complex values (hashmaps with a :real and an :imag key). Both produce matrices of identical size to the input containing complex values. To get the results as real numbers, use matrix-apply and the real function.

````(matrix-apply real (fft [1 2 3]))````

## Performance

Performance has not been benchmarked, and is unpredicatble. It currently improves with calls to fft and ifft, most likely as a result of caching in calls to sin and cos in java.lang.Math.

## License

Copyright © 2014 Kevin Dean

Distributed under the MIT license.
