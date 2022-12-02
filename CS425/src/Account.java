import java.sql.*;
import java.util.Hashtable;
import java.util.Scanner;
import java.lang.*;

public class Account {
    private static int accID;
    private static double balance;
    private static String accType;
    private static boolean negAllow;

    private static Hashtable<Integer, Account> accounts = new Hashtable<>();
    private static Hashtable<Account, Double> balances = new Hashtable<>();

    // Setter
    public Account(int accID) {
        this.accID = accID;
    }

//    public void setBalance(double balance) {
//        this.balance = balance;
//    }
//
//    public void setType(String accType) {
//        this.accType = accType;
//    }
//
//    public void setNegAllow(boolean negAllow) { this.negAllow = negAllow; }
//
//    // Getter
//    public static int getAccID(int account) {
//        return accID;
//    }


//    public double getBalance() {
//        return balance;
//    }
//
//    public String getType() {
//        return accType;
//    }
//
//    public boolean getNegAllow() {return negAllow; }

    public static void create(int SSN) throws Exception {
        System.out.println("Create six digit account number: ");
        Scanner input = new Scanner(System.in);
        accID = input.nextInt();
        Account account = new Account(accID);
        accounts.put(SSN, account);     // Store SSN and account number
        balance = 0;
        balances.put(account, balance); // Store account number and its balance
        System.out.println("Select account type\n1. Checking 2. Saving");
        Scanner input2 = new Scanner(System.in);
        String type = input2.next();
        if (type.equals('1')) accType = "Checking";
        else if (type.equals('2')) accType = "Saving";
        else System.out.println("Wrong account type.");

        // Ask customer wants to allow negative amount
        System.out.println("Do you want to allow negative balance with your new account" + accID + "?");
        System.out.println("1. Yes  2. No");
        Scanner input3 = new Scanner(System.in);
        int allow = input3.nextInt();
        if (allow == 1) negAllow = true;
        else negAllow = false;

        // Update to SQL Database
        try {
            PreparedStatement pStmt = Main.c.prepareStatement("INSERT INTO account VALUES (?,?,?,?,?);");
            pStmt.setInt(1, SSN);
            pStmt.setInt(2, accID);
            pStmt.setString(3, accType);
            pStmt.setDouble(4, balance);
            pStmt.setBoolean(5, negAllow);

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

    // Delete the account function
    public static void delete(int SSN) throws Exception {
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

    // Withdrawal from account function
    public static void withdrawal(int SSN) {
        double newBal = 0;
        double amount = 0;
        System.out.println("Please ENTER your account number: ");
        Scanner input = new Scanner(System.in);
        int withAcc = input.nextInt();
        Account account = new Account(withAcc);
        double accAmoun = balances.get(withAcc);

        System.out.println("How much you want to withdrawal?");
        Scanner input1 = new Scanner(System.in);
        amount = input.nextDouble();

        // Check the balance is enough
        if (accAmoun >= amount) {
            newBal = accAmoun - amount;
            balances.put(account, newBal);
        }

        else if (accAmoun < amount && negAllow == false)
            System.out.println("Cannot withdrawal " + amount + ", it will be overdraft.");

        else if (accAmoun < amount && negAllow == true) {
            newBal = accAmoun - amount;
            balances.put(account, newBal);
        }

        // Update SQL Database
        try {
            Main.s.executeUpdate("UPDATE balance = newBal FROM account WHERE account_id = withAcc;");
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

    // Deposit function
    public static void deposit(int SSN) {
        double newBal = 0;
        double amount = 0;

        Scanner input = new Scanner(System.in);

        Account custAcc = accounts.get(SSN);
        System.out.println("Is this account number to deposite?" + custAcc);
        System.out.println("1. Yes  2. No");
        int action = input.nextInt();
        switch (action) {
            case 1: {
                Account account = new Account(Integer.parseInt(custAcc.toString()));
                double accAmoun = balances.get(custAcc);
                System.out.println("How much you want to deposit?");
                amount = input.nextDouble();

                newBal = accAmoun + amount;
                balances.put(account, newBal);

                // Update SQL Database
                try {
                    Main.s.executeUpdate("UPDATE balance = newBal FROM account WHERE account_id = depAcc;");
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

            case 2: {
                System.out.println("Please ENTER your account number: ");
                int depAcc = input.nextInt();
                Account account = new Account(depAcc);

                System.out.println("How much you want to deposit?");
                amount = input.nextDouble();

                double accAmount = balances.get(depAcc);

                newBal = accAmount + amount;
                balances.put(account, newBal);

                // Update SQL Database
                try {
                    Main.s.executeUpdate("UPDATE balance = newBal FROM account WHERE account_id = depAcc;");
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
        }
    }

    // Show and store informations, such as account number, fee, interest, and etc.
    public static void accInfo(int SSN) {

    }
}
