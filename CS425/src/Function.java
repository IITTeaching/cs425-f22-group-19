import java.sql.*;
import java.util.Hashtable;
import java.util.Scanner;
import java.lang.*;
import java.util.ArrayList;

public class Function {

    // (FINISHED)
   public static void CreateAccount(int SSN) throws Exception{
    Scanner scan = new Scanner(System.in);
    int type = -1;
    String accType = "";
    while(true){
        System.out.println("Select account type. \nEnter 1: Checking \nEnter 2: Savings \nEnter 3: Cancel");
        type = scan.nextInt();
        switch(type)
        {
            case 1:{
                accType = "Checking";
                break;
            }
            case 2: {
                accType = "Savings";
                break;
            }
            case 3: {
                System.out.println("Create account cancelled.");
                return;
            }
            default: {
                System.out.println("Wrong account type.");
                return;
            }
        }
        if(type == 1 || type == 2 || type == 3){
            break;
        }
    }
    
    // Ask customer wants to allow negative amount
    int allow = -1;
    boolean negAllow = false;
    while(true){
        System.out.println("Do you want to allow negative balance with your new account?");
        System.out.println("Enter 1: Yes\nEnter 2: No");
        allow = scan.nextInt();
        negAllow = allow == 1;
        if(allow == 1 || allow == 2){
            break;  
        }
        else{
            System.out.println("Invalid selection entered, please try again.");
        }
    }
    
    // Update to SQL Database
    try {
        PreparedStatement pStmt = Main.c.prepareStatement("INSERT INTO account (ssn, type, balance, allow_negative) VALUES (?,?,?,?);");
        pStmt.setInt(1, SSN);
        pStmt.setString(2, accType);
        pStmt.setFloat(3, 0);
        pStmt.setBoolean(4, negAllow);
        pStmt.executeUpdate();
        System.out.println("Successfully created new "+ accType + " account.");
    } catch (Exception e) {
        System.out.println("Failed to create new account error: " + e.getMessage());
    }
   }

    // (FINISHED)
   public static void RemoveAccount(int SSN) throws Exception
   {
    ArrayList<Integer> accounts = new ArrayList<Integer>();
    int accID = -1;
    try 
    {
        
        System.out.println("Showing all accounts under customer profile: ");
        PreparedStatement pStmt1 = Main.c.prepareStatement("SELECT* from account WHERE ssn = ?;");
        pStmt1.setInt(1, SSN);
        ResultSet r1 = pStmt1.executeQuery();
        accounts.clear();
        while(r1.next()){
            System.out.println("AID: " + r1.getString("account_id") + 
                            " Type: " + r1.getString("type") + 
                            " Balance: " + r1.getString("balance"));
                            accounts.add(r1.getInt("account_id"));
        }
        r1.close();
        if(accounts.isEmpty()){
            System.out.println("You do not have any open accounts.");
            return;
        }
        Scanner input = new Scanner(System.in);
        accID = -1;
        while(!accounts.contains(accID)){
            System.out.println("Select account you want to delete, enter AID: ");
            System.out.println("Enter -1 to cancel. ");
            accID = input.nextInt();
            if(accID == -1){
                System.out.println("Account removal cancelled.");
                return;
            }
            if(!accounts.contains(accID))
                System.out.println("Account with AID: " + accID + " does not exist, please try again.");
        }
        Statement s = Main.c.createStatement();           
        PreparedStatement pStmt = Main.c.prepareStatement("DELETE from account WHERE account_id = ?;");
        pStmt.setInt(1, accID);
        pStmt.executeUpdate();
        System.out.println("Successfully removed account #" + accID + ".");
    }
    catch (Exception e) {
        System.err.println("Failed to remove account at ID: " + accID);
    }  
   }

    // (FINISHED)
   public static void ShowBalance(int SSN) throws Exception
   {
        ArrayList<Integer> accounts = new ArrayList<Integer>();
        int accID = -1;
        try 
        {
            System.out.println("Showing balance for all accounts under customer profile: ");
            PreparedStatement pStmt1 = Main.c.prepareStatement("SELECT* from account WHERE ssn = ?;");
            pStmt1.setInt(1, SSN);
            ResultSet r1 = pStmt1.executeQuery();
            accounts.clear();
            while(r1.next()){
                System.out.println("AID: " + r1.getString("account_id") + 
                                " Type: " + r1.getString("type") + 
                                " Balance: " + r1.getFloat("balance"));
                                accounts.add(r1.getInt("account_id"));
			}
			r1.close();
            if(accounts.isEmpty()){
                System.out.println("You do not have any open accounts.");
                return;
            }
        }
        catch (Exception e) {
            System.err.println("Failed to show balance for AID #" + accID + " " + e.getMessage());
        } 
   }

