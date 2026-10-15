public class SavingsAccount extends Account {

    private final double minimumBalance;
    private static final double INTEREST_RATE = 0.05;

    public SavingsAccount(String accountNumber, double balance, double minimumBalance) {
        super(accountNumber, balance);
        if (!Double.isFinite(minimumBalance) || minimumBalance < 0.0) {
            throw new IllegalArgumentException("Minimum balance must be finite and non-negative.");
        }
        this.minimumBalance = minimumBalance;
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }

    public static double getInterestRate() {
        return INTEREST_RATE;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0.0 || !Double.isFinite(amount)) {
            System.out.printf("Savings Account %s: Withdrawal rejected. Amount must be positive.%n",
                    accountNumber);
            return;
        }

        // Keep enough funds to satisfy the account's minimum balance.
        if ((balance - amount) < minimumBalance) {
            System.out.printf("Savings Account %s: Withdrawal rejected. Minimum balance of $%.2f must be maintained.%n",
                    accountNumber, minimumBalance);
        } else {
            balance -= amount;
            System.out.printf("Savings Account %s: Withdrawal successful. Balance = $%.2f%n",
                    accountNumber, balance);
        }
    }

    @Override
    public void endOfMonth() {
        // Add interest only when the account has a positive balance.
        if (balance > 0.0) {
            double interest = balance * INTEREST_RATE;
            balance += interest;
            System.out.printf("Savings Account %s: Interest added = $%.2f, New Balance = $%.2f%n",
                    accountNumber, interest, balance);
        } else {
            System.out.printf("Savings Account %s: No interest added. Balance = $%.2f%n",
                    accountNumber, balance);
        }
    }
}