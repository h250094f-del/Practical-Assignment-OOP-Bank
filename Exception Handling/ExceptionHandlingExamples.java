import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ExceptionHandlingExamples {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            demonstrateDivision(scanner);
        }
        demonstrateArrayAccess();
        demonstrateAgeValidation();
        demonstrateBankWithdrawal();
        demonstrateFinallyExecution();
    }

    private static void demonstrateDivision(Scanner scanner) {
        System.out.println("INTEGER DIVISION");
        try {
            System.out.print("Enter two integers: ");
            int dividend = scanner.nextInt();
            int divisor = scanner.nextInt();
            System.out.println("Result: " + dividend / divisor);
        } catch (InputMismatchException exception) {
            System.out.println("Input error: both values must be integers.");
            scanner.nextLine();
        } catch (ArithmeticException exception) {
            System.out.println("Division error: cannot divide by zero.");
        } catch (NoSuchElementException exception) {
            System.out.println("Input error: two integers are required.");
        }
    }

    private static void demonstrateArrayAccess() {
        System.out.println("\nARRAY ACCESS");
        int[] values = {10, 20, 30};
        printElement(values, 5);
    }

    private static void printElement(int[] values, int index) {
        try {
            System.out.println("Value at index " + index + ": " + values[index]);
        } catch (ArrayIndexOutOfBoundsException exception) {
            System.out.println("Invalid array index: " + index);
        }
    }

    private static void demonstrateAgeValidation() {
        System.out.println("\nAGE VALIDATION");
        try {
            validateAge(25);
            System.out.println("Age 25 is valid.");
            validateAge(130);
        } catch (InvalidAgeException exception) {
            System.out.println("Invalid age: " + exception.getMessage());
        }
    }

    private static void validateAge(int age) throws InvalidAgeException {
        if (age < 0 || age > 120) {
            throw new InvalidAgeException("age must be between 0 and 120");
        }
    }

    private static void demonstrateBankWithdrawal() {
        System.out.println("\nBANK WITHDRAWAL");
        CheckedBankAccount account = new CheckedBankAccount(100.0);
        try {
            account.withdraw(30.0);
            System.out.println("Balance after withdrawal: " + account.getBalance());
            account.withdraw(100.0);
        } catch (InsufficientFundsException exception) {
            System.out.println("Withdrawal rejected: " + exception.getMessage());
            System.out.println("Balance remains: " + account.getBalance());
        }
    }

    private static void demonstrateFinallyExecution() {
        System.out.println("\nFINALLY WITH RETURN");
        int result = returnFromTry();
        System.out.println("Returned value: " + result);
    }

    private static int returnFromTry() {
        try {
            System.out.println("Returning from try.");
            return 42;
        } finally {
            System.out.println("Finally runs before the method returns.");
        }
    }

    private static class InvalidAgeException extends Exception {
        private static final long serialVersionUID = 1L;

        private InvalidAgeException(String message) {
            super(message);
        }
    }

    private static class InsufficientFundsException extends Exception {
        private static final long serialVersionUID = 1L;

        private InsufficientFundsException(String message) {
            super(message);
        }
    }

    private static class CheckedBankAccount {
        private double balance;

        private CheckedBankAccount(double openingBalance) {
            if (!Double.isFinite(openingBalance) || openingBalance < 0.0) {
                throw new IllegalArgumentException(
                        "opening balance must be finite and non-negative");
            }
            balance = openingBalance;
        }

        private void withdraw(double amount) throws InsufficientFundsException {
            if (!Double.isFinite(amount) || amount <= 0.0) {
                throw new IllegalArgumentException(
                        "withdrawal must be finite and positive");
            }
            if (amount > balance) {
                throw new InsufficientFundsException("not enough funds");
            }
            balance -= amount;
        }

        private double getBalance() {
            return balance;
        }
    }
}