   // All transactions for a certain month for acustomer
   public static void ShowStatement(int SSN) throws Exception{

   }

   public static void Deposit(int SSN) throws Exception{
    // double newBal;
    //     double amount;

    //     Scanner input = new Scanner(System.in);

    //     Account custAcc = accounts.get(SSN);
    //     System.out.println("Is this account number to deposit?" + custAcc);
    //     System.out.println("1. Yes  2. No");
    //     int action = input.nextInt();
    //     switch (action) {
    //         case 1 -> {
    //             Account account = new Account(Integer.parseInt(custAcc.toString()));
    //             double accAmoun = balances.get(custAcc);
    //             System.out.println("How much you want to deposit?");
    //             amount = input.nextDouble();

    //             newBal = accAmoun + amount;
    //             balances.put(account, newBal);

    //             // Update SQL Database
    //             try {
    //                 Main.s.executeUpdate("UPDATE balance = newBal FROM account WHERE account_id = custAcc;");
    //                 Main.c.commit();
    //             } catch (Exception e) {
    //                 System.err.println("An error occurred: " + e);
    //                 System.err.println("\n\nFOR THIS PROGRAM TO WORK YOU HAVE TO HAVE A POSTGRES SERVER RUNNING LOCALLY (OR DOCKER) AT "
    //                         + Main.JDBC_HOST
    //                         + " WITH PORT " + Main.JDBC_PORT
    //                         + " AND DATABASE " + Main.JDBC_DB
    //                         + " AND USER " + Main.DBUSER
    //                         + " WITH PASSWORD " + Main.DBPASSWD);
    //             }
    //         }
    //         case 2 -> {
    //             System.out.println("Please ENTER your account number: ");
    //             int depAcc = input.nextInt();
    //             Account account = new Account(depAcc);

    //             System.out.println("How much you want to deposit?");
    //             amount = input.nextDouble();

    //             double accAmount = balances.get(depAcc);

    //             newBal = accAmount + amount;
    //             balances.put(account, newBal);

    //             // Update SQL Database
    //             try {
    //                 Main.s.executeUpdate("UPDATE balance = newBal FROM account WHERE account_id = depAcc;");
    //                 Main.c.commit();
    //             } catch (Exception e) {
    //                 System.err.println("An error occurred: " + e);
    //                 System.err.println("\n\nFOR THIS PROGRAM TO WORK YOU HAVE TO HAVE A POSTGRES SERVER RUNNING LOCALLY (OR DOCKER) AT "
    //                         + Main.JDBC_HOST
    //                         + " WITH PORT " + Main.JDBC_PORT
    //                         + " AND DATABASE " + Main.JDBC_DB
    //                         + " AND USER " + Main.DBUSER
    //                         + " WITH PASSWORD " + Main.DBPASSWD);
    //             }
    //         }

    //         default ->
    //                 System.out.println("Invalid option selected, please try again.");
    //     }

   }
   public static void Withdrawl(int SSN) throws Exception{
    // double newBal;
    //     double amount;

    //     Scanner input = new Scanner(System.in);

    //     Account custAcc = accounts.get(SSN);
    //     System.out.println("Is this account number to deposite?" + custAcc);
    //     System.out.println("1. Yes  2. No");
    //     int action = input.nextInt();

    //     switch (action) {
    //         case 1 -> {
    //             Account account = new Account(Integer.parseInt(custAcc.toString()));
    //             double accAmoun = balances.get(custAcc);
    //             System.out.println("How much you want to withdrawal?");
    //             amount = input.nextDouble();

    //             if (accAmoun >= amount) {
    //                 newBal = accAmoun - amount;
    //                 balances.put(account, newBal);
    //             } else if (!negAllow)
    //                 System.out.println("Cannot withdrawal " + amount + ", it will be overdraft.");

    //             else {
    //                 newBal = accAmoun - amount;
    //                 balances.put(account, newBal);
    //             }

    //             // Update SQL Database
    //             try {
    //                 Main.s.executeUpdate("UPDATE balance = newBal FROM account WHERE account_id = custAcc;");
    //                 Main.c.commit();
    //             } catch (Exception e) {
    //                 System.err.println("An error occurred: " + e);
    //                 System.err.println("\n\nFOR THIS PROGRAM TO WORK YOU HAVE TO HAVE A POSTGRES SERVER RUNNING LOCALLY (OR DOCKER) AT "
    //                         + Main.JDBC_HOST
    //                         + " WITH PORT " + Main.JDBC_PORT
    //                         + " AND DATABASE " + Main.JDBC_DB
    //                         + " AND USER " + Main.DBUSER
    //                         + " WITH PASSWORD " + Main.DBPASSWD);
    //             }
    //         }
    //         case 2 -> {
    //             System.out.println("Please ENTER your account number: ");
    //             int withAcc = input.nextInt();
    //             Account account = new Account(withAcc);
    //             double accAmoun = balances.get(withAcc);

    //             System.out.println("How much you want to withdrawal?");
    //             amount = input.nextDouble();

    //             // Check the balance is enough
    //             if (accAmoun >= amount) {
    //                 newBal = accAmoun - amount;
    //                 balances.put(account, newBal);
    //             } else if (!negAllow)
    //                 System.out.println("Cannot withdrawal " + amount + ", it will be overdraft.");

    //             else {
    //                 newBal = accAmoun - amount;
    //                 balances.put(account, newBal);
    //             }

    //             // Update SQL Database
    //             try {
    //                 Main.s.executeUpdate("UPDATE balance = newBal FROM account WHERE account_id = withAcc;");
    //                 Main.c.commit();
    //             } catch (Exception e) {
    //                 System.err.println("An error occurred: " + e);
    //                 System.err.println("\n\nFOR THIS PROGRAM TO WORK YOU HAVE TO HAVE A POSTGRES SERVER RUNNING LOCALLY (OR DOCKER) AT "
    //                         + Main.JDBC_HOST
    //                         + " WITH PORT " + Main.JDBC_PORT
    //                         + " AND DATABASE " + Main.JDBC_DB
    //                         + " AND USER " + Main.DBUSER
    //                         + " WITH PASSWORD " + Main.DBPASSWD);
    //             }
    //         }
    //         default ->
    //                 System.out.println("Invalid option selected, please try again.");
    //     }
   }

