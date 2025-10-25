# Prototype Pattern

This enables creating an object by copying from an existing object, which is known as prototype.
This is useful when object creation is costly.
Expensive: Involves I/O, database call, computations, external sources

***

## Advantages:

#### Cloning is faster than creating objects from scratch, for objects with expensive initialization.
#### Simplifies object creation, as we can create a new object from prototype and 
#### Allows creating variations of an object.
#### We can have a centralized prototype management system.

***

## Disadvantages

#### For objects with a complex structure, there could be errors cloning objects.
#### Maintaining prototypes consumes some memory if prototypes are large.

***

## When to use it
1. When object creation is expensive, since cloning is less costly.
2. Dynamic object types, since we can have a registry.
3. State prevention, when we want to preserve the initial state of the object.

***

## When to not use it
1. When the object is immutable, since modifications post cloning would not be possible. This will let the object remain in the initial state only.
2. When we have simple object creation, and the object creation is inexpensive.
3. Complex deep copy could throw errors.
4. Sometimes deep copying large objects can take more time, this could create a bottleneck in performance-critical system.

***
***