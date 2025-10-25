# Factory Pattern

Provides a way to create objects without specifying the exact class.
To implement it, make all the products follow the same interface.

***

## Advantages:

### Encapsulation of Object Creation
Isolates complexity for Object creation, 
makes it easy to manage and modify creational logic.

### Improves Code Maintainability
Change of object creation only requires updating the Factory method, 
not the client code.

### Loose Coupling
The creational logic and business logic are separated, 
which reduces dependencies on each other.

### Extensibility
New types can be added without altering client code.

***

## Disadvantages

### Increased Complexity
The code may become more complicated since a lot of new subclasses need to be introduced.

### Performance Overhead
Adds an extra layer of object creation, which could add minor performance cost.

***

## When to use it
1. Multiple related classes with common interface.
2. Extensibility is priority
3. Loose Coupling is desired.

***

## When to not use it
1. Performance-critical System
2. Avoid it when object creation is straightforward and unlikely to change.

***
***
