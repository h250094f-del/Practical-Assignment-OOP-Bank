import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class EncapsulationExamples {

    public static void main(String[] args) {
        demonstrateStudent();
        demonstrateBankAccount();
        demonstrateImmutablePerson();
        demonstrateTemperature();
    }

    private static void demonstrateStudent() {
        System.out.println("STUDENT");
        Student student = new Student("Amina", 86);
        System.out.println(student.getName() + " grade: " + student.getGrade());

        try {
            student.setMarks(105);
        } catch (IllegalArgumentException exception) {
            System.out.println("Invalid marks rejected: " + exception.getMessage());
        }
    }

    private static void demonstrateBankAccount() {
        System.out.println("\nBANK ACCOUNT TRANSACTIONS");
        BankAccount account = new BankAccount("A001", 100.0);
        account.deposit(50.0);
        account.withdraw(20.0);

        List<String> history = account.getTransactionHistory();
        System.out.println("History: " + history);

        try {
            history.add("Attempted external change");
        } catch (UnsupportedOperationException exception) {
            System.out.println("Returned transaction history cannot be modified.");
        }
        System.out.println("Stored history: " + account.getTransactionHistory());
    }

    private static void demonstrateImmutablePerson() {
        System.out.println("\nIMMUTABLE PERSON");
        ImmutablePerson person = new ImmutablePerson("Brian", 30);
        System.out.println(person.getName() + ", age " + person.getAge());
    }

    private static void demonstrateTemperature() {
        System.out.println("\nTEMPERATURE API");
        Temperature temperature = new Temperature(300.0);
        printTemperature(temperature);

        try {
            new Temperature(-1.0);
        } catch (IllegalArgumentException exception) {
            System.out.println("Below absolute zero rejected: " + exception.getMessage());
        }
    }

    private static void printTemperature(Temperature temperature) {
        System.out.printf(
                "Celsius: %.2f, Fahrenheit: %.2f%n",
                temperature.getCelsius(), temperature.getFahrenheit());
    }

    private static class Student {
        private final String name;
        private int marks;

        private Student(String name, int marks) {
            this.name = Objects.requireNonNull(name, "name must not be null");
            setMarks(marks);
        }

        private String getName() {
            return name;
        }

        private int getMarks() {
            return marks;
        }

        private void setMarks(int marks) {
            if (marks < 0 || marks > 100) {
                throw new IllegalArgumentException("marks must be between 0 and 100");
            }
            this.marks = marks;
        }

        private char getGrade() {
            if (marks >= 80) {
                return 'A';
            }
            if (marks >= 70) {
                return 'B';
            }
            if (marks >= 60) {
                return 'C';
            }
            return 'F';
        }
    }

    private static class BankAccount {
        private final String accountNumber;
        private double balance;
        private final List<String> transactionHistory = new ArrayList<>();

        private BankAccount(String accountNumber, double openingBalance) {
            this.accountNumber = Objects.requireNonNull(
                    accountNumber, "accountNumber must not be null");
            if (!Double.isFinite(openingBalance) || openingBalance < 0.0) {
                throw new IllegalArgumentException("opening balance must be finite and non-negative");
            }
            this.balance = openingBalance;
            transactionHistory.add(String.format("Opened account with balance %.2f", balance));
        }

        private void deposit(double amount) {
            if (!Double.isFinite(amount) || amount <= 0.0) {
                throw new IllegalArgumentException("deposit must be finite and positive");
            }
            balance += amount;
            transactionHistory.add(String.format("Deposited %.2f; balance %.2f", amount, balance));
        }

        private void withdraw(double amount) {
            if (!Double.isFinite(amount) || amount <= 0.0) {
                throw new IllegalArgumentException("withdrawal must be finite and positive");
            }
            if (amount > balance) {
                throw new IllegalArgumentException("insufficient funds");
            }
            balance -= amount;
            transactionHistory.add(String.format("Withdrew %.2f; balance %.2f", amount, balance));
        }

        private double getBalance() {
            return balance;
        }

        private String getAccountNumber() {
            return accountNumber;
        }

        private List<String> getTransactionHistory() {
            return Collections.unmodifiableList(new ArrayList<>(transactionHistory));
        }
    }

    private static final class ImmutablePerson {
        private final String name;
        private final int age;

        private ImmutablePerson(String name, int age) {
            this.name = Objects.requireNonNull(name, "name must not be null");
            if (age < 0) {
                throw new IllegalArgumentException("age must be non-negative");
            }
            this.age = age;
        }

        private String getName() {
            return name;
        }

        private int getAge() {
            return age;
        }
    }

    private static class Temperature {
        private final double kelvin;

        private Temperature(double kelvin) {
            if (!Double.isFinite(kelvin) || kelvin < 0.0) {
                throw new IllegalArgumentException("kelvin must be finite and at least zero");
            }
            this.kelvin = kelvin;
        }

        private double getCelsius() {
            return kelvin - 273.15;
        }

        private double getFahrenheit() {
            return getCelsius() * 9.0 / 5.0 + 32.0;
        }
    }
}
