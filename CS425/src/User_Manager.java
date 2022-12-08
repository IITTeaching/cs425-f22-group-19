import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.Scanner;
public class User_Manager {
    

    public static void start(int SSN) throws Exception{
        boolean loggedIn = true;
        while(loggedIn){
            Scanner scan = new Scanner(System.in);
            System.out.println("Select Option:");	
            System.out.println("Enter 1: Change Interest Fee");
            System.out.println("Enter 2 Change Account Fee");
            System.out.println("Enter 3: Change Overdraft Fees");
            System.out.println("Enter 4: Show Monthly Statment");
            System.out.println("Enter 5: Create Account");	
            System.out.println("Enter 6: Remove Account");
            System.out.println("Enter 7: Withdrawal");
            System.out.println("Enter 8: Deposit");
            System.out.println("Enter 9: Check Balance");
            System.out.println("Enter 10: Make a Transfer");
            System.out.println("Enter 11: Show Pending Transactions");
            System.out.println("Enter 12: Logout");
            int action = scan.nextInt();
            switch(action){
                    case 1:{
                        Function.updateInterest(Function.selectCustomer());
                        break;
                    }
                    case 2:{
                        Function.updateAccountFee(Function.selectCustomer());
                        break;
                    }
                    case 3:{
                        Function.updateOverdraft(Function.selectCustomer());
                        break;
                    }
                    case 4:{
                        Function.ShowStatement(Function.selectCustomer());
                        break;
                    }
                    case 5:{
                        Function.CreateAccount(Function.selectCustomer());
                        break;
                    }
                    case 6:{
                        Function.RemoveAccount(Function.selectCustomer());
                        break;
                    }
                    case 7:{
                        
                        Function.Withdrawal(Function.selectCustomer());
                        break;
                    }
                    case 8:{
                        Function.Deposit(Function.selectCustomer());
                        break;
                    }
                    case 9:{
                        Function.ShowBalance(Function.selectCustomer());
                        break;
                    }
                    case 10:{
                        Function.Transfer(Function.selectCustomer());
                        break;
                    }
                    case 11:{
                        Function.ShowPendingTransactions(Function.selectCustomer());
                        break;
                    }
                    case 12:{
                       System.out.println("Logged out.");
                       loggedIn = false;
                        break;
                    }
                    default:{
                        System.out.println("Invalid option selected, please try again.");
                        break;
                   }   
            }
        }
        }
            
}
