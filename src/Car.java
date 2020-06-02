import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class Car {
    private final int gasLevel;
    private final String colour;
    private final List<String> passengers;
    private final List<String> bootContents;

    // Constructor is private
    private Car(int gasLevel, String colour, List<String> passengers, List<String> bootContents) {
        this.gasLevel = gasLevel;
        this.colour = colour;
        this.passengers = passengers;
        this.bootContents = bootContents;
    }

    // Prefer factory methods to constructors
    public static Car withGasColourPassengers(
            int gas, String colour, String... passengers) {
        // Create an immutable list of customers
        List<String> p = Collections.unmodifiableList(Arrays.asList(passengers));
        Car self = new Car(gas, colour, p, null);
        return self;
    }

    // Multiple factory methods can have the same parameters
    public static Car withGasColourPassengersandBoot(
            int gas, String colour, String... passengers) {
        // Create an immutable list of customers
        List<String> p = Collections.unmodifiableList(Arrays.asList(passengers));
        Car self = new Car(gas, colour, p, Arrays.asList("jack", "wrench", "spare wheel"));
        return self;
    }

    public int getGasLevel() {
        return gasLevel;
    }

    public String getColour() {
        return colour;
    }

    public List<String> getPassengers() {
        return passengers;
    }

    public List<String> getBootContents() {
        return bootContents;
    }

    @Override
    public String toString() {
        return "Car{" +
                "gasLevel=" + gasLevel +
                ", colour='" + colour + '\'' +
                ", passengers=" + passengers +
                (bootContents != null ? ", bootContents=" + bootContents : ", nothing in boot") +
                '}';
    }

    // The type of the lambda is specified by the return type of the function
    public static Predicate<Car> getFourPassengersCriterion() {
        return car -> car.passengers.size() == 4;
    };

    // Factory method returning a singleton
    public static Predicate<Car> getRedCarCriterion() {
        return RED_CAR_CRITERION;
    }

    // The red car criterion only defines behaviour, not state, so we only need one instance
    // per class. Hence, we make it a singleton. We also use an expression lambda.
    private static Predicate<Car> RED_CAR_CRITERION = car -> car.colour.equals("Red");

    public static Predicate<Car> getColourCriterion(String... colours) {
        return car -> {
            for (String colour: colours) {
                if (car.colour == colour) {
                    return true;
                }
            }
            return false;
        };
    }

    public static Predicate<Car> getGasLevelCriterion(int threshold) {
        return car -> car.getGasLevel() >= threshold;
    }

    public static Comparator<Car> getGasLevelComparator() {
        return GAS_LEVEL_COMPARATOR;
    }

    // This sort only defines behaviour, not state, so we only need one instance
    // per class. Hence, we make it a singleton. We also use an expression lambda.
    private static final Comparator<Car> GAS_LEVEL_COMPARATOR = (o1, o2) -> o1.gasLevel - o2.gasLevel;
}
