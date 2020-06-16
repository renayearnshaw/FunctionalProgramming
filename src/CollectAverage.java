import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

public class CollectAverage {
    public static void main(String[] args) {
        Averager result =
            // The generate method takes a DoubleSupplier, which supplies a new double value
            // each time it's called. Our supplier creates a double between + and - pi. We use
            // ThreadLocalRandom to generate a random number because it supports concurrency,
            // even though this stream runs in sequential mode.
            DoubleStream.generate(() -> ThreadLocalRandom.current().nextDouble(-Math.PI, +Math.PI))
                // limit the stream to produce 1000 values
                .limit(1000)
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
        System.out.println("Average is " + result.get());
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
