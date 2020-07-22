import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

// Build a 'Concordance' - something that lists the frequency of words in a text file
public class Concordance {
    public static void main(String[] args) {
        try {
            // Get the path to the file we want to read
            Path path = Paths.get("PrideAndPrejudice.txt");

            // Read the text file as a stream and count the number of lines it contains
            Stream<String> streamOfLines = Files.lines(path);
            long noOfLines = streamOfLines.count();
            System.out.println("No of lines in " + path.toString() + " is " + noOfLines);
            System.out.println("-------------------------------------------");

            // Read the text file as a stream and count the number of words it contains
            Pattern pattern = Pattern.compile("\\W+");
            Predicate<String> isEmpty = String::isEmpty;
            Predicate<String> notEmpty = isEmpty.negate();
            streamOfLines = Files.lines(path)
                // map each line to a stream of the words it contains
                .flatMap(line -> pattern.splitAsStream(line));
            // Count the number of words in the stream, but ignore any empty words that result from
            // the regular expression not dealing with paragraph indentations correctly
            long noOfWords = streamOfLines.filter(notEmpty).count();
            System.out.println("No of words in " + path.toString() + " is " + noOfWords);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
