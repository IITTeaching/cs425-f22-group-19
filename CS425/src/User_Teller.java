import java.util.Scanner;
public class User_Teller {
    public static void start(int SSN) throws Exception{
        boolean loggedIn = true;
        while(loggedIn)
        {   
            Scanner scan = new Scanner(System.in);
            System.out.println("Select Option:");	
            System.out.println("Enter 1: Withdrawal");
            System.out.println("Enter 2: Deposit");
            System.out.println("Enter 3: Check Balance");
            System.out.println("Enter 4: Monthly Statment");
            System.out.println("Enter 5: Make a Transfer");
            System.out.println("Enter 6: Show Pending Transactions");
            System.out.println("Enter 7: Logout");
            int action = scan.nextInt();
            switch(action){
                    case 1:{
                        Function.Withdrawal(Function.selectCustomer());
                        break;
                    }
                    case 2:{
                        Function.Deposit(Function.selectCustomer());
                        break;
                    }
                    case 3:{
                        Function.ShowBalance(Function.selectCustomer());
                        break;
                    }
                    case 4:{
                        Function.ShowStatement(Function.selectCustomer());
                        break;
                    }
                    case 5:{
                        Function.Transfer(Function.selectCustomer());
                        break;
                    }
                    case 6:{
                        Function.ShowPendingTransactions(Function.selectCustomer());
                        break;
                    }
                    case 7:{
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
