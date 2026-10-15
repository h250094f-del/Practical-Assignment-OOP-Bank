public class ExceptionHandlingExamples {

    public static void main(String[] args) {
        // Demonstrate built-in and custom exceptions.
        demonstrateDivision();
        demonstrateArrayAccess();
        demonstrateAgeValidation();
        demonstrateBankWithdrawal();
        demonstrateFinallyExecution();
    }

    private static void demonstrateDivision() {
        System.out.println("INTEGER DIVISION");

        divideAndPrint("20", "4");

        divideAndPrint("10", "0");

        divideAndPrint("abc", "2");
    }

    private static void divideAndPrint(String numStr, String denStr) {
        // Parsing and division can fail for different reasons.
        try {
            System.out.printf("Dividing %s / %s -> ", numStr, denStr);
            int dividend = Integer.parseInt(numStr);
            int divisor = Integer.parseInt(denStr);
            int result = dividend / divisor;
            System.out.println("Result = " + result);
        } catch (NumberFormatException exception) {
            System.out.println("Input error: both values must be valid integers.");
        } catch (ArithmeticException exception) {
            System.out.println("Division error: cannot divide by zero.");
        }
    }

    private static void demonstrateArrayAccess() {
        System.out.println("\nARRAY ACCESS");
        int[] values = {10, 20, 30};

        printElement(values, 1);
        printElement(values, 5);
    }

    private static void printElement(int[] values, int index) {
        try {
            System.out.println("Value at index " + index + ": " + values[index]);
        } catch (ArrayIndexOutOfBoundsException exception) {
            System.out.println("Invalid array index: " + index + " (valid indices: 0 to " + (values.length - 1) + ")");
        }
    }

    private static void demonstrateAgeValidation() {
        System.out.println("\nAGE VALIDATION");
        tryAge(25);
        tryAge(-5);
        tryAge(130);
    }

    private static void tryAge(int age) {
        try {
            validateAge(age);
            System.out.println("Age " + age + " is valid.");
        } catch (InvalidAgeException exception) {
            System.out.println("Invalid age " + age + ": " + exception.getMessage());
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
        System.out.println("Initial Balance: $" + String.format("%.2f", account.getBalance()));

        try {
            account.withdraw(30.0);
            System.out.println("Withdrew $30.00. Balance remaining: $" + String.format("%.2f", account.getBalance()));

            account.withdraw(100.0);
            System.out.println("Withdrew $100.00. Balance remaining: $" + String.format("%.2f", account.getBalance()));
        } catch (InsufficientFundsException exception) {
            System.out.println("Withdrawal rejected: " + exception.getMessage());
            System.out.println("Balance remains: $" + String.format("%.2f", account.getBalance()));
        }
    }

    private static void demonstrateFinallyExecution() {
        System.out.println("\nFINALLY WITH RETURN");
        int result = returnFromTry();
        System.out.println("Returned value: " + result);
    }

    private static int returnFromTry() {
        try {
            System.out.println("Returning from try block.");
            return 42;
        } finally {
            // The finally block runs before the pending return completes.
            System.out.println("Finally block executed before method returned.");
        }
    }

    public static class InvalidAgeException extends Exception {
        private static final long serialVersionUID = 1L;

        public InvalidAgeException(String message) {
            super(message);
        }
    }

    public static class InsufficientFundsException extends Exception {
        private static final long serialVersionUID = 1L;

        public InsufficientFundsException(String message) {
            super(message);
        }
    }

    public static class CheckedBankAccount {
        private double balance;

        public CheckedBankAccount(double openingBalance) {
            if (!Double.isFinite(openingBalance) || openingBalance < 0.0) {
                throw new IllegalArgumentException(
                        "opening balance must be finite and non-negative");
            }
            balance = openingBalance;
        }

        public void withdraw(double amount) throws InsufficientFundsException {
            if (!Double.isFinite(amount) || amount <= 0.0) {
                throw new IllegalArgumentException(
                        "withdrawal must be finite and positive");
            }
            // Report insufficient funds with a checked exception.
            if (amount > balance) {
                throw new InsufficientFundsException(
                        String.format("insufficient funds (requested $%.2f, available $%.2f)", amount, balance));
            }
            balance -= amount;
        }

        public double getBalance() {
            return balance;
        }
    }
}
