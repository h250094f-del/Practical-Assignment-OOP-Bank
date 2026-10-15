import java.util.ArrayList;
import java.util.List;

public class BankDemo {

    public static void main(String[] args) {
       
        System.out.println("              BANK ACCOUNT MANAGEMENT SYSTEM DEMONSTRATION                      ");
       

        // Store both account types through their shared Account superclass.
        List<Account> accounts = new ArrayList<>();
        accounts.add(new SavingsAccount("S001", 1000.0, 500.0));
        accounts.add(new SavingsAccount("S002", 700.0, 300.0));
        accounts.add(new CurrentAccount("C001", 500.0, 1000.0));
        accounts.add(new CurrentAccount("C002", 200.0, 500.0));

        System.out.println("\n--- INITIAL ACCOUNTS REGISTERED ---");
        for (Account acc : accounts) {
            System.out.println("  " + acc);
        }

        System.out.println("\n--- PROCESSING WITHDRAWALS ---");
        // Each withdrawal amount corresponds to the account at the same index.
        double[] amounts = {600.0, 200.0, 1200.0, 800.0};

        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            System.out.printf("%nProcessing withdrawal for %s (Requested: $%.2f, Current Balance: $%.2f):%n",
                    acc.getAccountNumber(), amounts[i], acc.getBalance());
            acc.withdraw(amounts[i]);
        }

        System.out.println("\n--- TESTING NEGATIVE WITHDRAWAL VALIDATION ---");
        System.out.println("Attempting negative withdrawal (-$50.00) on S001:");
        accounts.get(0).withdraw(-50.0);

        System.out.println("\n--- END OF MONTH PROCESSING ---");
        // Dynamic dispatch applies each account type's month-end rule.
        for (Account acc : accounts) {
            System.out.printf("%nAccount %s | Balance Before: $%.2f%n", acc.getAccountNumber(), acc.getBalance());
            acc.endOfMonth();
            System.out.printf("Account %s | Balance After:  $%.2f%n", acc.getAccountNumber(), acc.getBalance());
        }

        
        System.out.println("              BANK DEMONSTRATION COMPLETED SUCCESSFULLY                         ");
       
}
}