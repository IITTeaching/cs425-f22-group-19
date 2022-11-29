public class Loan<T> {
    private T loanID;
    private T account;
    private T runTime;
    private T interest_sche;

    // Setter
    public void setLoanID(T loanID) {
        this.loanID = loanID;
    }

    public void setAccount(T account) {
        this.account = account;
    }

    public void setRunTime(T runTime) {
        this.runTime = runTime;
    }

    public void setInterest_sche(T interest_sche) {
        this.interest_sche = interest_sche;
    }

    // Getter
    public T getLoanID() {
        return loanID;
    }

    public T getAccount() {
        return account;
    }

    public T getRunTime() {
        return runTime;
    }

    public T getInterest_sche() {
        return interest_sche;
    }
}
