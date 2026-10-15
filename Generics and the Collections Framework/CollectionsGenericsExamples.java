import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CollectionsGenericsExamples {

    public static void main(String[] args) {
        // Run examples using collections, generic types, and algorithms.
        demonstrateArrayList();
        demonstrateSet();
        demonstrateGenericStack();
        demonstrateWordFrequencies(args);
        demonstrateBubbleSort();
    }

    private static void demonstrateArrayList() {
        System.out.println("ARRAYLIST NUMBERS");
        List<Integer> numbers = new ArrayList<>(Arrays.asList(
                12, 5, 8, 21, 3, 17, 9, 14, 6, 10
        ));

        int sum = 0;
        int maximum = numbers.get(0);
        for (int number : numbers) {
            sum += number;
            if (number > maximum) {
                maximum = number;
            }
        }

        List<Integer> reversed = new ArrayList<>(numbers);
        Collections.reverse(reversed);

        System.out.println("Numbers: " + numbers);
        System.out.println("Sum: " + sum);
        System.out.println("Maximum: " + maximum);
        System.out.println("Reversed: " + reversed);
    }

    private static void demonstrateSet() {
        System.out.println("\nUNIQUE NAMES");
        List<String> names = Arrays.asList(
                "Amina", "Brian", "Amina", "Chloe", "Brian", "Darius"
        );
        // LinkedHashSet removes duplicates while preserving first-seen order.
        Set<String> uniqueNames = new LinkedHashSet<>(names);
        System.out.println("Original: " + names);
        System.out.println("Without duplicates: " + uniqueNames);
    }

    private static void demonstrateGenericStack() {
        System.out.println("\nGENERIC STACK");
        Stack<String> stack = new Stack<>();
        stack.push("Alpha");
        stack.push("Beta");
        stack.push("Gamma");

        System.out.println("Top element (peek): " + stack.peek());
        System.out.println("Popped element: " + stack.pop());
        System.out.println("Current top: " + stack.peek());
        System.out.println("Stack empty? " + stack.isEmpty());
        System.out.println("Stack size: " + stack.size());
    }

    private static void demonstrateWordFrequencies(String[] args) {
        System.out.println("\nWORD FREQUENCIES");
        String paragraph = args.length > 0 && !args[0].isBlank()
                ? args[0]
                : "Java is an object-oriented programming language. Java is robust, secure, and versatile. Programming in Java is fun.";

        System.out.println("Analyzing paragraph: \"" + paragraph + "\"");
        Map<String, Integer> frequencies = new TreeMap<>();

        for (String word : paragraph.toLowerCase().split("[^\\p{L}\\p{N}]+")) {
            if (!word.isEmpty()) {
                frequencies.merge(word, 1, Integer::sum);
            }
        }

        List<Map.Entry<String, Integer>> sortedFrequencies =
                frequencies.entrySet().stream()
                        .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                                .thenComparing(Map.Entry.comparingByKey()))
                        .collect(Collectors.toList());

        System.out.println("Word Counts (sorted by frequency descending, then alphabetically):");
        for (Map.Entry<String, Integer> entry : sortedFrequencies) {
            System.out.printf("  - %-12s : %d%n", entry.getKey(), entry.getValue());
        }
    }

    private static void demonstrateBubbleSort() {
        System.out.println("\nGENERIC BUBBLE SORT");
        Integer[] numbers = {5, 1, 9, 4, 2, 8};
        String[] words = {"pear", "apple", "orange", "banana"};

        System.out.println("Before sorting integers: " + Arrays.toString(numbers));
        bubbleSort(numbers);
        System.out.println("After sorting integers:  " + Arrays.toString(numbers));

        System.out.println("Before sorting strings:  " + Arrays.toString(words));
        bubbleSort(words);
        System.out.println("After sorting strings:   " + Arrays.toString(words));
    }

    public static <T extends Comparable<T>> void bubbleSort(T[] arr) {
        // Stop early when a pass makes no swaps.
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

    public static class Stack<T> {
        // The final list element is the top of this last-in, first-out stack.
        private final List<T> items = new ArrayList<>();

        public void push(T item) {
            items.add(item);
        }

        public T pop() {
            if (isEmpty()) {
                throw new IllegalStateException("Cannot pop from an empty stack");
            }
            return items.remove(items.size() - 1);
        }

        public T peek() {
            if (isEmpty()) {
                throw new IllegalStateException("Cannot peek at an empty stack");
            }
            return items.get(items.size() - 1);
        }

        public boolean isEmpty() {
            return items.isEmpty();
        }

        public int size() {
            return items.size();
        }
    }
}
