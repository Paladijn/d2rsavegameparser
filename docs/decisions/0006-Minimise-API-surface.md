# Minimise API surface

## Context and Problem Statement

Minimise the API surface by limiting the amount of types from dependencies being exposed and minimising inheritance when it is not required.

## Considered Options

* Minimise the API surface as much as possible
* Take a more lenient approach and only limit dependency types while allowing inheritance

## Decision Outcome

Chosen option: Minimise the API surface as much as possible
* Good as it simplifies our interface
* Good because as we want to [minimise external dependencies](0004-Minimise-external-dependencies.md) as well. Not returning their types will also prevent their breaking changes impacting us
* Neutral as it limits inheritance for users that wish to override.
* Bad is it will require more work from maintainers (marking classes and non-overridable methods as final)
