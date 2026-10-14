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
        demonstrateLevelEnum();
        demonstrateStudentRecord();
        demonstrateBookObjectContract();
        demonstrateCustomLinkedList();
        demonstrateCoinChange();
    }

    private static void demonstrateLevelEnum() {
        System.out.println("LEVEL ADVICE");
        for (Level level : Level.values()) {
            switch (level) {
                case LOW:
                    System.out.println(level + ": Take your time.");
                    break;
                case MEDIUM:
                    System.out.println(level + ": Stay attentive.");
                    break;
                case HIGH:
                    System.out.println(level + ": Act immediately.");
                    break;
            }
        }
    }

    private static void demonstrateStudentRecord() {
        System.out.println("\nSTUDENT RECORD");
        Student first = new Student("Amina", 20);
        Student equal = new Student("Amina", 20);

        System.out.println(first);
        System.out.println("Records equal: " + first.equals(equal));
        System.out.println("Name component: " + first.name());
    }

    private static void demonstrateBookObjectContract() {
        System.out.println("\nBOOK ISBN EQUALITY");
        Set<Book> books = new HashSet<>();
        books.add(new Book("978-0134685991", "Effective Java"));
        boolean addedDuplicate = books.add(
                new Book("978-0134685991", "A Different Title"));

        System.out.println("Added duplicate ISBN: " + addedDuplicate);
        System.out.println("Books in set: " + books.size());
    }

    private static void demonstrateCustomLinkedList() {
        System.out.println("\nCUSTOM LINKED LIST");
        LinkedList<String> names = new LinkedList<>();
        names.add("Amina");
        names.add("Brian");
        names.add("Chloe");

        for (String name : names) {
            System.out.println(name);
        }
    }

    private static void demonstrateCoinChange() {
        System.out.println("\nCOIN CHANGE");
        int amount = 87;
        Map<Coin, Integer> change = Coin.fewestCoins(amount);
        System.out.println("Amount: " + amount + " cents");
        for (Map.Entry<Coin, Integer> entry : change.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private enum Level {
        LOW,
        MEDIUM,
        HIGH
    }

    private record Student(String name, int age) {
    }

    private static final class Book {
        private final String isbn;
        private final String title;

        private Book(String isbn, String title) {
            this.isbn = Objects.requireNonNull(isbn, "isbn must not be null");
            this.title = Objects.requireNonNull(title, "title must not be null");
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Book)) {
                return false;
            }
            Book book = (Book) other;
            return isbn.equals(book.isbn);
        }

        @Override
        public int hashCode() {
            return isbn.hashCode();
        }

        @Override
        public String toString() {
            return title + " [" + isbn + "]";
        }
    }

    private static final class LinkedList<E> implements Iterable<E> {
        private Node<E> head;
        private Node<E> tail;

        private void add(E value) {
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

        private static final class Node<T> {
            private final T value;
            private Node<T> next;

            private Node(T value) {
                this.value = value;
            }
        }

        private final class ListIterator implements Iterator<E> {
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

    private enum Coin {
        PENNY(1),
        NICKEL(5),
        DIME(10),
        QUARTER(25);

        private final int cents;

        Coin(int cents) {
            this.cents = cents;
        }

        private static Map<Coin, Integer> fewestCoins(int amount) {
            if (amount < 0) {
                throw new IllegalArgumentException("amount must be non-negative");
            }

            Map<Coin, Integer> result = new LinkedHashMap<>();
            int remaining = amount;
            Coin[] coins = values();
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
