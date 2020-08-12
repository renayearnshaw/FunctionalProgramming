import java.util.function.Consumer;
import java.util.function.Function;

// A wrapper that contains *either* a value or the reason for a failure
public class Either<E> {
    private E value;
    private Throwable problem;

    private Either() {}

    // Factory method to return a Either representing success
    public static <E> Either<E> success(E value) {
        Either<E> self = new Either<>();
        self.value = value;
        return self;
    }

    // Factory method to return a Either representing falure
    public static <E> Either<E> failure(Throwable throwable) {
        Either<E> self = new Either<>();
        self.problem = throwable;
        return self;
    }

    // Determine whether the Either contains a value
    public boolean success() {
        return value != null;
    }

    // Determine whether the Either contains a problem
    public boolean failure() {
        return problem != null;
    }

    public E getValue() {
        return value;
    }

    public Throwable getProblem() {
        return problem;
    }

    // If the Either contains a value, the behaviour passed in to this method is invoked on it
    public void ifSucceeded(Consumer<E> consumer) {
        if (value != null) {
            consumer.accept(value);
        }
    }

    // If the Either contains a problem, the behaviour passed in to this method is invoked on it
    public void ifFailed(Consumer<Throwable> consumer) {
        if (problem != null) {
            consumer.accept(problem);
        }
    }

    // This wrapper function takes any behaviour that takes a single argument and returns
    // a single result but throws an exception. It returns a function that takes a single
    // argument and returns the same result wrapped in an Either, *without* throwing an
    // exception. This resulting behaviour can thus be used in places that can't handle
    // exceptions, such as stream processing.
    public static <E,F> Function<E, Either<F>> wrap(ExceptionFunction<E,F> operation) {
        return e -> {
            try {
                return Either.success(operation.apply(e));
            } catch (Throwable t) {
                return Either.failure(t);
            }
        };
    }

}
