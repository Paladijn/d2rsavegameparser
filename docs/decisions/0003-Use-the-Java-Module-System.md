# Use the Java Module System

## Context and Problem Statement

JDK 9 and up offer the Java Module System which benefits architectural design and restricting internal packages for libraries.

## Considered Options

* Stick to the classpath
* Use the Java Module System

## Decision Outcome

Chosen option: Use the Java Module System
* Good because it allows more fine-grained control over the classes shared by the library
* Good because the dependencies and visibility of classes is exposed in a more readable way
* Good as it is not enforced upon users of the library
* Bad/Neutral, as to make full use of the JMS the library user will also have to apply JMS. For example internal classes are not available on the module classpath, but they are on the one when you don't use JMS.

## More information
Java modules in real life on [YouTube](https://www.youtube.com/watch?v=UqnwQp1uHuY).