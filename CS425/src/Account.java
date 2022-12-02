import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.lang.*;

public class Account {
    private static int accID;
    private static double balance;
    private static String accType;

    private static final Map<Integer, Account> accounts = new HashMap<>();

    // Setter
    public Account(int accID) {
        this.accID = accID;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setType(String accType) {
        this.accType = accType;
    }

    // Getter
    public int getAccID() {
        return accID;
    }


    public double getBalance() {
        return balance;
    }

    public String getType() {
        return accType;
    }

    public static void create(int SSN) throws Exception {
        System.out.println("Create six digit account number: ");
        Scanner input = new Scanner(System.in);
        accID = input.nextInt();
        Account account = new Account(accID);
        System.out.println("Select account type\n1. Checking 2. Saving");
        Scanner input2 = new Scanner(System.in);
        String type = input2.next();
        if (type.equals('1')) accType = "Checking";
        else if (type.equals('2')) accType = "Saving";
        else System.out.println("Wrong account type.");

        // Update to SQL Database
        try {
            PreparedStatement pStmt = Main.c.prepareStatement("INSERT INTO account VALUES (?,?);");
            pStmt.setInt(1, accID);
            pStmt.setString(2, accType);

            pStmt.executeQuery();
            Main.c.commit();
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.toString());
            System.err.println("\n\nFOR THIS PROGRAM TO WORK YOU HAVE TO HAVE A POSTGRES SERVER RUNNING LOCALLY (OR DOCKER) AT "
                    + Main.JDBC_HOST
                    + " WITH PORT " + Main.JDBC_PORT
                    + " AND DATABASE " + Main.JDBC_DB
                    + " AND USER " + Main.DBUSER
                    + " WITH PASSWORD " + Main.DBPASSWD);
        }
    }

    public static void delete() throws Exception {
        System.out.println("Type account ID want to delete: ");
        Scanner input = new Scanner(System.in);
        int delAcc = input.nextInt();

        if (accounts.containsKey(delAcc) == true) {
            accounts.remove(delAcc);
        }
        else  System.out.println("There is no account" + delAcc + "in our system.");

        // Update to SQL Database
        try {
            Main.s.executeUpdate("DELETE FROM account where account_id = delACC;");
            Main.c.commit();
        }
        catch (Exception e) {
            System.err.println("An error occurred: " + e.toString());
            System.err.println("\n\nFOR THIS PROGRAM TO WORK YOU HAVE TO HAVE A POSTGRES SERVER RUNNING LOCALLY (OR DOCKER) AT "
                    + Main.JDBC_HOST
                    + " WITH PORT " + Main.JDBC_PORT
                    + " AND DATABASE " + Main.JDBC_DB
                    + " AND USER " + Main.DBUSER
                    + " WITH PASSWORD " + Main.DBPASSWD);
        }
    }
}
