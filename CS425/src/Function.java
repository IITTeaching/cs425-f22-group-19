import java.sql.*;
import java.util.Hashtable;
import java.util.Scanner;
import java.lang.*;
import java.util.ArrayList;

public class Function {

    // SQL function for creating a new account
   public static void CreateAccount(int SSN) throws Exception{
    Scanner scan = new Scanner(System.in);
    int type = -1;
    String accType = "";
    // Ask the user about the account type
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

    // Remove an account
   public static void RemoveAccount(int SSN) throws Exception
   {
    // List all accounts under customer's SSN
    ArrayList<Integer> accounts = new ArrayList<Integer>();
    int accID = -1;
    try 
    {
        
        System.out.println("Showing all accounts under customer profile: ");
        PreparedStatement pStmt1 = Main.c.prepareStatement("SELECT* FROM account WHERE ssn = ?;");
        pStmt1.setInt(1, SSN);
        ResultSet r1 = pStmt1.executeQuery();
        accounts.clear();
        while(r1.next()){
            System.out.println("AID: " + r1.getString("account_id") + 
                            " Type: " + r1.getString("type") + 
                            " Balance: " + r1.getString("balance"));
                            // Add them to list for later access
                            accounts.add(r1.getInt("account_id"));
        }
        r1.close();
        // Check if list is empty, if it is we dont have any accounts
        if(accounts.isEmpty()){
            System.out.println("You do not have any open accounts.");
            return;
        }

        // Select account to delete
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

    // Show balance of all accounts under customer's SSN.
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

   // All transactions for a certain month for a customer
   public static void ShowStatement(int SSN) throws Exception{
    Scanner scan = new Scanner(System.in);
    //Finding customer's SSN
    int customerSSN;
    int accID;
    customerSSN = SSN;
    //select the account you want to display the statement
    try
    {
        PreparedStatement pStmt = Main.c.prepareStatement("SELECT * from account WHERE ssn = ?;");
        pStmt.setInt(1, customerSSN);
        pStmt.executeQuery();
        ResultSet r = pStmt.executeQuery();
        int count = 0;
        while(r.next()){
            count++;
            accID = r.getInt("account_id");
            int ssn = r.getInt("ssn");
            String balance = r.getString("balance");
            String type = r.getString("type");
            int ir = r.getInt("interest_rate");
            int of = r.getInt("overdraft_fee");
            int mf = r.getInt("monthly_fee");

            System.out.println("Option: " + count + "\t AccID: " + accID + "\t SSN: " + ssn + "\t Balance: " + balance 
            + "\t Type: " + type  + "\t Interest Rate: " + ir  + "\t Overdraft Fees: " + of + "\t Monthly Fees: " + mf);

		}
		r.close();
        if(count > 0){
            System.out.println("Select the customer's account id you want to modify: ");
        }else System.out.println("This customer has no accounts: " + customerSSN);
    }
    catch (Exception e)
    {
        System.out.println("Error: " + e.getMessage());
    }
    accID = scan.nextInt();
    
    //Display transactions for that account
    try
    {
        PreparedStatement pStmt = Main.c.prepareStatement("SELECT * from transaction WHERE account_id = ? AND EXTRACT(MONTH from transaction_date) = (EXTRACT(MONTH FROM CURRENT_DATE)-1);");
        pStmt.setInt(1, accID);
        pStmt.executeQuery();
        ResultSet r = pStmt.executeQuery();
        int count = 0;
        while(r.next()){
            count++;
            int transID = r.getInt("transaction_id");
            accID = r.getInt("account_id");
            String type = r.getString("type");
            String amount = r.getString("amount");
            String date = r.getString("transaction_date");
            int sender = r.getInt("account_sender");
            int recipient = r.getInt("account_recipient");
            

            System.out.println("\t Transaction ID: " + transID + "\t accID: " + accID + "\t Amount: " + amount 
            + "\t Type: " + type  + "\t Date: " + date  + "\t Sender: " + sender + "\t Recipient: " + recipient);

		}
		r.close();
        if(count > 0){
           
        }else System.out.println("No transactions found in the last month: " + customerSSN);
    }
    catch (Exception e)
    {
        System.out.println("Error: " + e.getMessage());
    }
   }

   // Show all pending transactions for an account
   public static void ShowPendingTransactions(int SSN) throws Exception{
    Scanner scan = new Scanner(System.in);
    
    //Finding customer's SSN
    int customerSSN;
    int accID;

    customerSSN = SSN;
    //select the account you want to display the statement
    try
    {
        PreparedStatement pStmt = Main.c.prepareStatement("SELECT * from account WHERE ssn = ?;");
        pStmt.setInt(1, customerSSN);
        pStmt.executeQuery();
        ResultSet r = pStmt.executeQuery();
        int count = 0;
        while(r.next()){
            count++;
            accID = r.getInt("account_id");
            int ssn = r.getInt("ssn");
            String balance = r.getString("balance");
            String type = r.getString("type");
            int ir = r.getInt("interest_rate");
            int of = r.getInt("overdraft_fee");
            int mf = r.getInt("monthly_fee");

            System.out.println("Option: " + count + "\t AccID: " + accID + "\t SSN: " + ssn + "\t Balance: " + balance 
            + "\t Type: " + type  + "\t Interest Rate: " + ir  + "\t Overdraft Fees: " + of + "\t Monthly Fees: " + mf);

		}
		r.close();
        if(count > 0){
            System.out.println("Select the customer's account you want to view pending transactions for: ");
        }else System.out.println("This customer has no accounts.");
    }
    catch (Exception e)
    {
        System.out.println("Error: " + e.getMessage());
    }
    accID = scan.nextInt();
    
    //Display transactions for that account
    try
    {
        PreparedStatement pStmt = Main.c.prepareStatement("SELECT * from transaction WHERE account_id = ? AND EXTRACT(MONTH from transaction_date) = (EXTRACT(MONTH FROM CURRENT_DATE));");
        pStmt.setInt(1, accID);
        pStmt.executeQuery();
        ResultSet r = pStmt.executeQuery();
        int count = 0;
        while(r.next()){
            count++;
            int transID = r.getInt("transaction_id");
            accID = r.getInt("account_id");
            String type = r.getString("type");
            String amount = r.getString("amount");
            String date = r.getString("transaction_date");
            int sender = r.getInt("account_sender");
            int recipient = r.getInt("account_recipient");
            

            System.out.println("\t Transaction ID: " + transID + "\t accID: " + accID + "\t Amount: " + amount 
            + "\t Type: " + type  + "\t Date: " + date  + "\t Sender: " + sender + "\t Recipient: " + recipient);

		}
		r.close();
        if(count > 0){
            //System.out.println("Select the customer's account id you want to modify: ");
        }else System.out.println("No transactions found in the last month: " + customerSSN);
    }
    catch (Exception e)
    {
        System.out.println("Error: " + e.getMessage());
    }
    System.out.println("Enter -1 to exit: ");
    if(scan.nextInt() == -1){
        scan.close();
        return;
    }
    
   }

   // Deposit funds into account
   public static void Deposit(int SSN) throws Exception{
       Hashtable<Integer, Float> accounts = new Hashtable<>();
       float newBal;
       float currBal;

       Scanner input = new Scanner(System.in);

       try {
        // Display all accounts a customer has under SSN
           System.out.println("Showing all accounts under customer profile: ");
           PreparedStatement pStmt1 = Main.c.prepareStatement("SELECT * FROM account WHERE ssn = ?;");
           pStmt1.setInt(1, SSN);
           ResultSet r1 = pStmt1.executeQuery();

           while(r1.next()){
               System.out.println("AID: " + r1.getString("account_id") +
                       " Type: " + r1.getString("type") +
                       " Balance: " + r1.getFloat("balance"));
               accounts.put(r1.getInt("account_id"), r1.getFloat("balance"));
           }
           r1.close();

           // Ask the user which account to deposit into
           int custAcc = -1;
           while(!accounts.containsKey(custAcc)){
            System.out.println("Which account do you deposit to?");
           custAcc = input.nextInt();
           }
           // Ask how much
           float amount = 0;
           while(amount <= 0){
            System.out.println("How much do you want to deposit?");
            amount = input.nextFloat();
           }
           currBal = accounts.get(custAcc);

           // Update SQL table and creat enew transaction row based on data.
           newBal = currBal + amount;
           accounts.put(custAcc, newBal);
           PreparedStatement pStmt = Main.c.prepareStatement("UPDATE account SET balance = ? WHERE account_id = ?;");
           pStmt.setFloat(1, newBal);
           pStmt.setInt(2, custAcc);
           pStmt.executeUpdate();
           System.out.println("Succesfully deposited " + amount);

           pStmt = Main.c.prepareStatement("INSERT INTO transaction (account_id, type, amount, transaction_date) VALUES(?,?,?,?);");
               pStmt.setInt(1, custAcc);
               pStmt.setString(2, "Deposit");
               pStmt.setFloat(3, amount);
               Date cur_date = new Date(System.currentTimeMillis());
               pStmt.setDate(4, cur_date);
               pStmt.executeUpdate();
       } catch (Exception e) {
        System.out.println("Error depositing, error:" + e.getMessage());
       }
   }

   // Withdrawl funds from account
   public static void Withdrawal(int SSN) {
       Hashtable<Integer, Float> accounts = new Hashtable<>();
       Hashtable<Integer, Boolean> neg_allow_a = new Hashtable<>();
       float newBal;
       float currBal;
       int branch;

       Scanner input = new Scanner(System.in);

       try {
        // Display all accounts under customer SSN 
           System.out.println("Showing all accounts under customer profile: ");
           PreparedStatement pStmt1 = Main.c.prepareStatement("SELECT* FROM account WHERE ssn = ?;");
           pStmt1.setInt(1, SSN);
           ResultSet r1 = pStmt1.executeQuery();

           while(r1.next()){
               System.out.println("AID: " + r1.getString("account_id") +
                       " Type: " + r1.getString("type") +
                       " Balance: " + r1.getFloat("balance"));
               accounts.put(r1.getInt("account_id"), r1.getFloat("balance"));
               neg_allow_a.put(r1.getInt("account_id"), r1.getBoolean("allow_negative"));
           }
           r1.close();

           // Select account to withdrawl from.
           int custAcc = -1;
           while(!accounts.containsKey(custAcc)){
            System.out.println("Which account do you want to withdrawl from?");
           custAcc = input.nextInt();
           }
           // Ask how much to withdrawal
           float amount = 0;
           while(amount <= 0){
            System.out.println("How much do you want to withdrawl?");
            amount = input.nextFloat();
           }
           currBal = accounts.get(custAcc);
           newBal = currBal - amount;
           boolean negAllow1 = neg_allow_a.get(custAcc);

           if (currBal >= amount) {
               newBal = currBal - amount;
               accounts.put(custAcc, newBal);
               PreparedStatement pStmt = Main.c.prepareStatement("UPDATE account SET balance = ? WHERE account_id = ?;");
               pStmt.setFloat(1, newBal);
               pStmt.setInt(2, custAcc);
               pStmt.executeUpdate();
               System.out.println("Succesfully withdrawled " + amount);

                pStmt = Main.c.prepareStatement("INSERT INTO transaction (account_id, type, amount, transaction_date) VALUES(?,?,?,?);");
                pStmt.setInt(1, custAcc);
                pStmt.setString(2, "Withdrawal");
                pStmt.setFloat(3, amount);
                Date cur_date = new Date(System.currentTimeMillis());
                pStmt.setDate(4, cur_date);
                pStmt.executeUpdate();
           } 
           else if (!negAllow1){
            System.out.println("Cannot withdrawal " + amount + ", it will be overdraft.");
            return;
           }
           else {
               newBal = currBal - amount;
               accounts.put(custAcc, newBal);
               PreparedStatement pStmt = Main.c.prepareStatement("UPDATE account SET balance = ? WHERE account_id = ?;");
               pStmt.setFloat(1, newBal);
               pStmt.setInt(2, custAcc);
               pStmt.executeUpdate();
               System.out.println("Succesfully withdrawled " + "$" + amount);

               pStmt = Main.c.prepareStatement("INSERT INTO transaction (account_id, type, amount, transaction_date) VALUES(?,?,?,?);");
               pStmt.setInt(1, custAcc);
               pStmt.setString(2, "Withdrawal");
               pStmt.setFloat(3, amount);
               Date cur_date = new Date(System.currentTimeMillis());
               pStmt.setDate(4, cur_date);
               pStmt.executeUpdate();
           }
       } catch (Exception e) {
        System.out.println("Error withdrawing, error:" + e.getMessage());
       }
   }

  // Transfer funds from one account to another account
   public static void Transfer(int SSN) throws Exception{
    ArrayList<Integer> accounts = new ArrayList<Integer>();
    int accID_from = -1;
    int accID_to = -1;
    try 
    {
        // Display all customer accounts that a customer has under their SSN.
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
        // Ask which account to transfer from
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

            // Update SQL funds for each account and create transaction row 
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

    /*
       Ask manager which account id update the Interest
       If Costumer's home branch and manager's home branch is different,
      manager cannot update the overdraft fee.
     */
   public static void updateInterest(int SSN) throws Exception{
    if(find_branch(Main.loggedinSSN) != find_branch(SSN)){
        System.out.println("Costumer's home branch and manager's home branch is different, manager cannot update the interest.");
        return;
    }
    Scanner scan = new Scanner(System.in);
    int customerSSN;
    int accID;
    customerSSN = SSN;
    ArrayList<Integer> accounts = new ArrayList<Integer>();
    try
    {
        // Display all accounts info
        accounts.clear();
        PreparedStatement pStmt = Main.c.prepareStatement("SELECT * from account WHERE ssn = ?;");
        pStmt.setInt(1, customerSSN);
        pStmt.executeQuery();
        ResultSet r = pStmt.executeQuery();
        int count = 0;
        while(r.next()){
            count++;
            accID = r.getInt("account_id");
            int ssn = r.getInt("ssn");
            float balance = r.getFloat("balance");
            String type = r.getString("type");
            float ir = r.getFloat("interest_rate");
            int of = r.getInt("overdraft_fee");
            int mf = r.getInt("monthly_fee");
            accounts.add(r.getInt("account_id"));
            System.out.println("AccID: " + accID + "    SSN: " + ssn + "    Balance: " + balance 
            + "    Type: " + type  + "    Interest Rate: " + ir  + "    Overdraft Fee: " + of + "    Monthly Fee: " + mf);

		}
		r.close();
        if(count <= 0){
            System.out.println("This customer has no accounts: " + customerSSN);
            return;
        }
    }
    catch (Exception e)
    {
        System.out.println("Error: " + e.getMessage());
    }
    // Ask which account to update interest for
    accID = -1;
    while(!accounts.contains(accID)){
        System.out.println("Select account you want to update interest for: ");
        accID = scan.nextInt();
        if(!accounts.contains(accID))
            System.out.println("Account with AID: " + accID + " does not exist, please try again.");
    }
//Updating the interest rate
    System.out.println("Enter the new interest rate for the account");
    float newRate = 0;
    newRate = scan.nextFloat();
    try
        {
            PreparedStatement pStmt = Main.c.prepareStatement("UPDATE account SET interest_rate = ? WHERE account_id = ?;");
            pStmt.setFloat(1, newRate);
            pStmt.setInt(2, accID);
            pStmt.executeUpdate();
            System.out.println("Customer's interest rate updated successfully to " + newRate + "%.");
        }
        catch (Exception e)
        {
            System.out.println("Customer's interest rate was NOT updated successfully, error:"  + e.getMessage());
        }
   }

    /*
       Ask manager which account id update the Overdraft fee.
       If Costumer's home branch and manager's home branch is different,
      manager cannot update the overdraft fee.
     */
   public static void updateOverdraft(int SSN) throws Exception{
        if(find_branch(Main.loggedinSSN) != find_branch(SSN)){
            System.out.println("Costumer's home branch and manager's home branch is different, manager cannot update the overdraft fee.");
            return;
        }
       int branch = 0;
       int overdraft;
       int custSSN;
       int account;
       Scanner input = new Scanner(System.in);
        System.out.println("Enter new overdraft fee.");
        int newOverdraft = input.nextInt();

       try {
           custSSN = SSN;
           PreparedStatement pStmt3 = Main.c.prepareStatement("SELECT * FROM account WHERE ssn = ?;");
           pStmt3.setInt(1, custSSN);
           ResultSet r3 = pStmt3.executeQuery();

           System.out.println("Which account id want to change 'Overdraft Fee'?");
           while (r3.next()) {
               System.out.println("Account ID: " + r3.getInt("account_id")); 
           }
           r3.close();
           account = input.nextInt();

        overdraft = newOverdraft;
        PreparedStatement pStmt = Main.c.prepareStatement("UPDATE account SET overdraft_fee = ? WHERE account_id = ?;");
        pStmt.setInt(1, overdraft);
        pStmt.setInt(2, account);
        pStmt.executeUpdate();
        System.out.println("Successfully updated overdraft fee.");
       } catch (Exception e) {
        System.out.println("Error updating overdraft fee " + e.getMessage());
       }

}

    /*
      Ask manager which account id update the monthly account fee.
      If Costumer's home branch and manager's home branch is different,
      manager cannot update the monthly account fee.
    */
    public static void updateAccountFee(int SSN) throws Exception{
        if(find_branch(Main.loggedinSSN) != find_branch(SSN)){
            System.out.println("Costumer's home branch and manager's home branch is different, manager cannot update the account fee.");
            return;
        }
        int branch = 0;
        int fee;
        int custSSN;
        int account;
        Scanner input = new Scanner(System.in);
        System.out.println("Enter new account fee.");
        int newAccountFee = input.nextInt();
        try {
            custSSN = SSN;
            PreparedStatement pStmt3 = Main.c.prepareStatement("SELECT * FROM account WHERE ssn = ?;");
            pStmt3.setInt(1, custSSN);
            ResultSet r3 = pStmt3.executeQuery();
            System.out.println("Which account id want to change 'Monthly fee'?");
            while (r3.next()) {
                System.out.println("Account ID: " + r3.getInt("account_id"));
            }
            r3.close();
            account = input.nextInt();

            PreparedStatement pStmt = Main.c.prepareStatement("UPDATE account SET monthly_fee = ? WHERE account_id = ?;");
            pStmt.setInt(1, newAccountFee);
            pStmt.setInt(2, account);
            pStmt.executeUpdate();
            System.out.println("Successfully updated account fee.");
        } catch (Exception e) {
            System.out.println("Error updating account fee " + e.getMessage());
        }
    }

     // Ask the user for a customer id, used for tellers and managers before exeucting functions to get a speciifc SSN.
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

    public static int find_branch(int SSN)
    {
            try 
            {	int count = 0;	
                int count2 = 0;	
                PreparedStatement pStmt = Main.c.prepareStatement("SELECT* from customer WHERE ssn = ?");
                pStmt.setInt(1, SSN);
                pStmt.executeQuery();
                ResultSet r1 = pStmt.executeQuery();
                
                while(r1.next()){
                    count++;
                    //System.out.println(r1.getInt("home_branch"));	
                    return r1.getInt("home_branch");
                }
                r1.close();
                if(count < 1){
                    PreparedStatement pStmt2 = Main.c.prepareStatement("SELECT* from employee WHERE ssn = ?");
                    pStmt2.setInt(1, SSN);
                    pStmt2.executeQuery();
                    ResultSet r2 = pStmt2.executeQuery();     
                    while(r2.next()){
                        count2++;
                        //System.out.println(r2.getInt("branch"));	
                        return r2.getInt("branch");
                    }
                    r2.close();
                }
                  	
            } catch (Exception e) 
            {
                System.out.println("Failed to get branch id from SSN: " + e.getMessage());	
                return -1;
            }
        return -1;
    } 
}