  // (FINISHED)
   public static void Transfer(int SSN) throws Exception{
    ArrayList<Integer> accounts = new ArrayList<Integer>();
    int accID_from = -1;
    int accID_to = -1;
    try 
    {
        // Select account to transfer from
        System.out.println("Showing all accounts under customer profile: ");
        PreparedStatement pStmt1 = Main.c.prepareStatement("SELECT* from account WHERE ssn = ?;");
        pStmt1.setInt(1, SSN);
        ResultSet r1 = pStmt1.executeQuery();
        accounts.clear();
        while(r1.next()){
            System.out.println("AID: " + r1.getString("account_id") + 
                            " Type: " + r1.getString("type") + 
                            " Balance: " + r1.getString("balance"));
                            accounts.add(r1.getInt("account_id"));
        }
        r1.close();
        if(accounts.isEmpty()){
            System.out.println("You do not have any open accounts.");
            return;
        }
        Scanner input = new Scanner(System.in);
        accID_from = -1;
        while(!accounts.contains(accID_from)){
            System.out.println("Select account you want to transfer from: ");
            accID_from = input.nextInt();
            if(!accounts.contains(accID_from))
                System.out.println("Account with AID: " + accID_from + " does not exist, please try again.");
        }

        // Select account to transfer funds to
        accounts.clear();
        System.out.println("Showing all accounts you may transfer to: ");
        PreparedStatement pStmt2 = Main.c.prepareStatement("SELECT* from account WHERE account_id != ?;");
        pStmt2.setInt(1, accID_from);
        ResultSet r2 = pStmt2.executeQuery();
        accounts.clear();
        while(r2.next()){
            System.out.println("AID: " + r2.getString("account_id") + 
                            " Type: " + r2.getString("type") + 
                            " Balance: " + r2.getString("balance"));
                            accounts.add(r2.getInt("account_id"));
        }
        r2.close();
        accID_to = -1;
        while(!accounts.contains(accID_to)){
            System.out.println("Select account you want to transfer to: ");
            accID_to = input.nextInt();
            if(!accounts.contains(accID_to))
                System.out.println("Account with AID: " + accID_to + " does not exist, please try again.");
        }

        // Get account balance
        float account_balance = -1;
        Statement s = Main.c.createStatement();           
        PreparedStatement pStmt3 = Main.c.prepareStatement("SELECT balance FROM account WHERE account_id = ?;");
        pStmt3.setInt(1, accID_from);
        ResultSet r = pStmt3.executeQuery();
        while(r.next()){
            account_balance = r.getFloat("balance");
        }
        r.close();

        // Enter transfer amount
        int transfer_amount = -1;
        while(transfer_amount <= 0 || account_balance - transfer_amount < 0){
            System.out.println("Your current balance (Maximum Transfer): " + account_balance);
            System.out.println("Enter amount to transfer: ");
            transfer_amount = input.nextInt();
            if(transfer_amount <= 0)
                System.out.println("Enter valid transfer amount.");
            else if(account_balance - transfer_amount < 0){
                System.out.println("You do not have sufficient funds to transfer this amount, try again.");
            }
        }
       
        // Decide whether internal or external transfer
        // Get from branch -->
        int from_branch = -1;
        PreparedStatement pStmt_i = Main.c.prepareStatement("SELECT* FROM customer WHERE ssn = ?;");
        pStmt_i.setInt(1, SSN);
        ResultSet ir = pStmt_i.executeQuery();
        while(ir.next()){
            from_branch = ir.getInt("home_branch");
        }
        ir.close();

        // Get to branch -->
        int to_branch = -1;
        int to_SSN = -1;
        PreparedStatement pStmt_i2 = Main.c.prepareStatement("SELECT* FROM account WHERE account_id = ?;");
        pStmt_i2.setInt(1, accID_to);
        ResultSet ir2 = pStmt_i.executeQuery();
        while(ir2.next()){
            to_SSN = ir2.getInt("ssn");
        }
        ir2.close();
        PreparedStatement pStmt_i3 = Main.c.prepareStatement("SELECT* FROM customer WHERE ssn = ?;");
        pStmt_i3.setInt(1, to_SSN);
        ResultSet ir3 = pStmt_i.executeQuery();
        while(ir3.next()){
            to_branch = ir3.getInt("home_branch");
        }
        ir3.close();

        String transfer_type = "";
        if(from_branch ==  to_branch)
            transfer_type = "Internal Transfer";
        else 
            transfer_type = "External Transfer";

        PreparedStatement pStmt = Main.c.prepareStatement("UPDATE account SET balance = balance - ? WHERE account_id = ?;");
        pStmt.setFloat(1, transfer_amount);
        pStmt.setInt(2, accID_from);
        pStmt.executeUpdate();
        pStmt = Main.c.prepareStatement("UPDATE account SET balance = balance + ? WHERE account_id = ?;");
        pStmt.setFloat(1, transfer_amount);
        pStmt.setInt(2, accID_to);
        pStmt.executeUpdate();
        pStmt = Main.c.prepareStatement("INSERT INTO transaction (account_id, type, amount, account_recipient, account_sender, transaction_date) VALUES(?,?,?,?,?,?);");
        pStmt.setInt(1, accID_from);
        pStmt.setString(2, transfer_type);
        pStmt.setFloat(3, transfer_amount);
        pStmt.setInt(4, accID_to);
        pStmt.setInt(5, accID_from);
        Date cur_date = new Date(System.currentTimeMillis());
        pStmt.setDate(6, cur_date);
        pStmt.executeUpdate();
        System.out.println(transfer_amount + "$ "+ "transfer from AID# " + accID_from + " to " + "AID# " + accID_to + " successful.");
    }
    catch (Exception e) {
        System.out.println("Failed to make transfer error: " + e.getMessage());
    } 

   }

   public static void updateInterest(int SSN) throws Exception{

   }

   public static void updateOverdraft(int SSN) throws Exception{

}

    public static void updateAccountFee(int SSN) throws Exception{

    }

     // (FINISHED)
    public static int selectCustomer(){
        Scanner scan = new Scanner(System.in);
        int SSN = -1;
        while(true){
            System.out.println("Enter SSN of customer:");	
            SSN = scan.nextInt();
            try 
            {					
                PreparedStatement pStmt = Main.c.prepareStatement("SELECT from customer WHERE ssn = ?");
                pStmt.setInt(1, SSN);
                pStmt.executeQuery();
                ResultSet r1 = pStmt.executeQuery();
                int count = 0;
                while(r1.next()){
                    count++;
                }
                r1.close();
                if(count > 0)
                    break;
                else 
                    System.out.println("Could not find any customer with entered SSN, please try again.");	
            } catch (Exception e) 
            {
                System.out.println("Failed to find customer with entered SSN: " + e.getMessage());	
            }
        }
        return SSN;
    }
}
