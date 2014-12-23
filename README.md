# clj_fft

A pure Clojure implementation of Fourier transforms.

## Usage

clj_fft is intended as a library, import it into your clojure project and call the fft function on any sequence.

## Examples

Calling clj_fft with:
````(fft [1 2 3])````

will yield:
````(<Complex (6.0 + -0.0j)>
<Complex (-1.5000000000000009 + 0.8660254037844382j)>
<Complex (-1.4999999999999987 + -0.8660254037844404j)>)````

## License

Copyright © 2014 Kevin Dean

Distributed under the MIT license.
