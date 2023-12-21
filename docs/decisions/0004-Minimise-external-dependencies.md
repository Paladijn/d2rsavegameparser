# Minimise external dependencies

## Context and Problem Statement

External dependencies in libraries require maintenance and can become attack vectors and may cause conflicts if the user wishes to use a different version.  
At the very least attempt to mark all dependencies as provided, so it will have less impact on users.

## Considered Options

* Just use whatever dependencies we need and mark them as provided
* Modern Java versions offer a lot of extra functionality lessening the need to use external dependencies, attempt to minimise those to as few as possible.

## Decision Outcome

Chosen option: Use as few external dependencies as possible while relying on modern JDK functionality.
* Good for users as they won't have to bother with our dependencies potentially causing conflicts
* Good for maintainers as they won't need to learn about those dependencies
* Bad for maintainers as they may be used to existing dependencies and libraries
* Neutral because this may require a bit more work from maintainers to consider and implement alternatives.
