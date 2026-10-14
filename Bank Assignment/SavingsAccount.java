public class SavingsAccount extends Account {

    private double minimumBalance;
    private static final double INTEREST_RATE = 0.05;

    public SavingsAccount(String accountNumber,
                          double balance,
                          double minimumBalance) {

        super(accountNumber, balance);
        this.minimumBalance = minimumBalance;
    }

    @Override
    public void withdraw(double amount) {

        if ((balance - amount) < minimumBalance) {

            System.out.println(
                    "Savings Account "
                    + accountNumber
                    + ": Withdrawal rejected. Minimum balance of $"
                    + minimumBalance
                    + " must be maintained.");

        } else {

            balance -= amount;

            System.out.println(
                    "Savings Account "
                    + accountNumber
                    + ": Withdrawal successful. Balance = $"
                    + balance);
        }
    }

    @Override
    public void endOfMonth() {

        double interest = balance * INTEREST_RATE;

        balance += interest;

        System.out.println(
                "Savings Account "
                + accountNumber
                + ": Interest added = $"
                + String.format("%.2f", interest)
                + ", New Balance = $"
                + String.format("%.2f", balance));
    }
}