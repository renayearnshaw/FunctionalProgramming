import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@FunctionalInterface
interface Criterion<E> {
    boolean test(E car);
}

public class CarScratch {

    public static <E> Criterion<E> negate(Criterion<E> criterion) {
        return x -> !criterion.test(x);
    }

    public static <E> Criterion<E> or(Criterion<E> criterion1, Criterion<E> criterion2) {
        return x -> criterion1.test(x) || criterion2.test(x);
    }

    public static <E> Criterion<E> and(Criterion<E> criterion1, Criterion<E> criterion2) {
        return x -> criterion1.test(x) && criterion2.test(x);
    }

    public static void main(String[] args) {
        List<Car> cars = Arrays.asList(
          Car.withGasColourPassengers(6, "Red", "Fred", "Jim", "Sheila"),
          Car.withGasColourPassengers(3, "Octarine", "Rincewind", "Ridcully"),
          Car.withGasColourPassengers(9, "Black", "Weatherwax", "Magrat"),
          Car.withGasColourPassengers(7, "Green", "Valentine", "Gillian", "Anne", "Dr. Mahmoud"),
          Car.withGasColourPassengers(6, "Red", "Ender", "Hyrum", "Locke", "Bonzo")
        );

        showAll(cars);

        Criterion<Car> green = Car.getColourCriterion(new String[] {"Green"});
        Criterion<Car> octarine = Car.getColourCriterion(new String[] {"Octarine"});
        Criterion<Car> greenOrOctarine = or(green, octarine);
        showAll(getByCriterion(cars, greenOrOctarine));
        Criterion<Car> notGreenOrOctarine = negate(greenOrOctarine);
        showAll(getByCriterion(cars, notGreenOrOctarine));

        Criterion<Car> gas6OrMore = Car.getGasLevelCriterion(6);
        Criterion<Car> gas6OrMoreAndGreen = and(gas6OrMore, green);
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
    public static <E> List<E> getByCriterion(Iterable<E> input, Criterion<E> criterion) {
        List<E> filtered = new ArrayList<>();
        for (E each: input) {
            if (criterion.test(each)) {
                filtered.add(each);
            }
        }
        return filtered;
    }
}
