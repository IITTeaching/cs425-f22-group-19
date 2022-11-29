public class Account<T> {
    private T accountID;
    private T balance;
    private T type;

    // Setter
    public void setAccountID(T accountID) {
        this.accountID = accountID;
    }

    public void setBalance(T balance) {
        this.balance = balance;
    }

    public void setType(T type) {
        this.type = type;
    }

    // Getter
    public T getAccountID() {
        return accountID;
    }

    public T getBalance() {
        return balance;
    }

    public T getType() {
        return type;
    }
}
