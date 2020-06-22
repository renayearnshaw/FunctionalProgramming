import java.util.Arrays;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Student {

    private static final NavigableMap<Integer, String> gradeLetters = new TreeMap<>();

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
        school.forEach(s -> System.out.println(s));
    }
}
