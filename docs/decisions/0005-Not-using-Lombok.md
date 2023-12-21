# Not using Lombok

## Context and Problem Statement

While documenting the interface with JavaDoc it appeared many getters and setters were not covered as Lombok prevents the JavaDoc documentation from being generated. Some hacks for this are available, but didn't seem to work.

## Considered Options

* Use Lombok
* Stop using Lombok

## Decision Outcome

Chosen option: do not use Lombok
* Good as this is yet another external dependency less (see [0004 Minimise external dependencies](0004-Minimise-external-dependencies.md))
* Good as the dependency requires regular updates due to breaking on Java updates
* Bad / Neutral because this will require more work on DTOs: This should be a one-time effort on the model and an extra line for SLF4J declarations. As we mostly use records with builders due to [0002 read only interface](0002-Read-only-interface.md) the impact should be negligible.
