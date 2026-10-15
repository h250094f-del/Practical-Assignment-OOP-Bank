import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

public class InheritanceExamples {

    public static void main(String[] args) {
        // Compare inheritance, constructor chaining, and composition.
        demonstratePersonStudentAndTeacher();
        demonstrateVehicleConstructorOrder();
        demonstrateStackComposition();
    }

    private static void demonstratePersonStudentAndTeacher() {
        System.out.println("PERSON, STUDENT, AND TEACHER (INHERITANCE & SUPER)");
        Student student = new Student("Amina", 20, "Computer Science");
        Teacher teacher = new Teacher("Mr. Kato", 42, "Programming");

        System.out.print("  ");
        student.display();
        System.out.println("    Course: " + student.getCourse());

        System.out.print("  ");
        teacher.display();
        System.out.println("    Subject: " + teacher.getSubject());
    }

    private static void demonstrateVehicleConstructorOrder() {
        System.out.println("\nVEHICLE CONSTRUCTOR HIERARCHY (SUPER CONSTRUCTOR CHAINING)");
        System.out.println("Instantiating ElectricCar(\"Nissan\", 40):");
        new ElectricCar("Nissan", 40);
    }

    private static void demonstrateStackComposition() {
        System.out.println("\nSTACK USING COMPOSITION OVER INHERITANCE");
        SimpleStack<String> stack = new SimpleStack<>();
        stack.push("first");
        stack.push("second");
        System.out.println("  Top item (peek): " + stack.peek());
        System.out.println("  Popped item:     " + stack.pop());
        System.out.println("  Items remaining: " + stack.size());
    }

    public static class Person {
        private final String name;
        private final int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public void display() {
            System.out.println("Name: " + name + ", age: " + age);
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }

    public static class Student extends Person {
        private final String course;

        public Student(String name, int age, String course) {
            // Initialize inherited name and age in the superclass.
            super(name, age);
            this.course = course;
        }

        public String getCourse() {
            return course;
        }
    }

    public static class Teacher extends Person {
        private final String subject;

        public Teacher(String name, int age, String subject) {
            super(name, age);
            this.subject = subject;
        }

        public String getSubject() {
            return subject;
        }
    }

    public static class Vehicle {
        public Vehicle(String type) {
            System.out.println("  [1] Vehicle constructor invoked: " + type);
        }
    }

    public static class Car extends Vehicle {
        public Car(String brand) {
            super("Car");
            System.out.println("  [2] Car constructor invoked: " + brand);
        }
    }

    public static class ElectricCar extends Car {
        public ElectricCar(String brand, int batteryKwh) {
            // Constructor calls move from the most specific class to its parents.
            super(brand);
            System.out.println("  [3] ElectricCar constructor invoked: " + batteryKwh + " kWh battery");
        }
    }

    public static class SimpleStack<E> {
        // This stack uses a list internally instead of extending a collection.
        private final List<E> items = new ArrayList<>();

        public void push(E item) {
            items.add(item);
        }

        public E pop() {
            if (items.isEmpty()) {
                throw new EmptyStackException();
            }
            return items.remove(items.size() - 1);
        }

        public E peek() {
            if (items.isEmpty()) {
                throw new EmptyStackException();
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
