import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamVersion {
    public static void main(String[] args) {
        List<String> strings = new ArrayList<>(
                Arrays.asList("Red", "yellow", "pink", "Green", "orange", "purple", "blue")
        );

        // Iterate over the list using the stream method in List
        strings.stream().forEach(s -> System.out.println("> " + s));

        // Create a stream that only contains strings that start with an upper case letter
        Stream<String> upperCase =
                strings.stream().filter(s -> Character.isUpperCase(s.charAt(0)));

        System.out.println("----------------------------------------------------------------");
        upperCase.forEach(s -> System.out.println("> " + s));

        // Check that the original list is unchanged
        System.out.println("----------------------------------------------------------------");
        strings.stream().forEach(s -> System.out.println("> " + s));

    }
}
