import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

// Build a 'Concordance' - something that lists the frequency of words in a text file
public class Concordance {
    public static void main(String[] args) {
        try {
            // Get the path to the file we want to read
            Path path = Paths.get("PrideAndPrejudice.txt");

            // Build a regular expression that matches one or more non-word characters
            Pattern pattern = Pattern.compile("\\W+");
            // Build a filter that removes empty strings
            Predicate<String> isEmpty = String::isEmpty;
            Predicate<String> notEmpty = isEmpty.negate();

            // Read the text file as a stream of strings
            Map<String, Long> countOfWords = Files.lines(path)
                // map each line to a stream of the words it contains
                .flatMap(line -> pattern.splitAsStream(line))
                // ignore any empty strings that result from the regular expression including paragraph indentations
                .filter(notEmpty)
                // Use a Collector object to group words according to...
                .collect(Collectors.groupingBy(
                    // the word itself...
                    Function.identity(),
                    // and the number of times it occurs
                    Collectors.counting()));
            // Build a comparator that sorts the map created above in descending order.
            // Use the factory method in Map.Entry for comparing by value
            Comparator<Map.Entry<String,Long>> comparator = Map.Entry.comparingByValue();
            // Reverse the order of the comparator
            comparator = comparator.reversed();
            countOfWords.entrySet().stream()
                // Sort by the count of words
                .sorted(comparator)
                .limit(200)
                .forEach(entry -> System.out.println(entry.getKey() + " is found " + entry.getValue() + " times."));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
