# Adapter Pattern
It helps interfaces with incompatible types to collaborate.
So converting an interface of one class to the interface that the client expects.

***

## Advantages:

#### This increases the reusability of the classes without modifying the behavior of the class.(Open/closed Principle)
#### Adapter has a single responsibility of translation. This promotes Single Responsibility.

***

## Disadvantages

#### Extra layer might add some time to the runtime, this might not be a perfect solution for performance-critical application.
#### Lots of boilerplate code needs to be added.

***

## When to use it
1. Integrate legacy code with modern systems when interfaces are not compatible.ß
2. Communicating with third party libraries.
3. Version Migration within the project.

***

## When to not use it
1. When we control both client and service, we should not use adapter.
2. Performance is critical.
3. Building a new system from scratch.

***
***