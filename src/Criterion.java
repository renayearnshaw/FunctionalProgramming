public interface Criterion<E> {
    boolean test(E e);

    default Criterion<E> negate() {
        return x -> !this.test(x);
    }

    default Criterion<E> or(Criterion<E> criterion2) {
        return x -> this.test(x) || criterion2.test(x);
    }

    default Criterion<E> and(Criterion<E> criterion2) {
        return x -> this.test(x) && criterion2.test(x);
    }

}
