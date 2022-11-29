public class Transaction<T> {
    private T accountID;
    private T type;
    private T Amount;
    private T description;
    private T acc_Recipi;
    private T acc_Sender;

    // Setter
    public void setAccountID(T accountID) {
        this.accountID = accountID;
    }

    public void setType(T type) {
        this.type = type;
    }

    public void setAmount(T amount) {
        Amount = amount;
    }

    public void setDescription(T description) {
        this.description = description;
    }

    public void setAcc_Recipi(T acc_Recipi) {
        this.acc_Recipi = acc_Recipi;
    }

    public void setAcc_Sender(T acc_Sender) {
        this.acc_Sender = acc_Sender;
    }

    // Getter
    public T getAccountID() {
        return accountID;
    }

    public T getType() {
        return type;
    }

    public T getAmount() {
        return Amount;
    }

    public T getDescription() {
        return description;
    }

    public T getAcc_Recipi() {
        return acc_Recipi;
    }

    public T getAcc_Sender() {
        return acc_Sender;
    }
}
