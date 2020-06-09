import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NullChecks {
    public static void main(String[] args) {
        Map<String, Car> owners = new HashMap<>();
        owners.put("Sheila", Car.withGasColourPassengers(
                6, "Red", "Fred", "Jim", "Sheila"));
        owners.put("Librarian", Car.withGasColourPassengers(
                3, "Octarine", "Rincewind", "Ridcully"));
        owners.put("Ogg", Car.withGasColourPassengers(
                9, "Black", "Weatherwax", "Magrat"));

        String owner = "Ogg";
        Car car = owners.get(owner);
        if (car != null) {
            List<String> bootContents = car.getBootContents();
            if (bootContents != null) {
                System.out.println(owner + " has " + bootContents.toString() + " in the car");
            }
        }
    }
}
