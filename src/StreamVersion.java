import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamVersion {
    public static void main(String[] args) {
        List<String> strings = new ArrayList<>(
                Arrays.asList("Red", "yellow", "pink", "Green", "orange", "purple", "blue")
        );

        // Iterate over the list using the stream method in List
        strings.stream().forEach(s -> System.out.println("> " + s));

        // Create a stream that only contains strings that start with an upper case letter
        Stream<String> upperCase =
                strings.stream().filter(s -> Character.isUpperCase(s.charAt(0)));

        System.out.println("----------------------------------------------------------------");
        upperCase.forEach(s -> System.out.println("> " + s));

        // Check that the original list is unchanged
        System.out.println("----------------------------------------------------------------");
        strings.stream().forEach(s -> System.out.println("> " + s));

        System.out.println("----------------------------------------------------------------");
        // Chain operations without creating intermediate variables
        strings.stream().filter(s -> Character.isUpperCase(s.charAt(0)))
                // Map from a mixed case string to an upper case string
                .map(s -> s.toUpperCase())
                .forEach(s -> System.out.println("> " + s));

        List<Car> cars = new ArrayList<>(
                Arrays.asList(
                        Car.withGasColourPassengers(6, "Red", "Fred", "Jim", "Sheila"),
                        Car.withGasColourPassengers(3, "Octarine", "Rincewind", "Ridcully"),
                        Car.withGasColourPassengers(9, "Black", "Weatherwax", "Magrat"),
                        Car.withGasColourPassengers(7, "Green", "Valentine", "Gillian", "Anne", "Dr. Mahmoud"),
                        Car.withGasColourPassengers(6, "Red", "Ender", "Hyrum", "Locke", "Bonzo")
                ));

        System.out.println("----------------------------------------------------------------");
        // Filter out cars that have fuel levels of six or less
        cars.stream().filter(car -> car.getGasLevel() > 6)
                // Map from a Car to a String - a description of the car
                .map(car -> car.getPassengers().get(0) + " is driving a " + car.getColour()
                        + " car with lots of fuel")
                .forEach(description -> System.out.println("> " + description));

        System.out.println("----------------------------------------------------------------");
        // Add gas to all the cars, but without modifying the original cars
        cars.stream().map(car -> car.addGas(4)).forEach(car -> System.out.println("> " + car));

        System.out.println("----------------------------------------------------------------");
        // Print out a list of all the passengers in all the cars
        cars.stream()
            .flatMap(car -> car.getPassengers().stream())
            .forEach(passenger -> System.out.println("> " + passenger));

        System.out.println("----------------------------------------------------------------");
        // Print out a list of all the passengers and which car they were in (by colour).
        // This preserves the boundaries between the different cars.
        cars.stream()
                .flatMap(car -> car.getPassengers().stream()
                // Map each passenger (a string) to a string containing information about the car.
                // We are still within the flatMap operation here
                .map(passenger -> passenger + " is riding in a " + car.getColour() + " car"))
                .forEach(description -> System.out.println(">  " + description));

        // Print out the original list of cars
        System.out.println("----------------------------------------------------------------");
        cars.stream().forEach(car -> System.out.println("> " + car));

    }
}
