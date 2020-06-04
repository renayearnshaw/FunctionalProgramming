import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

// A wrapper around data (an Iterable) that allows us to apply
// operations to it (in this case, a filter operation). Each
// operation returns a new wrapper object that contains modified data.
// This will allow us to chain operations and create a pipeline.
public class SuperIterable<E> implements Iterable<E> {
    // The Iterable object that the wrapper wraps
    private Iterable<E> self;

    public SuperIterable(Iterable<E> self) {
        this.self = self;
    }

    // Return a new SuperIterable containing objects that pass the predicate
    public SuperIterable<E> filter(Predicate<E> predicate) {
        List<E> results = new ArrayList<>();

        for (E e: self) {
            if (predicate.test(e)) {
                results.add(e);
            }
        }

        return new SuperIterable<>(results);
    }

    @Override
    public Iterator<E> iterator() {
        return self.iterator();
    }

    public static void main(String[] args) {
        SuperIterable<String> strings = new SuperIterable<>(
                Arrays.asList("Red", "yellow", "pink", "Green", "orange", "purple", "blue")
        );

        // Iterate over the SuperIterable
        for (String string: strings) {
            System.out.println("> " + string);
        }

        // Create a SuperIterable that only contains strings that start with an upper case letter
        SuperIterable<String> upperCase =
                strings.filter(s -> Character.isUpperCase(s.charAt(0)));

        System.out.println("----------------------------------------------------------------");
        for (String string: upperCase) {
            System.out.println("> " + string);
        }

        // Check that the original list is unchanged
        System.out.println("----------------------------------------------------------------");
        for (String string: strings) {
            System.out.println("> " + string);
        }
    }
}
