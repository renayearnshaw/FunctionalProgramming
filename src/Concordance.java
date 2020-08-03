import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

// Build a 'Concordance' - something that lists the frequency of words in a text file
public class Concordance {
    public static void main(String[] args) {
        // Build a list of files to process
        List<String> fileNames = Arrays.asList(
            "PrideAndPrejudice.txt",
            "BadBook.txt",
            "Emma.txt",
            "SenseAndSensibility.txt");

        // Build a regular expression that matches one or more non-word characters
        Pattern pattern = Pattern.compile("\\W+");
        // Build a filter that removes empty strings
        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> notEmpty = isEmpty.negate();

        // Build a map of the words in a text file and the number of occurrences of that word
        Map<String, Long> countOfWords =
            // Read the file names as a stream
            fileNames.stream()
            // map each file name to a Path
            .map(Paths::get)
            // Read each text file as a stream of lines
            .flatMap(Files::lines)
            // Map each line to a stream of the words it contains
            .flatMap(pattern::splitAsStream)
            // Ignore any empty strings that result from the regular expression including paragraph indentations
            .filter(notEmpty)
            // Words with different cases count as the same word, so map them all to lower-case
            .map(String::toLowerCase)
            // Use a Collector object to group words according to...
            .collect(Collectors.groupingBy(
                // the word itself...
                Function.identity(),
                // and the number of times it occurs
                Collectors.counting()));

        // Build a comparator that sorts the map created above in descending order.
        // Use the factory method in Map.Entry for comparing by value
        Comparator<Map.Entry<String,Long>> orderByValue = Map.Entry.comparingByValue();
        // Reverse the order of the comparator
        Comparator<Map.Entry<String,Long>> reverseOrderByValue  = orderByValue.reversed();

        countOfWords.entrySet().stream()
            // Sort by the count of words
            .sorted(reverseOrderByValue)
            .limit(200)
            // Format the word count information for printing
            .map(entry -> String.format("%20s: %d", entry.getKey(), entry.getValue()) )
            .forEach(System.out::println);
    }
}
