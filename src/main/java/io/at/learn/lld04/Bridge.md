# Bridge Pattern
Helps to separate a set of co-related classes into two separate hierarchies, which can be developed separately.

***

## Advantages:

#### Open/Close principle: this is open for extension, closed for modification
#### Decoupling of two separate hierarchies.

***

## Disadvantages

#### Increases the complexity of the code by adding more interfaces.
#### Over-engineering if only one implementation exists.
#### Harder debugging.

***

## When to use it
1. Multiple variations in two separate hierarchies.

***

## When to not use it
1. When there is only one implementation for all hierarchies, this would be over-engineering.
2. When performance is critical.

***
***