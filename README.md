# FunctionalProgramming
This project contains code I wrote while following [Functional Programming](https://learning.oreilly.com/videos/functional-programming-for/9780134778235) course by Simon Roberts.

### Description
The __FunctionalProgramming__ project is written using Java 9.

We start with a traditional object oriented design pattern and move to a functional foundation. 
We do this for filtering and sorting of objects.

We learn about:
1. Lambda expressions and their syntax variations
   - block lambdas
   - expression lambdas
   - type specifiers are optional
   - single arguments can be defined without brackets
2. Functional interfaces\
   Interfaces that must have a single abstract method.
   - the `@FunctionalInterface` annotation
   - the `java.util.function` package
3. Closures and the concept of _effectively final_
4. Higher order functions\
   Functions as arguments to other functions, or returns from other functions.
   - Functions that adapt functions
5. Pure functions\
   A function whose return value depends only on its arguments and has no side effects (they are _idempotent_).
   It always returns the same result from the same inputs (referential transparency).
6. Static methods in interfaces\
Allows a higher degree of cohesion by grouping related methods in one place without having to create an object.
You no longer need to create artificial utility classes that are simply placeholders for static methods.
6. Default instance methods in interfaces\
Allows new methods to be added to an interface that are automatically available in all implementations.
Implementions are therefore not forced to implement the new methods.
This preserves backward compatibility.
7. Monads - a type of wrapper object that:
   - wraps data
   - provides a way of extracting the data
   - contains a `flatMap()` method (or something that does the equivalent)
8. The `Optional` class\
   A container object which may or may not contain a non-null value.
   It's an monad, specific to handling zero or one instances of 'something'.
9. The `Stream` API\
   - Streams are 'lazy'.\
   Each item contained by a stream will be processed completely before the next one is processed.\
   This means that a stream can give a result before it's finished processing all the data in the stream.
   A stream can thus be infinite, and completes when enough data has been processed to give a result.
   - Streams can only be used once. If you need to process the same data again, you must create a new stream.
   - A stream can run concurrently if you have a CPU that has multiple cores. 
   This means that you cannot use shared mutable data, because you could have multiple threads changing the 
   data at the same time.
   - There are variants of streams for processing primitive data (`int`, `long` and `double`)\
   This is to avoid the boxing and unboxing of primitives, which can be very wasteful of objects and garbage collection.
   - Terminal operations\
   These produce final results and terminate stream processing.
   Any method that doesn't return another stream is a terminal method.
   - Reduce operations\
   These aggregate all the data to a single result. 
   A reduce operation must take two items of one type, and produce a new item of one type.
   A reduce operation must be *associative*, ie. `(a + b) + c == a + (b + c)`, because it could be used concurrently.