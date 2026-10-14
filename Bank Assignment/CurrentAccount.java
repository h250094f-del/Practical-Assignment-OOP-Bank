public class CurrentAccount extends Account {

    private double overdraftLimit;
    private static final double MONTHLY_FEE = 20.0;

    public CurrentAccount(String accountNumber,
                          double balance,
                          double overdraftLimit) {

        super(accountNumber, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {

        if ((balance - amount) < -overdraftLimit) {

            System.out.println(
                    "Current Account "
                    + accountNumber
                    + ": Withdrawal rejected. Overdraft limit exceeded.");

        } else {

            balance -= amount;

            System.out.println(
                    "Current Account "
                    + accountNumber
                    + ": Withdrawal successful. Balance = $"
                    + balance);
        }
    }

    @Override
    public void endOfMonth() {

        balance -= MONTHLY_FEE;

        System.out.println(
                "Current Account "
                + accountNumber
                + ": Monthly fee charged = $20.00, New Balance = $"
                + String.format("%.2f", balance));
    }
}