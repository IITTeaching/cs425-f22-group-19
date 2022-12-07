import java.sql.*;
import java.util.Hashtable;
import java.util.Scanner;
import java.lang.*;

public class Function {
   public static void CreateAccount(int SSN) throws Exception{
    System.out.println("Create six digit account number: ");
    Scanner input = new Scanner(System.in);
    accID = input.nextInt();
    Account account = new Account(accID);
    accounts.put(SSN, account);     // Store SSN and account number
    double balance = 0;
    balances.put(account, balance); // Store account number and its balance
    System.out.println("Select account type\n1. Checking 2. Saving");
    int type = input.nextInt();
    if (type == 1) accType = "Checking";
    else if (type == 2) accType = "Saving";
    else System.out.println("Wrong account type.");

    // Ask customer wants to allow negative amount
    System.out.println("Do you want to allow negative balance with your new account" + accID + "?");
    System.out.println("1. Yes  2. No");
    int allow = input.nextInt();
    negAllow = allow == 1;

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
        System.err.println("An error occurred: " + e);
        System.err.println("\n\nFOR THIS PROGRAM TO WORK YOU HAVE TO HAVE A POSTGRES SERVER RUNNING LOCALLY (OR DOCKER) AT "
                + Main.JDBC_HOST
                + " WITH PORT " + Main.JDBC_PORT
                + " AND DATABASE " + Main.JDBC_DB
                + " AND USER " + Main.DBUSER
                + " WITH PASSWORD " + Main.DBPASSWD);
    }
   }

   public static void RemoveAccount(int SSN) throws Exception{
    System.out.println("Type account ID want to delete: ");
    Scanner input = new Scanner(System.in);
    int delAcc = input.nextInt();

    if (accounts.containsKey(delAcc)) {
        accounts.remove(delAcc);
    }
    else  System.out.println("There is no account" + delAcc + "in our system.");

    // Update to SQL Database
    try {
        Main.s.executeUpdate("DELETE FROM account where account_id = delACC;");
        Main.c.commit();
    }
    catch (Exception e) {
        System.err.println("An error occurred: " + e);
        System.err.println("\n\nFOR THIS PROGRAM TO WORK YOU HAVE TO HAVE A POSTGRES SERVER RUNNING LOCALLY (OR DOCKER) AT "
                + Main.JDBC_HOST
                + " WITH PORT " + Main.JDBC_PORT
                + " AND DATABASE " + Main.JDBC_DB
                + " AND USER " + Main.DBUSER
                + " WITH PASSWORD " + Main.DBPASSWD);
    }
   }

   public static void ShowBalance(int SSN) throws Exception{
    Account account = accounts.get(SSN);
    double balance = balances.get(account);
    System.out.println("Your account " + account + " balance is " + " balance.");
   }

   // All transactions for a certain month for acustomer
   public static void ShowStatment(int SSN) throws Exception{

   }
   public static void Deposit(int SSN) throws Exception{
    double newBal;
        double amount;

        Scanner input = new Scanner(System.in);

        Account custAcc = accounts.get(SSN);
        System.out.println("Is this account number to deposit?" + custAcc);
        System.out.println("1. Yes  2. No");
        int action = input.nextInt();
        switch (action) {
            case 1 -> {
                Account account = new Account(Integer.parseInt(custAcc.toString()));
                double accAmoun = balances.get(custAcc);
                System.out.println("How much you want to deposit?");
                amount = input.nextDouble();

                newBal = accAmoun + amount;
                balances.put(account, newBal);

                // Update SQL Database
                try {
                    Main.s.executeUpdate("UPDATE balance = newBal FROM account WHERE account_id = custAcc;");
                    Main.c.commit();
                } catch (Exception e) {
                    System.err.println("An error occurred: " + e);
                    System.err.println("\n\nFOR THIS PROGRAM TO WORK YOU HAVE TO HAVE A POSTGRES SERVER RUNNING LOCALLY (OR DOCKER) AT "
                            + Main.JDBC_HOST
                            + " WITH PORT " + Main.JDBC_PORT
                            + " AND DATABASE " + Main.JDBC_DB
                            + " AND USER " + Main.DBUSER
                            + " WITH PASSWORD " + Main.DBPASSWD);
                }
            }
            case 2 -> {
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
                    System.err.println("An error occurred: " + e);
                    System.err.println("\n\nFOR THIS PROGRAM TO WORK YOU HAVE TO HAVE A POSTGRES SERVER RUNNING LOCALLY (OR DOCKER) AT "
                            + Main.JDBC_HOST
                            + " WITH PORT " + Main.JDBC_PORT
                            + " AND DATABASE " + Main.JDBC_DB
                            + " AND USER " + Main.DBUSER
                            + " WITH PASSWORD " + Main.DBPASSWD);
                }
            }

            default ->
                    System.out.println("Invalid option selected, please try again.");
        }

   }
   public static void Withdrawl(int SSN) throws Exception{
    double newBal;
        double amount;

        Scanner input = new Scanner(System.in);

        Account custAcc = accounts.get(SSN);
        System.out.println("Is this account number to deposite?" + custAcc);
        System.out.println("1. Yes  2. No");
        int action = input.nextInt();

        switch (action) {
            case 1 -> {
                Account account = new Account(Integer.parseInt(custAcc.toString()));
                double accAmoun = balances.get(custAcc);
                System.out.println("How much you want to withdrawal?");
                amount = input.nextDouble();

                if (accAmoun >= amount) {
                    newBal = accAmoun - amount;
                    balances.put(account, newBal);
                } else if (!negAllow)
                    System.out.println("Cannot withdrawal " + amount + ", it will be overdraft.");

                else {
                    newBal = accAmoun - amount;
                    balances.put(account, newBal);
                }

                // Update SQL Database
                try {
                    Main.s.executeUpdate("UPDATE balance = newBal FROM account WHERE account_id = custAcc;");
                    Main.c.commit();
                } catch (Exception e) {
                    System.err.println("An error occurred: " + e);
                    System.err.println("\n\nFOR THIS PROGRAM TO WORK YOU HAVE TO HAVE A POSTGRES SERVER RUNNING LOCALLY (OR DOCKER) AT "
                            + Main.JDBC_HOST
                            + " WITH PORT " + Main.JDBC_PORT
                            + " AND DATABASE " + Main.JDBC_DB
                            + " AND USER " + Main.DBUSER
                            + " WITH PASSWORD " + Main.DBPASSWD);
                }
            }
            case 2 -> {
                System.out.println("Please ENTER your account number: ");
                int withAcc = input.nextInt();
                Account account = new Account(withAcc);
                double accAmoun = balances.get(withAcc);

                System.out.println("How much you want to withdrawal?");
                amount = input.nextDouble();

                // Check the balance is enough
                if (accAmoun >= amount) {
                    newBal = accAmoun - amount;
                    balances.put(account, newBal);
                } else if (!negAllow)
                    System.out.println("Cannot withdrawal " + amount + ", it will be overdraft.");

                else {
                    newBal = accAmoun - amount;
                    balances.put(account, newBal);
                }

                // Update SQL Database
                try {
                    Main.s.executeUpdate("UPDATE balance = newBal FROM account WHERE account_id = withAcc;");
                    Main.c.commit();
                } catch (Exception e) {
                    System.err.println("An error occurred: " + e);
                    System.err.println("\n\nFOR THIS PROGRAM TO WORK YOU HAVE TO HAVE A POSTGRES SERVER RUNNING LOCALLY (OR DOCKER) AT "
                            + Main.JDBC_HOST
                            + " WITH PORT " + Main.JDBC_PORT
                            + " AND DATABASE " + Main.JDBC_DB
                            + " AND USER " + Main.DBUSER
                            + " WITH PASSWORD " + Main.DBPASSWD);
                }
            }
            default ->
                    System.out.println("Invalid option selected, please try again.");
        }
   }

   public static void Transfer(int SSN) throws Exception{

   }

   public static void updateInterest(int SSN) throws Exception{

   }

   public static void updateOverdraft(int SSN) throws Exception{

}

    public static void updateAccountFee(int SSN) throws Exception{

    }
}
