// This interface defines a behaviour that takes a single argument
// and returns a single result but throws an exception.
public interface ExceptionFunction<E,F> {
    F apply(E e) throws Throwable;
}
