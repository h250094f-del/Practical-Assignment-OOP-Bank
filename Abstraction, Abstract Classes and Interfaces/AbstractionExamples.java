import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class AbstractionExamples {

    public static void main(String[] args) {
        // Each method demonstrates a different abstraction feature.
        demonstrateShapes();
        demonstrateFlyable();
        demonstrateAccounts();
        demonstrateStudentSorting();
        demonstrateDefaultMethodConflict();
    }

    private static void demonstrateShapes() {
        System.out.println("SHAPES (ABSTRACT CLASS & POLYMORPHISM)");
        // A Shape reference can hold any concrete shape implementation.
        Shape[] shapes = {
            new Circle(3.0),
            new Rectangle(4.0, 5.0),
            new Square(2.0)
        };

        for (Shape shape : shapes) {
            System.out.printf("  %s: area=%.2f, perimeter=%.2f%n",
                    shape.getClass().getSimpleName(), shape.area(), shape.perimeter());
        }
    }

    private static void demonstrateFlyable() {
        System.out.println("\nFLYABLE (INTERFACE IMPLEMENTATION)");
        Flyable[] flyers = {new Bird(), new Aeroplane()};
        for (Flyable flyer : flyers) {
            System.out.print("  ");
            flyer.fly();
        }
    }

    private static void demonstrateAccounts() {
        System.out.println("\nACCOUNTS (ABSTRACT SUPERCLASS)");
        Account[] accounts = {
            new SavingsAccount(1000.0),
            new CurrentAccount(1000.0)
        };

        for (Account account : accounts) {
            account.deposit(100.0);
            System.out.printf("  %s balance=%.2f, monthly fee=%.2f%n",
                    account.getClass().getSimpleName(),
                    account.getBalance(),
                    account.monthlyFee());
        }
    }

    private static void demonstrateStudentSorting() {
        System.out.println("\nSTUDENT SORTING (COMPARABLE & COMPARATOR)");
        List<Student> students = new ArrayList<>(Arrays.asList(
                new Student("Zara", 91),
                new Student("Amina", 85),
                new Student("Brian", 85)
        ));

        // Natural ordering sorts by name; the comparator then sorts by marks.
        students.sort(null);
        System.out.println("  By name (natural order): " + students);

        students.sort(Comparator.comparingInt(Student::getMarks));
        System.out.println("  By marks (ascending):    " + students);
    }

    private static void demonstrateDefaultMethodConflict() {
        System.out.println("\nDEFAULT METHOD CONFLICT RESOLUTION");
        new HelloBoth().hello();
    }

    public abstract static class Shape {
        // Concrete shapes provide their own formulas for these operations.
        public abstract double area();
        public abstract double perimeter();
    }

    public static class Circle extends Shape {
        private final double radius;

        public Circle(double radius) {
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

    public static class Rectangle extends Shape {
        private final double width;
        private final double height;

        public Rectangle(double width, double height) {
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

    public static class Square extends Shape {
        private final double side;

        public Square(double side) {
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

    public interface Flyable {
        // Implementations define how each object flies.
        void fly();
    }

    public static class Bird implements Flyable {
        @Override
        public void fly() {
            System.out.println("Bird flies by flapping its wings.");
        }
    }

    public static class Aeroplane implements Flyable {
        @Override
        public void fly() {
            System.out.println("Aeroplane flies using lift from its wings.");
        }
    }

    public abstract static class Account {
        private double balance;

        public Account(double openingBalance) {
            if (!Double.isFinite(openingBalance) || openingBalance < 0.0) {
                throw new IllegalArgumentException("Opening balance must be finite and non-negative.");
            }
            balance = openingBalance;
        }

        // Shared deposit logic is final; subclasses supply their monthly fee.
        public final void deposit(double amount) {
            if (!Double.isFinite(amount) || amount <= 0.0) {
                throw new IllegalArgumentException("Deposit must be finite and positive.");
            }
            balance += amount;
        }

        public final double getBalance() {
            return balance;
        }

        public abstract double monthlyFee();
    }

    public static class SavingsAccount extends Account {
        public SavingsAccount(double openingBalance) {
            super(openingBalance);
        }

        @Override
        public double monthlyFee() {
            return 0.0;
        }
    }

    public static class CurrentAccount extends Account {
        private static final double MONTHLY_FEE = 20.0;

        public CurrentAccount(double openingBalance) {
            super(openingBalance);
        }

        @Override
        public double monthlyFee() {
            return MONTHLY_FEE;
        }
    }

    public static class Student implements Comparable<Student> {
        private final String name;
        private final int marks;

        public Student(String name, int marks) {
            this.name = name;
            this.marks = marks;
        }

        public String getName() {
            return name;
        }

        public int getMarks() {
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

    public interface FirstHello {
        default void hello() {
            System.out.println("  Hello from FirstHello.");
        }
    }

    public interface SecondHello {
        default void hello() {
            System.out.println("  Hello from SecondHello.");
        }
    }

    public static class HelloBoth implements FirstHello, SecondHello {
        @Override
        public void hello() {
            FirstHello.super.hello();
            SecondHello.super.hello();
            System.out.println("  Conflict resolved explicitly in HelloBoth.");
        }
    }
}
