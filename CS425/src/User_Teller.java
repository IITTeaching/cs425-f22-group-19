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
            System.out.println("Enter 6: Logout");
            int action = scan.nextInt();
            switch(action){
                    case 1:{
                        Function.Withdrawl(SSN);
                        break;
                    }
                    case 2:{
                        Function.Deposit(SSN);
                        break;
                    }
                    case 3:{
                        Function.ShowBalance(SSN);
                        break;
                    }
                    case 4:{
                        Function.ShowStatment(SSN);
                        break;
                    }
                    case 5:{
                        Function.Transfer(SSN);
                        break;
                    }
                    case 6:{
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
