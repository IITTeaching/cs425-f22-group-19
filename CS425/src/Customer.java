public class Customer<T> {
    private T SSN;
    private T name;
    private T address;
    private T homeBranch;

    // Setter
    public void setSSN (T SSN) {
        this.SSN = SSN;
    }

    public void setName (T name) {
        this.name = name;
    }

    public void setAddress (T address) {
        this.address = address;
    }

    public void setHomeBranch (T homeBranch) {
        this.homeBranch = homeBranch;
    }

    // Getter
    public T getSSN() {
        return SSN;
    }

    public T getName() {
        return name;
    }

    public T getAddress() {
        return address;
    }

    public T getHomeBranch() {
        return homeBranch;
    }
}
