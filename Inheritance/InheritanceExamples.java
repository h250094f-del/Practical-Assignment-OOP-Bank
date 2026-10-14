import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

public class InheritanceExamples {

    public static void main(String[] args) {
        demonstratePersonStudentAndTeacher();
        demonstrateVehicleConstructorOrder();
        demonstrateStackComposition();
    }

    private static void demonstratePersonStudentAndTeacher() {
        System.out.println("PERSON, STUDENT, AND TEACHER");
        Student student = new Student("Amina", 20, "Computer Science");
        Teacher teacher = new Teacher("Mr. Kato", 42, "Programming");

        student.display();
        System.out.println("Course: " + student.getCourse());
        teacher.display();
        System.out.println("Subject: " + teacher.getSubject());
    }

    private static void demonstrateVehicleConstructorOrder() {
        System.out.println("\nVEHICLE CONSTRUCTOR ORDER");
        new ElectricCar("Nissan", 40);
    }

    private static void demonstrateStackComposition() {
        System.out.println("\nSTACK USING COMPOSITION");
        SimpleStack<String> stack = new SimpleStack<>();
        stack.push("first");
        stack.push("second");
        System.out.println("Top item: " + stack.peek());
        System.out.println("Removed: " + stack.pop());
        System.out.println("Items remaining: " + stack.size());
    }

    private static class Person {
        private final String name;
        private final int age;

        private Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        protected void display() {
            System.out.println("Name: " + name + ", age: " + age);
        }
    }

    private static class Student extends Person {
        private final String course;

        private Student(String name, int age, String course) {
            super(name, age);
            this.course = course;
        }

        private String getCourse() {
            return course;
        }
    }

    private static class Teacher extends Person {
        private final String subject;

        private Teacher(String name, int age, String subject) {
            super(name, age);
            this.subject = subject;
        }

        private String getSubject() {
            return subject;
        }
    }

    private static class Vehicle {
        private Vehicle(String type) {
            System.out.println("Vehicle constructor: " + type);
        }
    }

    private static class Car extends Vehicle {
        private Car(String brand) {
            super("Car");
            System.out.println("Car constructor: " + brand);
        }
    }

    private static class ElectricCar extends Car {
        private ElectricCar(String brand, int batteryKwh) {
            super(brand);
            System.out.println("ElectricCar constructor: " + batteryKwh + " kWh battery");
        }
    }

    private static class SimpleStack<E> {
        private final List<E> items = new ArrayList<>();

        private void push(E item) {
            items.add(item);
        }

        private E pop() {
            if (items.isEmpty()) {
                throw new EmptyStackException();
            }
            return items.remove(items.size() - 1);
        }

        private E peek() {
            if (items.isEmpty()) {
                throw new EmptyStackException();
            }
            return items.get(items.size() - 1);
        }

        private boolean isEmpty() {
            return items.isEmpty();
        }

        private int size() {
            return items.size();
        }
    }
}
