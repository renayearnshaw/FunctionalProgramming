import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

// A wrapper around data (an Iterable) that allows us to apply
// operations to it (in this case, a filter or map operation). Each
// operation returns a new wrapper object that contains modified data.
// This will allow us to chain operations and create a pipeline.
public class SuperIterable<E> implements Iterable<E> {
    // The Iterable object that the wrapper wraps
    private Iterable<E> self;

    public SuperIterable(Iterable<E> self) {
        this.self = self;
    }

    // Return a new SuperIterable containing objects that have been changed/mapped by the operation
    public <F> SuperIterable<F> map(Function<E, F> operation) {
        List<F> results = new ArrayList<>();

        self.forEach(e -> results.add(operation.apply(e)));

        return new SuperIterable<>(results);
    }

    // Return a new SuperIterable containing many items for each item in the original SuperIterable
    // (One to many mapping)
    public <F> SuperIterable<F> flatMap(Function<E, SuperIterable<F>> operation) {
        List<F> results = new ArrayList<>();

        self.forEach(
            // The result of the apply operation is a SuperIterable of type F
            e -> operation.apply(e)
                // Add each item in the SuperIterable into the results list
                .forEach(f-> results.add(f)));

        return new SuperIterable<>(results);
    }

    // Return a new SuperIterable containing objects that pass the predicate
    public SuperIterable<E> filter(Predicate<E> predicate) {
        List<E> results = new ArrayList<>();

        self.forEach(e -> {
            if (predicate.test(e)) results.add(e);
        });

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
        strings.forEach(s -> System.out.println("> " + s));

        // Create a SuperIterable that only contains strings that start with an upper case letter
        SuperIterable<String> upperCase =
                strings.filter(s -> Character.isUpperCase(s.charAt(0)));

        System.out.println("----------------------------------------------------------------");
        upperCase.forEach(s -> System.out.println("> " + s));

        // Check that the original list is unchanged
        System.out.println("----------------------------------------------------------------");
        strings.forEach(s -> System.out.println("> " + s));

        System.out.println("----------------------------------------------------------------");
        // Chain operations without creating intermediate variables
        strings.filter(s -> Character.isUpperCase(s.charAt(0)))
            // Map from a mixed case string to an upper case string
            .map(s -> s.toUpperCase())
            .forEach(s -> System.out.println("> " + s));

        // Check that the original list is unchanged
        System.out.println("----------------------------------------------------------------");
        strings.forEach(s -> System.out.println("> " + s));

        SuperIterable<Car> cars = new SuperIterable<>(
            Arrays.asList(
                Car.withGasColourPassengers(6, "Red", "Fred", "Jim", "Sheila"),
                Car.withGasColourPassengers(3, "Octarine", "Rincewind", "Ridcully"),
                Car.withGasColourPassengers(9, "Black", "Weatherwax", "Magrat"),
                Car.withGasColourPassengers(7, "Green", "Valentine", "Gillian", "Anne", "Dr. Mahmoud"),
                Car.withGasColourPassengers(6, "Red", "Ender", "Hyrum", "Locke", "Bonzo")
        ));

        System.out.println("----------------------------------------------------------------");
        // Filter out cars that have fuel levels of six or less
        cars.filter(car -> car.getGasLevel() > 6)
            // Map from a Car to a String - a description of the car
            .map(car -> car.getPassengers().get(0) + " is driving a " + car.getColour()
                + " car with lots of fuel")
            .forEach(description -> System.out.println("> " + description));

        System.out.println("----------------------------------------------------------------");
        // Add gas to all the cars, but without modifying the original cars
        cars.map(car -> car.addGas(4)).forEach(car -> System.out.println("> " + car));

        System.out.println("----------------------------------------------------------------");
        // Print out a list of all the passengers in all the cars
        cars.flatMap(car -> new SuperIterable<>(car.getPassengers())).forEach(car -> System.out.println("> " + car));

        // Print out the original list of cars
        System.out.println("----------------------------------------------------------------");
        cars.forEach(car -> System.out.println("> " + car));
    }
}
