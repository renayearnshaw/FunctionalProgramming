import java.util.OptionalInt;
import java.util.stream.IntStream;

public class SumIntStream {
    public static void main(String[] args) {
        // Generate an infinite stream of integer values starting at  0 and incrementing by 1
        OptionalInt sum = IntStream.iterate(0, i -> i + 1)
            // limit the stream to the first 100 values - 0 to 99
            .limit(100)
            // Sum the values with a reduce operation
            .reduce((a, b) -> a + b);

        // The stream above returns an Optional
        sum.ifPresent(s -> System.out.println("Sum is " + s));
    }
}
