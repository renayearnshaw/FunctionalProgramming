# FunctionalProgramming
This project contains code I wrote while following the [Functional Programming](https://learning.oreilly.com/videos/functional-programming-for/9780134778235) course by Simon Roberts.

### Description
The __FunctionalProgramming__ project is written using Java 9.

We cover the following:
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
7. Default instance methods in interfaces\
Allows new methods to be added to an interface that are automatically available in all implementations.
Implementions are therefore not forced to implement the new methods.
This preserves backward compatibility.
8. Monads - a type of wrapper object that:
   - wraps data
   - provides a way of extracting the data
   - contains a `flatMap()` method (or something that does the equivalent)
9. The `Optional` class\
   A container object which may or may not contain a non-null value.
   It's an monad, specific to handling zero or one instances of 'something'.
10. The `Stream` API
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
    A reduce operation must take two items of one type, and produce a brand new item of one type.
    A reduce operation must be *associative*, ie. `(a + b) + c == a + (b + c)`, because it could be used concurrently.
      - `reduce(BinaryOperator<T> accumulator)` is the simplest type of reduce operation, whose result type 
      is of the same type as the data in the stream. It actually produces an `Optional<T>`, which could be empty.
      - `reduce(T identity, BinaryOperator<T> accumulator)` requires an *identity* value, which is used to avoid 
      producing an empty optional if the stream is empty.\
      The `identity` provides a starting value which is effectively *invisible*, so for summing it would be zero, 
      for multiplying it would be one - something that won't change the result. If the stream is empty, this 
      value will be returned, so there is no need to return `Optional`.
      - `reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner)` is an even more 
      flexible version that allows you to produce a result that is of a different type to the stream type.\
      This method requires an *accumulator* that processes the stream type `T` to produce a result type `U`, and 
      a *combiner* method that can combine any intermediate results from separate sub-streams into a final result 
      of type `U`. It also requires an *identity* that defines what you want to produce, and this is an empty 
      version of the result type. This method still produces a brand new item of type `U` for each operation - it 
      is *non-mutating* - and this can get very expensive.
    - Collect operations\
    These aggregate all the date to a single result, like a reduce operation.
    Unlike a reduce operation - which produces a brand new item each time - the collect operation **mutates** data. 
    This is to avoid the expense of producing new objects for every operation.
      - `collect(Supplier<R> supplier, BiConsumer<R,? super T> accumulator, BiConsumer<R,R> combiner)` still 
      requires an *accumulator* method that processes the stream type `T` to produce a result type `R`, and a 
      *combiner* method that can combine any intermediate results from separate sub-streams into a final result of 
      type `R`. But the processing is kicked off with a 'seed' object of the result type `R` - one for each 
      sub-stream - that is consequently **mutated** by the accumulator operation. Thus we can't just supply a single 
      empty object as a template, we have to provide a *supplier* that can supply each sub-stream with a fresh 
      'seed' object.\
    Also, the accumulator operation this time is a consumer, that returns `void` and mutates the 'seed' object, as
     is the combiner that produces the final result.
      - `collect(Collector<? super T,A,R> collector)` takes a `Collector` object.
    - Concurrency
      - Use the `parallel()` method to create multiple sub-streams (*sharding*).
    Managing parallel streams comes at a cost, so you need to balance this against what the stream is actually doing.
    The more computationally intensive a stream is, the more benefit you will get from running in parallel mode.
      - Use the `unordered()` method to create an unordered stream. Managing order during parallelization can be
      *extremely* expensive.
11. The `Collector` interface\
Provides abstract methods such as an `accumulator` method that includes a stream item, a `combiner` method that 
combines two of the sub-stream results and a `supplier` method that creates a new 'seed' object. It also provides 
a `finisher` method that transforms the intermediate result type to a final result type.\
You can use the `Collector` interface to build your own collector.
12. The `Collectors` class\
This class provides several pre-built collectors via static factory methods, such as `averagingInt`, `counting`,
 `mapping` and `groupingBy`. 
    - `groupingBy(Function<? super T,? extends K> classifier)` returns a `Collector` that groups elements in a 
    stream into groups (lists) that all have the same specified key.
    When creating the collector produced by the `groupingBy` method, you will need to tell the collector *how* to 
    extract the key from the stream element, which you do via the `classifier`. 
    It returns a `Map` of keys to lists of stream elements.
    - `groupingBy(Function<? super T,? extends K> classifier, Collector<? super T,A,D> downstream)`. The stream
    items that would have been stored in a list are first passed through a *downstream collector*. The values 
    that are stored in the `Map` will no longer be lists, but are the result of the operation provided by the
    downstream collector. 
    - `mapping(Function<? super T,? extends U> mapper, Collector<? super U,A,R> downstream)` returns a `Collector` 
    that maps stream items from type `U` to type `T`, and then passes them through a *downstream collector*, 
    which determines the result that is stored in the map.
13. Method references\
These are used when a method already exists that does exactly what you want to do.
There are four types:
    - Static method invocation\
    When a lambda expression takes some arguments, invokes a static method on a class and passes those arguments
    straight through:\
    `(a, ...) -> MyClass.aMethod(a, ...)`\
     becomes\
    `MyClass::aMethod`
    - Instance method invocation on a particular object\
    This time the arguments are passed through to a method that is invoked on an object:\
    `(a, ...) -> anObject.aMethod(a, ...)`\
    becomes\
    `anObject::aMethod`
    - Constructor invocation\
    This time the arguments are passed through to a constructor method that is invoked to create a new object:\
    `(a, ...) -> new MyClass(a, ...)`\
    becomes\
    `MyClass::new`
    - Instance method invocation on an arbitrary object\
    This time the first argument from the lambda is an object which is used to invoke a method.
    The remaining arguments from the lambda are then passed to that method:\
    `(a, ...) -> a.aMethod(...)`\
    becomes\
    `MyClass::aMethod`, where `a` must be of type `MyClass`\
    This takes the same format as the static method invocation. How do we know which type of lambda expresssion
    we're dealing with? The *context* will tell you. If the method used is static or instance, then the appropriate
    invocation will be used. If you have two methods of the same name in your class, the compiler will throw an 
    error.
