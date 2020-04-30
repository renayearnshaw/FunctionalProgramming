import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        showAll(getCarsOfColour(cars, "Black"));
        showAll(cars);
    }

    public static void showAll(List<Car> cars) {
        for (Car car: cars) {
            System.out.println(car);
        }
        System.out.println("-----------------------------------------------");
    }

    // Returns a new list without modifying the existing one
    public static List<Car> getCarsOfColour(List<Car> in, String colour) {
        List<Car> filtered = new ArrayList<>();
        for (Car car: in) {
            if (car.getColour() == colour) {
                filtered.add(car);
            }
        }
        return filtered;
    }
}
