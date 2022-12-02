import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.lang.*;

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

    public static boolean exists(int SSN) throws Exception
    {
        try
        {
            PreparedStatement pStmt = Main.c.prepareStatement("SELECT* from customer WHERE ssn = ?;");
            pStmt.setInt(1, SSN);
            pStmt.executeQuery();
            ResultSet r = pStmt.executeQuery();
            int count = 0;
            while(r.next()){
                count++;
			}
			r.close();
            return count > 0;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
