import java.util.*;
import java.util.stream.Collectors;

public class Student {

    private static final NavigableMap<Integer, String> gradeLetters = new TreeMap<>();

    // Grade thesholds
    static {
        gradeLetters.put(90, "A");
        gradeLetters.put(80, "B");
        gradeLetters.put(70, "C");
        gradeLetters.put(60, "D");
        gradeLetters.put(50, "E");
        gradeLetters.put(0, "F");
    }

    private String name;
    private int score;

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    // Represent the percentage score as a letter grade
    public String getLetterGrade() {
        return gradeLetters.floorEntry(score).getValue();
    }

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString() {
        return name + ", " + score + "%, grade is " + getLetterGrade();
    }

    public static void main(String[] args) {
        List<Student> school = Arrays.asList(
            new Student("Fred", 71),
            new Student("Jim", 38),
            new Student("Sheila", 97),
            new Student("Weatherwax", 100),
            new Student("Ogg", 56),
            new Student("Rincewind", 28),
            new Student("Ridcully", 65),
            new Student("Magrat", 79),
            new Student("Valentine", 93),
            new Student("Gillian", 87),
            new Student("Anne", 91),
            new Student("Dr. Mahmoud", 88),
            new Student("Ender", 91),
            new Student("Hyrum", 72),
            new Student("Locke", 91),
            new Student("Bonzo", 57)
        );

        // Print out each student
        school.forEach(s -> System.out.println(s));
        System.out.println("------------------------------------------------------");

        // Build a table of letter grades and the students that attained that grade.
        // The return type of collect() is a map of the key type (the grade) to a list
        // of the data type that the stream consists of (in this case, students)
        Map<String, List<Student>> studentsByGrade = school.stream()
            // Use a Collector object to collect students according the the grade they achieved
            .collect(Collectors.groupingBy(student -> student.getLetterGrade()));

        // Build a comparator that sorts the map created above in descending order.
        // Use the factory method in Map.Entry for comparing by key
        Comparator<Map.Entry<String, List<Student>>> comparator = Map.Entry.comparingByKey();
        // Reverse the order of the comparator
        comparator = comparator.reversed();

        // entrySet() returns a set of key-value pairs, which can be iterated over
        studentsByGrade.entrySet().stream()
            .sorted(comparator)
            .forEach(entry -> System.out.println("Students " + entry.getValue()
                + " have grade " + entry.getKey()));

        System.out.println("------------------------------------------------------");

        Map<String, Long> countOfStudentsByGrade = school.stream()
            .collect(
                // Use a Collector object to collect students according to the grade they achieved...
                Collectors.groupingBy(
                    student -> student.getLetterGrade(),
                    // ...and count them using a downstream collector
                    Collectors.counting()));
        // Sort by the number of students who achieved a grade
        countOfStudentsByGrade.entrySet().stream()
            .sorted(Map.Entry.comparingByValue())
            .forEach(entry -> System.out.println(entry.getValue() + " students have grade " + entry.getKey()));

        System.out.println("------------------------------------------------------");

        Map<String, String> table3 = school.stream()
            .collect(
                // Use a Collector object to collect students according to the grade they achieved...
                Collectors.groupingBy(student -> student.getLetterGrade(),
                    // ... and map them using a downstream collector
                    Collectors.mapping(Student::getName, Collectors.joining(", "))));
        table3.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> System.out.println(entry.getValue() + "have achieved grade " + entry.getKey()));
    }
}
