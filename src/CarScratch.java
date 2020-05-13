import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@FunctionalInterface
interface Criterion<E> {
    boolean test(E car);
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
        showAll(getByCriterion(cars, Car.getRedCarCriterion()));
        showAll(getByCriterion(cars, new Car.GasLevelCriterion(6)));

        // Prove that we haven't changed the initial list
        showAll(cars);

        List<String> colours = Arrays.asList("Red", "Yellow", "Pink", "Green", "Orange", "Purple", "Blue");
        showAll(getByCriterion(colours, colour -> colour.length() > 4));

        LocalDate today = LocalDate.now();
        List<LocalDate> dates = Arrays.asList(today, today.plusDays(1), today.plusDays(7), today.minusDays(1));
        showAll(getByCriterion(dates, date -> date.isAfter(today)));
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
