import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

interface CarCriterion {
    boolean test(Car car);
}

public class CarScratch {
    public static void main(String[] args) {
        List<Car> cars = Arrays.asList(
          Car.withGasColourPassengers(6, "Red", "Fred", "Jim", "Sheila"),
          Car.withGasColourPassengers(3, "Octarine", "Rincewind", "Ridcully"),
          Car.withGasColourPassengers(9, "Black", "Weatherwax", "Magrat"),
          Car.withGasColourPassengers(7, "Green", "Valentine", "Gillian", "Anne", "Dr. Mahmoud"),
          Car.withGasColourPassengers(6, "Red", "Ender", "Hyrum", "Locke", "Bonzo")
        );

        showAll(cars);
        showAll(getCarsByCriterion(cars, new Car.RedCarCriterion()));
        showAll(getCarsByCriterion(cars, new Car.GasLevelCriterion(6)));

        // Prove that we haven't changed the initial list
        showAll(cars);
    }

    public static void showAll(List<Car> cars) {
        for (Car car: cars) {
            System.out.println(car);
        }
        System.out.println("-----------------------------------------------");
    }

    // Returns a new list without modifying the existing one. The selection criteria is
    // passed in as a object that specifies the required behaviour.
    public static List<Car> getCarsByCriterion(Iterable<Car> in, CarCriterion criterion) {
        List<Car> filtered = new ArrayList<>();
        for (Car car: in) {
            if (criterion.test(car)) {
                filtered.add(car);
            }
        }
        return filtered;
    }
}
