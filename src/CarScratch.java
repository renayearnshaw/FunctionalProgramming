import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

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

        Predicate<Car> green = Car.getColourCriterion(new String[] {"Green"});
        Predicate<Car> octarine = Car.getColourCriterion(new String[] {"Octarine"});
        Predicate<Car> greenOrOctarine = green.or(octarine);
        showAll(getByCriterion(cars, greenOrOctarine));
        Predicate<Car> notGreenOrOctarine = greenOrOctarine.negate();
        showAll(getByCriterion(cars, notGreenOrOctarine));

        Predicate<Car> gas6OrMore = Car.getGasLevelCriterion(6);
        Predicate<Car> gas6OrMoreAndGreen = gas6OrMore.and(green);
        showAll(getByCriterion(cars, gas6OrMoreAndGreen));

        // Prove that we haven't changed the initial list
        showAll(cars);
    }

    public static <E> void showAll(List<E> list) {
        for (E each: list) {
            System.out.println(each);
        }
        System.out.println("-----------------------------------------------");
    }

    // Returns a new list without modifying the existing one. The selection criteria is
    // passed in as a object that specifies the required behaviour.
    public static <E> List<E> getByCriterion(Iterable<E> input, Predicate<E> criterion) {
        List<E> filtered = new ArrayList<>();
        for (E each: input) {
            if (criterion.test(each)) {
                filtered.add(each);
            }
        }
        return filtered;
    }
}
