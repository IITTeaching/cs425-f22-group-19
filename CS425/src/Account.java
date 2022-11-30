import java.util.*;

public class Account<T> {
    private T accNum;
    private T accPin;
    private T balance;
    private T type;

    private static final Map<Integer, Account> accounts = new HashMap<>();
    // Setter
    public Account(T accNum, T accPin) {
        this.accNum = accNum;
        this.accPin = accPin;
    }

    public void setBalance(T balance) {
        this.balance = balance;
    }

    public void setType(T type) {
        this.type = type;
    }

    // Getter
    public T getAccNum() {
        return accNum;
    }

    public T getAccPin() { return accPin;}

    public T getBalance() {
        return balance;
    }

    public T getType() {
        return type;
    }

    public static void create() {
        System.out.println("Create six digit account number: ");
        Scanner input = new Scanner(System.in);
        int accNum = input.nextInt();
        System.out.println("Create four digit account pin: ");
        Scanner input2 = new Scanner(System.in);
        int accPin = input2.nextInt();
        Account account = new Account(accNum, accPin);
    }
}
