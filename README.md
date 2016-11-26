# MARS

Rover simulation application in Clojure.

## Overview

This rover simulation is implemented based on rules from: https://gist.github.com/nirev/c42c35eb9a839f7756558519f361bc06

Changes from implementation: in case of parse error or other problems
(like, rover is out of field or parse error), the following rules would be implemented:

* If there's a parse error - three coordinates for start, or invalid characters
in rover's movement: nothing will be simulated, and we'll just return the
string `INVALID-FORMAT`
* If it's possible to parse commands, but after obeying one command rover would be
in an invalid position - out of bounds - we'll return `ERROR OUT-OF-FIELD `, followed
by the last valid position of rover before it reached an invalid coordinate.

## Setup

To get a webserver, run:

    lein run

Then, pass commands using PUT in "localhost:3001/mars". You can use curl:

    curl localhost:3001/move -XPUT -d "5 5
    1 2 N
    LMLMLMLMM
    3 3 E
    MMRMMRMRRM"

## Some examples

This code is deployed on heroku, at https://rock-and-rover.herokuapp.com/. We'll use
`curl` and try to send coordinates to one or more rovers:

```sh
# case 1 - everything is correct
curl https://rock-and-rover.herokuapp.com/move -XPUT -d "5 5
1 2 N
LMLMLMLMM
3 3 E
MMRMMRMRRM"
# returns
1 3 N
5 1 E

# case 2 - parse error (movement of rover 2 includes A
curl https://rock-and-rover.herokuapp.com/move -XPUT -d "5 5
1 2 N
LMLMLMLMM
3 3 E
MMRMMRMRRMA"
# returns
INVALID-FORMAT

# case 3 - rover is out of field
$ curl https://rock-and-rover.herokuapp.com/move -XPUT -d "5 5
1 2 N
LMLMLMLMM
3 3 E
MMRMMRMRRMMMMMMMMM"
# returns
1 3 N
ERROR OUT-OF-FIELD 5 1 E
```

## License

Copyright Maurício Szabo © 2016

Distributed under the Creative Commons Non-comercial Non-derivatives license.
