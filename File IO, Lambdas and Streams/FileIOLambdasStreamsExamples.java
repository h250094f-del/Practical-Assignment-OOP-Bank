import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FileIOLambdasStreamsExamples {

    public static void main(String[] args) throws IOException {
        Path dataDirectory = args.length == 0 ? Path.of(".") : Path.of(args[0]);
        Files.createDirectories(dataDirectory);

        demonstrateSaveAndReadLines(dataDirectory);
        demonstratePredicateAndRemoveIf();
        demonstrateTextFileStatistics(dataDirectory);
        demonstrateStudentsAboveAverage();
        demonstrateLearnerReports(dataDirectory);
    }

    private static void demonstrateSaveAndReadLines(Path dataDirectory) throws IOException {
        System.out.println("SAVE AND READ FIVE LINES");
        List<String> lines = new ArrayList<>();

        try (Scanner scanner = new Scanner(System.in)) {
            for (int lineNumber = 1; lineNumber <= 5; lineNumber++) {
                System.out.print("Enter line " + lineNumber + " of 5: ");
                if (!scanner.hasNextLine()) {
                    throw new IOException("Expected five lines of input; received "
                            + lines.size());
                }
                lines.add(scanner.nextLine());
            }
        }

        Path linesFile = dataDirectory.resolve("five-lines.txt");
        Files.write(linesFile, lines, StandardCharsets.UTF_8);
        System.out.println("Saved to " + linesFile);
        System.out.println("Read back:");
        for (String line : Files.readAllLines(linesFile, StandardCharsets.UTF_8)) {
            System.out.println(line);
        }
    }

    private static void demonstratePredicateAndRemoveIf() {
        System.out.println("\nPREDICATE AND REMOVEIF");
        List<Integer> numbers = new ArrayList<>(
                Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        Predicate<Integer> isEven = number -> number % 2 == 0;
        numbers.removeIf(isEven);
        System.out.println("After removing even numbers: " + numbers);
    }

    private static void demonstrateTextFileStatistics(Path dataDirectory) throws IOException {
        System.out.println("\nTEXT FILE STATISTICS");
        Path textFile = dataDirectory.resolve("five-lines.txt");
        List<String> lines = Files.readAllLines(textFile, StandardCharsets.UTF_8);

        long words = lines.stream()
                .flatMap(line -> Arrays.stream(line.trim().split("\\s+")))
                .filter(word -> !word.isEmpty())
                .count();
        long characters = lines.stream().mapToLong(String::length).sum();

        System.out.println("Lines: " + lines.size());
        System.out.println("Words: " + words);
        System.out.println("Characters (excluding line breaks): " + characters);
    }

    private static void demonstrateStudentsAboveAverage() {
        System.out.println("\nSTUDENTS ABOVE CLASS AVERAGE");
        List<Student> students = Arrays.asList(
                new Student("Zara", 78),
                new Student("Amina", 91),
                new Student("Brian", 83),
                new Student("Chloe", 68));

        double average = students.stream()
                .mapToInt(Student::marks)
                .average()
                .orElseThrow(() -> new IllegalStateException("class has no students"));

        System.out.printf("Class average: %.2f%n", average);
        students.stream()
                .filter(student -> student.marks() > average)
                .map(Student::name)
                .sorted()
                .forEach(System.out::println);
    }

    private static void demonstrateLearnerReports(Path dataDirectory) throws IOException {
        System.out.println("\nLEARNER REPORTS");
        Path csvFile = dataDirectory.resolve("learners.csv");
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
                        Collectors.toList()));

        for (String band : Arrays.asList("A", "B", "C")) {
            List<Learner> bandLearners = byBand.getOrDefault(band, new ArrayList<>());
            bandLearners.sort(Comparator.comparing(Learner::name));

            Path report = dataDirectory.resolve("learners-" + band + ".txt");
            List<String> reportLines = new ArrayList<>();
            reportLines.add("Learners in grade band " + band);
            for (Learner learner : bandLearners) {
                reportLines.add(learner.name() + ", " + learner.marks());
            }
            Files.write(report, reportLines, StandardCharsets.UTF_8);
            System.out.println(report + ": " + bandLearners.size() + " learner(s)");
        }
    }

    private static Learner parseLearner(String line) {
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

    private record Student(String name, int marks) {
    }

    private record Learner(String name, int marks) {
        private String gradeBand() {
            if (marks >= 80) {
                return "A";
            }
            if (marks >= 60) {
                return "B";
            }
            return "C";
        }
    }
}
