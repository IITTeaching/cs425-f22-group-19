
import java.util.Scanner;
public class User_Customer {
    public static void start() throws Exception{
        Scanner scan = new Scanner(System.in);
        boolean loggedIn = false;
        int SSN = 0;

        while(!loggedIn){
            System.out.println("Enter SSN or type -1 to cancel: ");
            SSN = scan.nextInt();
            if(SSN > 99999999 && SSN < 1000000000){
                if(Customer.exists(SSN))
                    loggedIn = true;
            }
            else if(SSN == -1)
                return;
        }

        while(loggedIn){ 
            System.out.println("Select Option:");	
            System.out.println("Enter 1: Create Account");	
            System.out.println("Enter 2: Remove Account");
            System.out.println("Enter 3: Withdrawal");
            System.out.println("Enter 4: Deposit");
            System.out.println("Enter 5: Check Balance");
            System.out.println("Enter 6: Logout");
            int action = scan.nextInt();
            switch(action){
                    case 1:{
                        Account.create(SSN);
                        break;
                    }
                    case 2:{
                        Account.delete(SSN);
                        break;
                    }
                    case 3:{
                        
                        break;
                    }
                    case 4:{
                        
                        break;
                    }
                    case 5:{
                        
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
