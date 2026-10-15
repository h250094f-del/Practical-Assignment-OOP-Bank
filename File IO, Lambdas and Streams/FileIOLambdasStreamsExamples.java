import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FileIOLambdasStreamsExamples {

    public static void main(String[] args) throws IOException {
        // Use the supplied data folder or find the project's sample data.
        Path dataDirectory;
        if (args.length > 0) {
            dataDirectory = Path.of(args[0]);
        } else if (Files.exists(Path.of("learners.csv"))) {
            dataDirectory = Path.of(".");
        } else if (Files.exists(Path.of("File IO, Lambdas and Streams", "learners.csv"))) {
            dataDirectory = Path.of("File IO, Lambdas and Streams");
        } else {
            dataDirectory = Path.of(".");
        }
        Files.createDirectories(dataDirectory);

        demonstrateSaveAndReadLines(dataDirectory);
        demonstratePredicateAndRemoveIf();
        demonstrateTextFileStatistics(dataDirectory);
        demonstrateStudentsAboveAverage();
        demonstrateLearnerReports(dataDirectory);
    }

    private static void demonstrateSaveAndReadLines(Path dataDirectory) throws IOException {
        System.out.println("SAVE AND READ FIVE LINES");
        List<String> lines = Arrays.asList(
                "Java is a strongly typed, object-oriented programming language.",
                "It enables write-once, run-anywhere cross-platform execution.",
                "Modern Java provides lambdas, streams, and functional interfaces.",
                "Records offer concise syntax for immutable data carriers.",
                "Proper exception handling and resource management ensure stability."
        );

        Path linesFile = dataDirectory.resolve("five-lines.txt");
        // Write and read using UTF-8 for consistent text encoding.
        Files.write(linesFile, lines, StandardCharsets.UTF_8);
        System.out.println("Wrote " + lines.size() + " lines to " + linesFile);

        System.out.println("Read back:");
        List<String> readBack = Files.readAllLines(linesFile, StandardCharsets.UTF_8);
        for (int i = 0; i < readBack.size(); i++) {
            System.out.printf("  [%d] %s%n", (i + 1), readBack.get(i));
        }
    }

    private static void demonstratePredicateAndRemoveIf() {
        System.out.println("\nPREDICATE AND REMOVEIF");
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        System.out.println("Original list: " + numbers);

        Predicate<Integer> isEven = number -> number % 2 == 0;
        numbers.removeIf(isEven);
        System.out.println("After removing even numbers: " + numbers);
    }

    private static void demonstrateTextFileStatistics(Path dataDirectory) throws IOException {
        System.out.println("\nTEXT FILE STATISTICS");
        Path textFile = dataDirectory.resolve("five-lines.txt");
        List<String> lines = Files.readAllLines(textFile, StandardCharsets.UTF_8);

        // Stream operations count words and characters without line breaks.
        long wordCount = lines.stream()
                .flatMap(line -> Arrays.stream(line.trim().split("\\s+")))
                .filter(word -> !word.isEmpty())
                .count();

        long characterCount = lines.stream()
                .mapToLong(String::length)
                .sum();

        System.out.println("File: " + textFile);
        System.out.println("Lines: " + lines.size());
        System.out.println("Words: " + wordCount);
        System.out.println("Characters (excluding line breaks): " + characterCount);
    }

    private static void demonstrateStudentsAboveAverage() {
        System.out.println("\nSTUDENTS ABOVE CLASS AVERAGE");
        List<Student> students = Arrays.asList(
                new Student("Zara", 78),
                new Student("Amina", 91),
                new Student("Brian", 83),
                new Student("Chloe", 68)
        );

        double average = students.stream()
                .mapToInt(Student::marks)
                .average()
                .orElseThrow(() -> new IllegalStateException("Class has no students"));

        System.out.printf("Class average: %.2f%n", average);
        System.out.println("Students scoring above average (alphabetical):");
        students.stream()
                .filter(student -> student.marks() > average)
                .sorted(Comparator.comparing(Student::name))
                .forEach(student -> System.out.printf("  - %s (%d marks)%n", student.name(), student.marks()));
    }

    private static void demonstrateLearnerReports(Path dataDirectory) throws IOException {
        System.out.println("\nLEARNER REPORTS");
        Path csvFile = dataDirectory.resolve("learners.csv");
        if (!Files.exists(csvFile)) {
            Path altPath = Path.of("File IO, Lambdas and Streams", "learners.csv");
            if (Files.exists(altPath)) {
                csvFile = altPath;
            }
        }

        List<Learner> learners = Files.readAllLines(csvFile, StandardCharsets.UTF_8)
                .stream()
                .skip(1)
                .filter(line -> !line.isBlank())
                .map(FileIOLambdasStreamsExamples::parseLearner)
                .collect(Collectors.toList());

        Map<String, List<Learner>> byBand = learners.stream()
                .collect(Collectors.groupingBy(
                        Learner::gradeBand,
                        TreeMap::new,
                        Collectors.toList()
                ));

        for (String band : Arrays.asList("A", "B", "C")) {
            List<Learner> bandLearners = byBand.getOrDefault(band, new ArrayList<>());
            bandLearners.sort(Comparator.comparing(Learner::name));

            Path report = dataDirectory.resolve("learners-" + band + ".txt");
            List<String> reportLines = new ArrayList<>();
            reportLines.add("Learners in Grade Band " + band + " (" + bandLearners.size() + " total):");
            for (Learner learner : bandLearners) {
                reportLines.add(String.format("  - %-10s : %d marks", learner.name(), learner.marks()));
            }
            Files.write(report, reportLines, StandardCharsets.UTF_8);
            System.out.println("Generated " + report + " (" + bandLearners.size() + " learner(s))");
        }
    }

    private static Learner parseLearner(String line) {
        // Validate CSV fields before creating a learner record.
        String[] columns = line.split(",", -1);
        if (columns.length != 2 || columns[0].isBlank()) {
            throw new IllegalArgumentException("Invalid learner CSV row: " + line);
        }

        final int marks;
        try {
            marks = Integer.parseInt(columns[1].trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Invalid learner marks in row: " + line, exception);
        }
        if (marks < 0 || marks > 100) {
            throw new IllegalArgumentException("Learner marks must be between 0 and 100: " + line);
        }
        return new Learner(columns[0].trim(), marks);
    }

    public record Student(String name, int marks) {}

    public record Learner(String name, int marks) {
        public String gradeBand() {
            if (marks >= 80) return "A";
            if (marks >= 60) return "B";
            return "C";
        }
    }
}
