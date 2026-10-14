import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class AbstractionExamples {

    public static void main(String[] args) {
        demonstrateShapes();
        demonstrateFlyable();
        demonstrateAccounts();
        demonstrateStudentSorting();
        demonstrateDefaultMethodConflict();
    }

    private static void demonstrateShapes() {
        System.out.println("SHAPES");
        Shape[] shapes = {
            new Circle(3.0),
            new Rectangle(4.0, 5.0),
            new Square(2.0)
        };

        for (Shape shape : shapes) {
            System.out.printf(
                    "%s: area=%.2f, perimeter=%.2f%n",
                    shape.getClass().getSimpleName(), shape.area(), shape.perimeter());
        }
    }

    private static void demonstrateFlyable() {
        System.out.println("\nFLYABLE");
        Flyable[] flyers = {new Bird(), new Aeroplane()};
        for (Flyable flyer : flyers) {
            flyer.fly();
        }
    }

    private static void demonstrateAccounts() {
        System.out.println("\nACCOUNTS");
        Account[] accounts = {
            new SavingsAccount(1000.0),
            new CurrentAccount(1000.0)
        };

        for (Account account : accounts) {
            account.deposit(100.0);
            System.out.printf(
                    "%s balance=%.2f, monthly fee=%.2f%n",
                    account.getClass().getSimpleName(),
                    account.getBalance(),
                    account.monthlyFee());
        }
    }

    private static void demonstrateStudentSorting() {
        System.out.println("\nSTUDENT SORTING");
        List<Student> students = new ArrayList<>(Arrays.asList(
                new Student("Zara", 91),
                new Student("Amina", 85),
                new Student("Brian", 85)));

        students.sort(null);
        System.out.println("By name: " + students);

        students.sort(Comparator.comparingInt(Student::getMarks));
        System.out.println("By marks (ascending): " + students);
    }

    private static void demonstrateDefaultMethodConflict() {
        System.out.println("\nDEFAULT METHOD CONFLICT");
        new HelloBoth().hello();
    }

    private abstract static class Shape {
        public abstract double area();

        public abstract double perimeter();
    }

    private static class Circle extends Shape {
        private final double radius;

        private Circle(double radius) {
            this.radius = radius;
        }

        @Override
        public double area() {
            return Math.PI * radius * radius;
        }

        @Override
        public double perimeter() {
            return 2.0 * Math.PI * radius;
        }
    }

    private static class Rectangle extends Shape {
        private final double width;
        private final double height;

        private Rectangle(double width, double height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public double area() {
            return width * height;
        }

        @Override
        public double perimeter() {
            return 2.0 * (width + height);
        }
    }

    private static class Square extends Shape {
        private final double side;

        private Square(double side) {
            this.side = side;
        }

        @Override
        public double area() {
            return side * side;
        }

        @Override
        public double perimeter() {
            return 4.0 * side;
        }
    }

    private interface Flyable {
        void fly();
    }

    private static class Bird implements Flyable {
        @Override
        public void fly() {
            System.out.println("Bird flies by flapping its wings.");
        }
    }

    private static class Aeroplane implements Flyable {
        @Override
        public void fly() {
            System.out.println("Aeroplane flies using lift from its wings.");
        }
    }

    private abstract static class Account {
        private double balance;

        private Account(double openingBalance) {
            if (!Double.isFinite(openingBalance) || openingBalance < 0.0) {
                throw new IllegalArgumentException(
                        "opening balance must be finite and non-negative");
            }
            balance = openingBalance;
        }

        public final void deposit(double amount) {
            if (!Double.isFinite(amount) || amount <= 0.0) {
                throw new IllegalArgumentException(
                        "deposit must be finite and positive");
            }
            balance += amount;
        }

        public final double getBalance() {
            return balance;
        }

        public abstract double monthlyFee();
    }

    private static class SavingsAccount extends Account {
        private SavingsAccount(double openingBalance) {
            super(openingBalance);
        }

        @Override
        public double monthlyFee() {
            return 0.0;
        }
    }

    private static class CurrentAccount extends Account {
        private static final double MONTHLY_FEE = 20.0;

        private CurrentAccount(double openingBalance) {
            super(openingBalance);
        }

        @Override
        public double monthlyFee() {
            return MONTHLY_FEE;
        }
    }

    private static class Student implements Comparable<Student> {
        private final String name;
        private final int marks;

        private Student(String name, int marks) {
            this.name = name;
            this.marks = marks;
        }

        private String getName() {
            return name;
        }

        private int getMarks() {
            return marks;
        }

        @Override
        public int compareTo(Student other) {
            return name.compareTo(other.name);
        }

        @Override
        public String toString() {
            return name + " (" + marks + ")";
        }
    }

    private interface FirstHello {
        default void hello() {
            System.out.println("Hello from FirstHello.");
        }
    }

    private interface SecondHello {
        default void hello() {
            System.out.println("Hello from SecondHello.");
        }
    }

    private static class HelloBoth implements FirstHello, SecondHello {
        @Override
        public void hello() {
            FirstHello.super.hello();
            SecondHello.super.hello();
            System.out.println("Conflict resolved in HelloBoth.");
        }
    }
}
