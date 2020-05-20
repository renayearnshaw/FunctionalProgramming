public interface Criterion<E> {
    boolean test(E e);

    static <E> Criterion<E> negate(Criterion<E> criterion) {
        return x -> !criterion.test(x);
    }

    static <E> Criterion<E> or(Criterion<E> criterion1, Criterion<E> criterion2) {
        return x -> criterion1.test(x) || criterion2.test(x);
    }

    static <E> Criterion<E> and(Criterion<E> criterion1, Criterion<E> criterion2) {
        return x -> criterion1.test(x) && criterion2.test(x);
    }

}
