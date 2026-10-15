import java.util.Arrays;

public class PolymorphismExamples {

    public static void main(String[] args) {
        // Run examples of overloading, overriding, and interface dispatch.
        demonstrateOverloading();
        demonstrateAnimalPolymorphism();
        demonstrateEmployeePayroll();
        demonstrateReferenceDispatch();
        demonstratePaymentProcessing();
    }

    private static void demonstrateOverloading() {
        System.out.println("METHOD OVERLOADING (COMPILE-TIME POLYMORPHISM)");
        Printer.print(42);
        Printer.print(3.14159);
        Printer.print("Hello, Object-Oriented Java!");
        Printer.print(new int[]{1, 2, 3, 4, 5});
    }

    private static void demonstrateAnimalPolymorphism() {
        System.out.println("\nANIMAL SOUNDS (RUN-TIME METHOD OVERRIDING)");
        Animal[] animals = {new Cat(), new Cow(), new Duck()};
        for (Animal animal : animals) {
            System.out.print("  " + animal.getClass().getSimpleName() + ": ");
            animal.sound();
        }
    }

    private static void demonstrateEmployeePayroll() {
        System.out.println("\nEMPLOYEE PAYROLL (SUBTYPE POLYMORPHISM)");
        Employee[] employees = {
            new SalariedEmployee("Amina", 3200.0),
            new Intern("Brian", 120.0, 10),
            new SalariedEmployee("Chloe", 2800.0)
        };

        double totalPayroll = 0.0;
        for (Employee employee : employees) {
            double pay = employee.calculatePay();
            System.out.printf("  Employee %-8s : $%.2f%n", employee.getName(), pay);
            totalPayroll += pay;
        }
        System.out.printf("  Total Organization Payroll: $%.2f%n", totalPayroll);
    }

    private static void demonstrateReferenceDispatch() {
        System.out.println("\nSUPERCLASS REFERENCE DISPATCH (FIELDS vs METHODS)");
        // Fields use the reference type; overridden methods use the object type.
        Parent reference = new Child();
        System.out.println("  reference.label:     " + reference.label + " (resolved at compile-time by reference type)");
        System.out.println("  reference.describe(): " + reference.describe() + " (resolved at run-time by object type)");
    }

    private static void demonstratePaymentProcessing() {
        System.out.println("\nPAYMENT PROCESSING (INTERFACE POLYMORPHISM)");
        ProcessablePayment[] payments = {
            new CardPayment("**** 1234", 50.0),
            new MobileMoneyPayment("0712345678", 25.0)
        };

        System.out.println("  Polymorphic dispatch through ProcessablePayment interface:");
        for (ProcessablePayment payment : payments) {
            System.out.print("    - ");
            payment.process();
        }
    }

    public static class Printer {
        public static void print(int value) {
            System.out.println("  int:    " + value);
        }

        public static void print(double value) {
            System.out.printf("  double: %.5f%n", value);
        }

        public static void print(String value) {
            System.out.println("  String: " + value);
        }

        public static void print(int[] values) {
            System.out.println("  int[]:  " + Arrays.toString(values));
        }
    }

    public static class Animal {
        public void sound() {
            System.out.println("Animal makes a sound");
        }
    }

    public static class Cat extends Animal {
        @Override
        public void sound() {
            System.out.println("Meow");
        }
    }

    public static class Cow extends Animal {
        @Override
        public void sound() {
            System.out.println("Moo");
        }
    }

    public static class Duck extends Animal {
        @Override
        public void sound() {
            System.out.println("Quack");
        }
    }

    public abstract static class Employee {
        private final String name;

        public Employee(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        // Subclasses calculate pay using their own rules.
        public abstract double calculatePay();
    }

    public static class SalariedEmployee extends Employee {
        private final double monthlySalary;

        public SalariedEmployee(String name, double monthlySalary) {
            super(name);
            this.monthlySalary = monthlySalary;
        }

        @Override
        public double calculatePay() {
            return monthlySalary;
        }
    }

    public static class Intern extends Employee {
        private final double dailyRate;
        private final int daysWorked;

        public Intern(String name, double dailyRate, int daysWorked) {
            super(name);
            this.dailyRate = dailyRate;
            this.daysWorked = daysWorked;
        }

        @Override
        public double calculatePay() {
            return dailyRate * daysWorked;
        }
    }

    public static class Parent {
        public String label = "parent field";

        public String describe() {
            return "parent method";
        }
    }

    public static class Child extends Parent {
        public String label = "child field";

        @Override
        public String describe() {
            return "child method";
        }
    }

    public interface ProcessablePayment {
        // Each payment exposes its amount and its processing behavior.
        double amount();
        void process();
    }

    public static class CardPayment implements ProcessablePayment {
        private final String cardNumber;
        private final double paymentAmount;

        public CardPayment(String cardNumber, double paymentAmount) {
            this.cardNumber = cardNumber;
            this.paymentAmount = paymentAmount;
        }

        @Override
        public double amount() {
            return paymentAmount;
        }

        @Override
        public void process() {
            System.out.printf("Processed card payment of $%.2f using %s%n", amount(), cardNumber);
        }
    }

    public static class MobileMoneyPayment implements ProcessablePayment {
        private final String phoneNumber;
        private final double paymentAmount;

        public MobileMoneyPayment(String phoneNumber, double paymentAmount) {
            this.phoneNumber = phoneNumber;
            this.paymentAmount = paymentAmount;
        }

        @Override
        public double amount() {
            return paymentAmount;
        }

        @Override
        public void process() {
            System.out.printf("Processed mobile money payment of $%.2f for %s%n", amount(), phoneNumber);
        }
    }
}
