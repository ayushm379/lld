# Builder Pattern

It helps in creating complex object step-by-step. 
It helps when lots of fields are optional.

***

## Advantages:
 
#### Simplifies creating objects with many parameters, avoiding telescoping constructors.
#### Enables Creating Immutable objects as builder builds the object in one go.
#### During construction, we can have validation as well.
### Flexibility and Reusability with optional parameters.

***

## Disadvantages

#### Requires creating a separate Builder class, this increases codebase size.
#### We need to write repetitive code for builder's fields, setters.
#### Any change in the main class will require updating Builder class.

***

## When to use it
1. When Object has many parameters and a lot of optional parameters.
2. Need for immutability of objects.
3. When Object creation requires validation logic, or has complex initialization logic.

***

## When to not use it
1. Avoid it in systems which are performance-critical, as there is an overhead of creating the builder pattern.
2. Simple object is there with few parameters or small projects are there.

***
***