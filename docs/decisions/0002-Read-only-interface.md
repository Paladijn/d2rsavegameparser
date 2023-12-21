# Read-only interface

## Context and Problem Statement

Considering we stick to the latest LTS versions (decision 0001) we'll get the record type which will allow us to return read-only types in the interface.

## Considered Options

* Use DTOs with getters _and_ setters
* Only use records in the external interface

## Decision Outcome

Chosen option: Only use (read-only) records in the external interface
* Good from a parser/reader perspective: we only pass on the values
* Good for simplifying the interface
* Neutral as it will require builders for the larger records
* Bad for having more code as the internal model will have more fields

## More Information

The records that are filled over time require a builder to be passed along. Examples are the D2Character and D2Item records.
