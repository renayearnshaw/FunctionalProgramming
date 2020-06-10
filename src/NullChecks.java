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

        String owner = "Sheila";

        // Using the monad Optional to wrap our map
        Optional<Map<String, Car>> ownersOpt = Optional.of(owners);

        ownersOpt
            // Map the Map to a Car, if one is found for the owner
            .map(map -> map.get(owner))
            .map(
                // Map the car to a list of strings describing the boot contents - this is an Optional
                car -> car.getBootContents()
                // Map the list of strings describing the boot contents to a single string, if they exist..
                .map(bootContents -> bootContents.toString())
                // ..or to a alternative string value if they don't
                .orElse("nothing"))
            // Map the string describing the boot contents to another string
            .map(bootContents -> owner + " has " + bootContents + " in the car")
            // print out the description if one exists
            .ifPresent(description -> System.out.println(description));
    }
}
