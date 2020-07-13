import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

public class CollectAverage {
    public static void main(String[] args) {
        long start = System.nanoTime();

        Averager result =
            // The generate method takes a DoubleSupplier, which supplies a new double value
            // each time it's called. Our supplier creates a double between + and - pi. We use
            // ThreadLocalRandom to generate a random number because it supports concurrency.
            DoubleStream.generate(() -> ThreadLocalRandom.current().nextDouble(-Math.PI, Math.PI))
                .parallel()
                // an unordered stream is much faster in parallel mode
                .unordered() // Stream.generate is already unordered so this isn't actually needed
                // limit the stream to produce a number of values
                .limit(200_000_000L)
                // Add some computation to the stream - this makes parallelization more worthwhile
                .map(x -> Math.sin(x))
                .collect(
                    // A Supplier that creates a 'bucket' that contains the data that is mutated.
                    // There is one of these per thread (or sub-stream), so we need to tell the
                    // collect method how to create a new bucket.
                    () -> new Averager(),
                    // A BiConsumer that is used to calculate an intermediate result - this uses
                    // the data item from the stream to update the bucket contents
                    (bucket, item) -> bucket.include(item),
                    // A BiConsumer that is used to combine intermediate buckets to give a final result
                    (bucket1, bucket2) -> bucket1.merge(bucket2));

        long end = System.nanoTime();
        System.out.println("Average is " + result.get() + ", computation took " +
                ((end - start) / 1_000_000) + " ms");
    }
}

// A class to calculate the average of a stream of doubles
class Averager {
    private double total;
    private long count;

    // Create the bucket
    public Averager() {}

    // Include a data item from the stream into the bucket
    public void include(double d) {
        total += d;
        count++;
    }

    // Merge in the result of another bucket
    public void merge(Averager other) {
        total += other.total;
        count += other.count;
    }

    // Return the final average value
    public double get() {
        return total / count;
    }
}
