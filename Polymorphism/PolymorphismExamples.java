import java.util.Arrays;

public class PolymorphismExamples {

    public static void main(String[] args) {
        demonstrateOverloading();
        demonstrateAnimalPolymorphism();
        demonstrateEmployeePayroll();
        demonstrateReferenceDispatch();
        demonstratePaymentProcessing();
    }

    private static void demonstrateOverloading() {
        System.out.println("METHOD OVERLOADING");
        Printer.print(42);
        Printer.print(3.14);
        Printer.print("Hello");
        Printer.print(new int[] {1, 2, 3});
    }

    private static void demonstrateAnimalPolymorphism() {
        System.out.println("\nANIMAL OVERRIDING");
        Animal[] animals = {new Cat(), new Cow(), new Duck()};
        for (Animal animal : animals) {
            animal.sound();
        }
    }

    private static void demonstrateEmployeePayroll() {
        System.out.println("\nMIXED EMPLOYEE PAYROLL");
        Employee[] employees = {
            new SalariedEmployee("Amina", 3200.0),
            new Intern("Brian", 120.0, 10),
            new SalariedEmployee("Chloe", 2800.0)
        };

        double totalPayroll = 0.0;
        for (Employee employee : employees) {
            totalPayroll += employee.calculatePay();
        }
        System.out.printf("Total payroll: $%.2f%n", totalPayroll);
    }

    private static void demonstrateReferenceDispatch() {
        System.out.println("\nSUPERCLASS REFERENCE");
        Parent reference = new Child();
        System.out.println("Field: " + reference.label);
        System.out.println("Method: " + reference.describe());
    }

    private static void demonstratePaymentProcessing() {
        System.out.println("\nPAYMENT PROCESSING");
        Payment[] payments = {
            new CardPayment("**** 1234", 50.0),
            new MobileMoneyPayment("0712345678", 25.0)
        };

        System.out.println(" instanceof dispatch:");
        for (Payment payment : payments) {
            PaymentProcessor.pay(payment);
        }

        System.out.println(" Polymorphic dispatch:");
        ProcessablePayment[] processablePayments = {
            new CardPayment("**** 1234", 50.0),
            new MobileMoneyPayment("0712345678", 25.0)
        };
        for (ProcessablePayment payment : processablePayments) {
            payment.process();
        }
    }

    private static class Printer {
        private static void print(int value) {
            System.out.println("int: " + value);
        }

        private static void print(double value) {
            System.out.println("double: " + value);
        }

        private static void print(String value) {
            System.out.println("String: " + value);
        }

        private static void print(int[] values) {
            System.out.println("int[]: " + Arrays.toString(values));
        }
    }

    private static class Animal {
        public void sound() {
            System.out.println("Animal makes a sound");
        }
    }

    private static class Cat extends Animal {
        @Override
        public void sound() {
            System.out.println("Cat: meow");
        }
    }

    private static class Cow extends Animal {
        @Override
        public void sound() {
            System.out.println("Cow: moo");
        }
    }

    private static class Duck extends Animal {
        @Override
        public void sound() {
            System.out.println("Duck: quack");
        }
    }

    private abstract static class Employee {
        private final String name;

        private Employee(String name) {
            this.name = name;
        }

        protected String getName() {
            return name;
        }

        public abstract double calculatePay();
    }

    private static class SalariedEmployee extends Employee {
        private final double monthlySalary;

        private SalariedEmployee(String name, double monthlySalary) {
            super(name);
            this.monthlySalary = monthlySalary;
        }

        @Override
        public double calculatePay() {
            return monthlySalary;
        }
    }

    private static class Intern extends Employee {
        private final double dailyRate;
        private final int daysWorked;

        private Intern(String name, double dailyRate, int daysWorked) {
            super(name);
            this.dailyRate = dailyRate;
            this.daysWorked = daysWorked;
        }

        @Override
        public double calculatePay() {
            return dailyRate * daysWorked;
        }
    }

    private static class Parent {
        protected String label = "parent field";

        public String describe() {
            return "parent method";
        }
    }

    private static class Child extends Parent {
        protected String label = "child field";

        @Override
        public String describe() {
            return "child method";
        }
    }

    private interface Payment {
        double amount();
    }

    private interface ProcessablePayment extends Payment {
        void process();
    }

    private static class CardPayment implements ProcessablePayment {
        private final String cardNumber;
        private final double paymentAmount;

        private CardPayment(String cardNumber, double paymentAmount) {
            this.cardNumber = cardNumber;
            this.paymentAmount = paymentAmount;
        }

        @Override
        public double amount() {
            return paymentAmount;
        }

        @Override
        public void process() {
            System.out.printf(
                    "Processed card payment of $%.2f using %s%n",
                    amount(), cardNumber);
        }
    }

    private static class MobileMoneyPayment implements ProcessablePayment {
        private final String phoneNumber;
        private final double paymentAmount;

        private MobileMoneyPayment(String phoneNumber, double paymentAmount) {
            this.phoneNumber = phoneNumber;
            this.paymentAmount = paymentAmount;
        }

        @Override
        public double amount() {
            return paymentAmount;
        }

        @Override
        public void process() {
            System.out.printf(
                    "Processed mobile money payment of $%.2f for %s%n",
                    amount(), phoneNumber);
        }
    }

    private static class PaymentProcessor {
        private static void pay(Payment payment) {
            if (payment instanceof CardPayment cardPayment) {
                System.out.printf(
                        "Processed card payment of $%.2f using %s%n",
                        cardPayment.amount(), cardPayment.cardNumber);
            } else if (payment instanceof MobileMoneyPayment mobileMoneyPayment) {
                System.out.printf(
                        "Processed mobile money payment of $%.2f for %s%n",
                        mobileMoneyPayment.amount(), mobileMoneyPayment.phoneNumber);
            } else {
                throw new IllegalArgumentException(
                        "Unsupported payment type: " + payment.getClass().getName());
            }
        }
    }
}
