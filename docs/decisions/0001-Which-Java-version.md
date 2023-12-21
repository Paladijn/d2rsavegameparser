# Which Java version

## Context and Problem Statement

The latest Java LTS versions offer a lot of improvements in speed, security and developer improvements.  
Existing Java solutions for Diablo II tooling are based on JDK 8, so not following up on the latest LTS can result in a larger audience.

## Considered Options

* Build upon JDK 8
* Use the latest LTS and update once a new one arrives (11->17->21, etc.)

## Decision Outcome

Chosen option: Use the latest available LTS and update when a new one arrives
* Good, because receiving performance, security benefits and developer improvements
* Good, for maintenance as the more modern language will be a driver for submissions
* Bad, for adaptation as developers using the library will also need to use the latest LTS
