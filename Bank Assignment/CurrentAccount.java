public class CurrentAccount extends Account {

    private final double overdraftLimit;
    private static final double MONTHLY_FEE = 20.0;

    public CurrentAccount(String accountNumber, double balance, double overdraftLimit) {
        super(accountNumber, balance);
        if (!Double.isFinite(overdraftLimit) || overdraftLimit < 0.0) {
            throw new IllegalArgumentException("Overdraft limit must be finite and non-negative.");
        }
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public static double getMonthlyFee() {
        return MONTHLY_FEE;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0.0 || !Double.isFinite(amount)) {
            System.out.printf("Current Account %s: Withdrawal rejected. Amount must be positive.%n",
                    accountNumber);
            return;
        }

        // The balance may go negative, but not beyond the overdraft limit.
        if ((balance - amount) < -overdraftLimit) {
            System.out.printf("Current Account %s: Withdrawal rejected. Overdraft limit exceeded (limit: $%.2f).%n",
                    accountNumber, overdraftLimit);
        } else {
            balance -= amount;
            System.out.printf("Current Account %s: Withdrawal successful. Balance = $%.2f%n",
                    accountNumber, balance);
        }
    }

    @Override
    public void endOfMonth() {
        // Charge the fixed fee once at month end.
        balance -= MONTHLY_FEE;
        System.out.printf("Current Account %s: Monthly fee charged = $%.2f, New Balance = $%.2f%n",
                accountNumber, MONTHLY_FEE, balance);
    }
}