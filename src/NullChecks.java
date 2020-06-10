import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class NullChecks {
    public static void main(String[] args) {
        Map<String, Car> owners = new HashMap<>();
        owners.put("Sheila", Car.withGasColourPassengers(
                6, "Red", "Fred", "Jim", "Sheila"));
        owners.put("Librarian", Car.withGasColourPassengers(
                3, "Octarine", "Rincewind", "Ridcully"));
        owners.put("Ogg", Car.withGasColourPassengersandBoot(
                9, "Black", "Weatherwax", "Magrat"));

        // Traditional version
        String owner = "Ogg";
        Car car = owners.get(owner);
        if (car != null) {
            List<String> bootContents = car.getBootContents();
            if (bootContents != null) {
                System.out.println(owner + " has " + bootContents.toString() + " in the car");
            }
        }

        System.out.println("---------------------------------------");

        // Using the monad Optional to wrap our map
        Optional<Map<String, Car>> ownersOpt = Optional.of(owners);
        ownersOpt
            // Map the Map to a Car, if one is found for the owner
            .map(map -> map.get("Ogg"))
            // Map the car to a List of strings - the boot contents - if the car has any
            .map(c -> c.getBootContents())
            // Map the boot contents to a descriptive string, if they existe
            .map(bootContents -> owner + " has " + bootContents.toString() + " in the car")
            // print out the description if one exists
            .ifPresent(description -> System.out.println(description));
    }
}
