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
6. Static and default instance methods in interfaces