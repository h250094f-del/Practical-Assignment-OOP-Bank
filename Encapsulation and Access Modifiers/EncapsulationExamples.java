import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class EncapsulationExamples {

    public static void main(String[] args) {
        // Show validation, protected state, immutability, and invariants.
        demonstrateStudent();
        demonstrateBankAccount();
        demonstrateImmutablePerson();
        demonstrateTemperature();
    }

    private static void demonstrateStudent() {
        System.out.println("STUDENT (ENCAPSULATION & RANGE VALIDATION)");
        Student student = new Student("Amina", 86);
        System.out.println("  " + student.getName() + " marks: " + student.getMarks() + ", grade: " + student.getGrade());

        try {
            student.setMarks(105);
        } catch (IllegalArgumentException exception) {
            System.out.println("  Attempted to set marks to 105 -> Rejected: " + exception.getMessage());
        }
    }

    private static void demonstrateBankAccount() {
        System.out.println("\nBANK ACCOUNT (DEFENSIVE COPYING OF TRANSACTION HISTORY)");
        BankAccount account = new BankAccount("A001", 100.0);
        account.deposit(50.0);
        account.withdraw(20.0);

        List<String> history = account.getTransactionHistory();
        System.out.println("  Retrieved history: " + history);

        try {
            history.add("Malicious external modification");
        } catch (UnsupportedOperationException exception) {
            System.out.println("  Attempt to mutate returned transaction history threw UnsupportedOperationException.");
        }
        System.out.println("  Actual internal history remains protected: " + account.getTransactionHistory());
    }

    private static void demonstrateImmutablePerson() {
        System.out.println("\nIMMUTABLE PERSON (FINAL CLASS & READ-ONLY STATE)");
        ImmutablePerson person = new ImmutablePerson("Brian", 30);
        System.out.println("  Created immutable instance: " + person.getName() + ", age " + person.getAge());
    }

    private static void demonstrateTemperature() {
        System.out.println("\nTEMPERATURE API (INVARIANT ENFORCEMENT)");
        Temperature temperature = new Temperature(300.0);
        System.out.printf("  300.0 K = %.2f C = %.2f F%n",
                temperature.getCelsius(), temperature.getFahrenheit());

        try {
            new Temperature(-1.0);
        } catch (IllegalArgumentException exception) {
            System.out.println("  Attempted -1.0 K (below absolute zero) -> Rejected: " + exception.getMessage());
        }
    }

    public static class Student {
        private final String name;
        private int marks;

        public Student(String name, int marks) {
            this.name = Objects.requireNonNull(name, "name must not be null");
            setMarks(marks);
        }

        public String getName() {
            return name;
        }

        public int getMarks() {
            return marks;
        }

        public void setMarks(int marks) {
            // Keep marks within the valid percentage range.
            if (marks < 0 || marks > 100) {
                throw new IllegalArgumentException("marks must be between 0 and 100 (provided: " + marks + ")");
            }
            this.marks = marks;
        }

        public char getGrade() {
            if (marks >= 80) return 'A';
            if (marks >= 70) return 'B';
            if (marks >= 60) return 'C';
            return 'F';
        }
    }

    public static class BankAccount {
        private final String accountNumber;
        private double balance;
        private final List<String> transactionHistory = new ArrayList<>();

        public BankAccount(String accountNumber, double openingBalance) {
            this.accountNumber = Objects.requireNonNull(accountNumber, "accountNumber must not be null");
            if (!Double.isFinite(openingBalance) || openingBalance < 0.0) {
                throw new IllegalArgumentException("Opening balance must be finite and non-negative.");
            }
            this.balance = openingBalance;
            transactionHistory.add(String.format("Opened account with balance $%.2f", balance));
        }

        public void deposit(double amount) {
            if (!Double.isFinite(amount) || amount <= 0.0) {
                throw new IllegalArgumentException("Deposit must be finite and positive.");
            }
            balance += amount;
            transactionHistory.add(String.format("Deposited $%.2f; new balance $%.2f", amount, balance));
        }

        public void withdraw(double amount) {
            if (!Double.isFinite(amount) || amount <= 0.0) {
                throw new IllegalArgumentException("Withdrawal must be finite and positive.");
            }
            if (amount > balance) {
                throw new IllegalArgumentException("Insufficient funds.");
            }
            balance -= amount;
            transactionHistory.add(String.format("Withdrew $%.2f; new balance $%.2f", amount, balance));
        }

        public double getBalance() {
            return balance;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public List<String> getTransactionHistory() {
            // Return a protected copy so callers cannot alter internal history.
            return Collections.unmodifiableList(new ArrayList<>(transactionHistory));
        }
    }

    public static final class ImmutablePerson {
        // Final fields and no setters keep this object's state immutable.
        private final String name;
        private final int age;

        public ImmutablePerson(String name, int age) {
            this.name = Objects.requireNonNull(name, "name must not be null");
            if (age < 0) {
                throw new IllegalArgumentException("age must be non-negative");
            }
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }

    public static class Temperature {
        private final double kelvin;

        public Temperature(double kelvin) {
            if (!Double.isFinite(kelvin) || kelvin < 0.0) {
                throw new IllegalArgumentException("Kelvin must be finite and at least 0.0 (absolute zero)");
            }
            this.kelvin = kelvin;
        }

        public double getCelsius() {
            return kelvin - 273.15;
        }

        public double getFahrenheit() {
            // Convert the stored Kelvin value through Celsius.
            return getCelsius() * 9.0 / 5.0 + 32.0;
        }
    }
}
