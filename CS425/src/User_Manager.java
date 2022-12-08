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
            System.out.println("Enter 11: Logout");
            int action = scan.nextInt();
            switch(action){
                    case 1:{
                        Function.updateInterest(Function.selectCustomer());
                        break;
                    }
                    case 2:{
                        /*
                           To Update the Account fee function with manager SSN and new monthly account fee.
                               Ex) updateAccountFee(123456789, 3);
                           Ask manager which account id update the monthly account fee.
                        */
                        System.out.println("What is new Monthly Fee?");
                        int newAccountFee = scan.nextInt();
                        Function.updateAccountFee(SSN, newAccountFee);
                        break;
                    }
                    case 3:{
                        /*
                           To Update the Overdraft rate call the function with manager SSN and new overdraft fee.
                              Ex) updateOverdraft(123456789, 3);
                           Ask manager which account id update the Overdraft fee.
                        */
                        System.out.println("What is new Overdraft fee?");
                        int newOverdraft = scan.nextInt();
                        Function.updateOverdraft(SSN, newOverdraft);
                        break;
                    }
                    case 4:{
                        //Function.ShowStatement(Function.selectCustomer());
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
                        
                        //Function.Withdrawl(Function.selectCustomer());
                        break;
                    }
                    case 8:{
                        //Function.Deposit(Function.selectCustomer());
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
