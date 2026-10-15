import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

public class NestedTypesExamples {

    public static void main(String[] args) {
        // Run examples of enums, records, nested classes, and object contracts.
        demonstrateLevelEnum();
        demonstrateStudentRecord();
        demonstrateBookObjectContract();
        demonstrateCustomLinkedList();
        demonstrateCoinChange();
    }

    private static void demonstrateLevelEnum() {
        System.out.println("LEVEL ENUM (SWITCH DISPATCH)");
        for (Level level : Level.values()) {
            String advice = switch (level) {
                case LOW -> "Take your time.";
                case MEDIUM -> "Stay attentive.";
                case HIGH -> "Act immediately.";
            };
            System.out.println("  " + level + ": " + advice);
        }
    }

    private static void demonstrateStudentRecord() {
        System.out.println("\nSTUDENT RECORD (COMPACT IMMUTABLE DATA CARRIER)");
        Student first = new Student("Amina", 20);
        Student equal = new Student("Amina", 20);

        System.out.println("  Record instance: " + first);
        System.out.println("  Records equal?   " + first.equals(equal));
        System.out.println("  Name component:  " + first.name());
        System.out.println("  Age component:   " + first.age());
    }

    private static void demonstrateBookObjectContract() {
        System.out.println("\nBOOK OBJECT CONTRACT (EQUALS & HASHCODE BY ISBN)");
        Set<Book> books = new HashSet<>();
        Book book1 = new Book("978-0134685991", "Effective Java");
        Book book2 = new Book("978-0134685991", "Effective Java (3rd Edition)");

        books.add(book1);
        boolean addedDuplicate = books.add(book2);

        System.out.println("  Added first book:  " + book1);
        System.out.println("  Added duplicate ISBN? " + addedDuplicate);
        System.out.println("  Total unique books in Set: " + books.size());
    }

    private static void demonstrateCustomLinkedList() {
        System.out.println("\nCUSTOM LINKED LIST (STATIC NESTED NODE & INNER ITERATOR)");
        LinkedList<String> names = new LinkedList<>();
        names.add("Amina");
        names.add("Brian");
        names.add("Chloe");

        System.out.println("  Iterating through custom linked list:");
        for (String name : names) {
            System.out.println("    - " + name);
        }
    }

    private static void demonstrateCoinChange() {
        System.out.println("\nCOIN ENUM (GREEDY CHANGE ALGORITHM)");
        int amount = 87;
        Map<Coin, Integer> change = Coin.fewestCoins(amount);
        System.out.println("  Change for " + amount + " cents:");
        for (Map.Entry<Coin, Integer> entry : change.entrySet()) {
            System.out.printf("    %-8s (%2d cents) : %d coin(s)%n",
                    entry.getKey(), entry.getKey().getCents(), entry.getValue());
        }
    }

    public enum Level {
        LOW,
        MEDIUM,
        HIGH
    }

    public record Student(String name, int age) {}

    public static final class Book {
        private final String isbn;
        private final String title;

        public Book(String isbn, String title) {
            this.isbn = Objects.requireNonNull(isbn, "isbn must not be null");
            this.title = Objects.requireNonNull(title, "title must not be null");
        }

        public String getIsbn() {
            return isbn;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public boolean equals(Object other) {
            // Books are equal when their ISBN values match.
            if (this == other) return true;
            if (!(other instanceof Book book)) return false;
            return isbn.equals(book.isbn);
        }

        @Override
        public int hashCode() {
            // Use the same ISBN field as equals.
            return isbn.hashCode();
        }

        @Override
        public String toString() {
            return title + " [ISBN: " + isbn + "]";
        }
    }

    public static final class LinkedList<E> implements Iterable<E> {
        private Node<E> head;
        private Node<E> tail;

        public void add(E value) {
            // Append each new node after the current tail.
            Node<E> node = new Node<>(value);
            if (tail == null) {
                head = node;
            } else {
                tail.next = node;
            }
            tail = node;
        }

        @Override
        public Iterator<E> iterator() {
            return new ListIterator();
        }

        public static final class Node<T> {
            final T value;
            Node<T> next;

            Node(T value) {
                this.value = value;
            }
        }

        public final class ListIterator implements Iterator<E> {
            private Node<E> next = head;

            @Override
            public boolean hasNext() {
                return next != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E value = next.value;
                next = next.next;
                return value;
            }
        }
    }

    public enum Coin {
        PENNY(1),
        NICKEL(5),
        DIME(10),
        QUARTER(25);

        private final int cents;

        Coin(int cents) {
            this.cents = cents;
        }

        public int getCents() {
            return cents;
        }

        public static Map<Coin, Integer> fewestCoins(int amount) {
            if (amount < 0) {
                throw new IllegalArgumentException("Amount must be non-negative");
            }

            Map<Coin, Integer> result = new LinkedHashMap<>();
            int remaining = amount;
            Coin[] coins = values();
            // Greedily use larger denominations before smaller ones.
            for (int i = coins.length - 1; i >= 0; i--) {
                Coin coin = coins[i];
                int count = remaining / coin.cents;
                if (count > 0) {
                    result.put(coin, count);
                    remaining %= coin.cents;
                }
            }
            return Collections.unmodifiableMap(result);
        }
    }
}
