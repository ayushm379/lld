# Singleton Pattern
It's a creational design pattern that ensures the class has only one instance
and provides a global point of access.

### What will happen if we create multiple instances of a cache manager, but with the same cache source?
There will be two TCP connections to the cache. 
One or other instance might not reflect the values immediately, causing inconsistencies and increasing cache hit.
Number of connection pools = number of instances, so more objects, so GC pauses will be more.
This will make the system hard to debug.

***

## Advantages:
#### Ensures only one instance exists, which is useful for resources like config manager, db connection.
#### Reusing the same instance reduces memory footprint.
#### Lazy initialization ensures that an instance is created only when needed.

***

## Disadvantages
#### Poor implementation can lead to bugs in multithreaded environment.

***

## When to use it
1. Managing shared resources like thread pool, connection pool, cache.
2. When we need a single source of truth for app wide configurations.

***

## When to not use it
1. When multiple instances are needed.

***
***