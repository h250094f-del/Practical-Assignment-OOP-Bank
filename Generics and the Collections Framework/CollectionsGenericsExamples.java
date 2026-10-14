import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CollectionsGenericsExamples {

    public static void main(String[] args) {
        demonstrateArrayList();
        demonstrateSet();
        demonstrateGenericStack();
        demonstrateWordFrequencies();
        demonstrateBubbleSort();
    }

    private static void demonstrateArrayList() {
        System.out.println("ARRAYLIST NUMBERS");
        List<Integer> numbers = new ArrayList<>(Arrays.asList(
                12, 5, 8, 21, 3, 17, 9, 14, 6, 10));

        int sum = 0;
        int maximum = numbers.get(0);
        for (int number : numbers) {
            sum += number;
            if (number > maximum) {
                maximum = number;
            }
        }

        List<Integer> reversed = new ArrayList<>(numbers);
        java.util.Collections.reverse(reversed);

        System.out.println("Numbers: " + numbers);
        System.out.println("Sum: " + sum);
        System.out.println("Maximum: " + maximum);
        System.out.println("Reverse: " + reversed);
    }

    private static void demonstrateSet() {
        System.out.println("\nUNIQUE NAMES");
        List<String> names = Arrays.asList(
                "Amina", "Brian", "Amina", "Chloe", "Brian", "Darius");
        Set<String> uniqueNames = new LinkedHashSet<>(names);
        System.out.println("Original: " + names);
        System.out.println("Without duplicates: " + uniqueNames);
    }

    private static void demonstrateGenericStack() {
        System.out.println("\nGENERIC STACK");
        Stack<String> stack = new Stack<>();
        stack.push("first");
        stack.push("second");
        System.out.println("Top: " + stack.peek());
        System.out.println("Removed: " + stack.pop());
        System.out.println("Is empty: " + stack.isEmpty());
    }

    private static void demonstrateWordFrequencies() {
        System.out.println("\nWORD FREQUENCIES");
        System.out.print("Enter a paragraph: ");
        try (Scanner scanner = new Scanner(System.in)) {
            String paragraph = scanner.hasNextLine() ? scanner.nextLine() : "";
            Map<String, Integer> frequencies = new TreeMap<>();

            for (String word : paragraph.toLowerCase().split("[^\\p{L}\\p{N}]+")) {
                if (!word.isEmpty()) {
                    frequencies.merge(word, 1, Integer::sum);
                }
            }

            List<Map.Entry<String, Integer>> sortedFrequencies =
                    frequencies.entrySet().stream()
                            .sorted(Map.Entry.<String, Integer>comparingByValue(
                                    Comparator.reverseOrder())
                                    .thenComparing(Map.Entry.comparingByKey()))
                            .collect(Collectors.toList());

            if (sortedFrequencies.isEmpty()) {
                System.out.println("No words found.");
            } else {
                for (Map.Entry<String, Integer> entry : sortedFrequencies) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }
            }
        }
    }

    private static void demonstrateBubbleSort() {
        System.out.println("\nGENERIC BUBBLE SORT");
        Integer[] numbers = {5, 1, 4, 2, 8};
        String[] words = {"pear", "apple", "orange", "banana"};

        bubbleSort(numbers);
        bubbleSort(words);

        System.out.println("Integers: " + Arrays.toString(numbers));
        System.out.println("Strings: " + Arrays.toString(words));
    }

    private static <T extends Comparable<T>> void bubbleSort(T[] arr) {
        for (int end = arr.length - 1; end > 0; end--) {
            boolean swapped = false;
            for (int i = 0; i < end; i++) {
                if (arr[i].compareTo(arr[i + 1]) > 0) {
                    T current = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = current;
                    swapped = true;
                }
            }
            if (!swapped) {
                return;
            }
        }
    }

    private static class Stack<T> {
        private final List<T> items = new ArrayList<>();

        private void push(T item) {
            items.add(item);
        }

        private T pop() {
            if (isEmpty()) {
                throw new IllegalStateException("cannot pop from an empty stack");
            }
            return items.remove(items.size() - 1);
        }

        private T peek() {
            if (isEmpty()) {
                throw new IllegalStateException("cannot peek at an empty stack");
            }
            return items.get(items.size() - 1);
        }

        private boolean isEmpty() {
            return items.isEmpty();
        }
    }
}
