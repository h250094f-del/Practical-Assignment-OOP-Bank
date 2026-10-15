public abstract class Account {

    // Subclasses use this shared account identity and balance.
    protected String accountNumber;
    protected double balance;

    public Account(String accountNumber, double balance) {
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Account number must not be null or blank.");
        }
        if (!Double.isFinite(balance) || balance < 0.0) {
            throw new IllegalArgumentException("Opening balance must be finite and non-negative.");
        }
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(double amount) {
        // Reject non-finite and non-positive deposits.
        if (amount > 0.0 && Double.isFinite(amount)) {
            balance += amount;
            System.out.printf("Account %s: Deposit successful. Deposited = $%.2f, New Balance = $%.2f%n",
                    accountNumber, amount, balance);
        } else {
            System.out.println("Deposit rejected: Amount must be positive.");
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    // Account types implement their own transaction and month-end rules.
    public abstract void withdraw(double amount);

    public abstract void endOfMonth();

    @Override
    public String toString() {
        return String.format("%s[Account No: %s, Balance: $%.2f]",
                getClass().getSimpleName(), accountNumber, balance);
    }
}