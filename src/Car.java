import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    // Factory method returning a singleton
    public static CarCriterion getRedCarCriterion() {
        return RED_CAR_CRITERION;
    }

    // The red car criterion only defines behaviour, so we only need one instance
    // per class. Hence, we make it a singleton. We also use a lambda expression.
    private static CarCriterion RED_CAR_CRITERION = (car) -> {
            return car.getColour().equals("Red");
    };

    // This object defines state as well as behaviour
    static class GasLevelCriterion implements CarCriterion {
        private int threshold;
        public GasLevelCriterion (int threshold) {
            this.threshold = threshold;
        }
        @Override
        public boolean test(Car car) {
            return car.getGasLevel() >= threshold;
        }
    }

}
