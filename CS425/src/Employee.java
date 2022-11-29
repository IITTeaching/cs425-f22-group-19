public class Employee<T> {
    private T SSN;
    private T name;
    private T role;
    private T salary;
    private T branch;

    // Setter
    public void setSSN (T SSN) {
        this.SSN = SSN;
    }

    public void setName (T name) {
        this.name = name;
    }

    public void setRole (T role) {
        this.role = role;
    }

    public void setSalary (T salary) {
        this.salary = salary;
    }

    public void setBranch (T branch) {
        this.branch = branch;
    }

    // Getter
    public T getSSN() {
        return SSN;
    }

    public T getName() {
        return name;
    }

    public T getRole() {
        return  role;
    }

    public T getSalary() {
        return salary;
    }

    public T getBranch() {
        return branch;
    }

}
