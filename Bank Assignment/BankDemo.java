import java.util.ArrayList;
import java.util.List;

public class BankDemo {

    public static void main(String[] args) {

        List<Account> accounts = new ArrayList<>();

        accounts.add(
                new SavingsAccount("S001", 1000, 500));

        accounts.add(
                new SavingsAccount("S002", 700, 300));

        accounts.add(
                new CurrentAccount("C001", 500, 1000));

        accounts.add(
                new CurrentAccount("C002", 200, 500));

        System.out.println("WITHDRAWAL ");

        double[] amounts = {600, 200, 1200, 800};

        for (int i = 0; i < accounts.size(); i++) {

            Account acc = accounts.get(i);

            System.out.println("\nProcessing Account: "
                    + acc.getAccountNumber());

            acc.withdraw(amounts[i]);
        }

        System.out.println("\n END OF MONTH");

        for (Account acc : accounts) {

            System.out.println(
                    "\nAccount "
                    + acc.getAccountNumber()
                    + " Balance Before = $"
                    + acc.getBalance());

            acc.endOfMonth();

            System.out.println(
                    "Account "
                    + acc.getAccountNumber()
                    + " Balance After = $"
                    + acc.getBalance());
        }
    }
}