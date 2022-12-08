
import java.util.Scanner;
public class User_Customer {
    public static void start(int SSN) throws Exception{
        boolean loggedIn = true;
        while(loggedIn)
        {   
            Scanner scan = new Scanner(System.in);
            System.out.println("Select Option:");	
            System.out.println("Enter 1: Create Account");	
            System.out.println("Enter 2: Remove Account");
            System.out.println("Enter 3: Withdrawal");
            System.out.println("Enter 4: Deposit");
            System.out.println("Enter 5: Check Balance");
            System.out.println("Enter 6: Monthly Statement");
            System.out.println("Enter 7: Make a Transfer");
            System.out.println("Enter 8: Show Pending Transactions");
            System.out.println("Enter 9: Logout");
            int action = scan.nextInt();
            switch(action){
                    case 1:{
                        Function.CreateAccount(SSN);
                        break;
                    }
                    case 2:{
                        Function.RemoveAccount(SSN);
                        break;
                    }
                    case 3:{
                        Function.Withdrawal(SSN);
                        break;
                    }
                    case 4:{
                        Function.Deposit(SSN);
                        break;
                    }
                    case 5:{
                        Function.ShowBalance(SSN);
                        break;
                    }
                    case 6:{
                        Function.ShowStatement(SSN);
                        break;
                    }
                    case 7:{
                        Function.Transfer(SSN);
                        break;
                    }
                    case 8:{
                        Function.ShowPendingTransactions(Function.selectCustomer());
                        break;
                    }
                    case 9:{
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
