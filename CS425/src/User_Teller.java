import java.util.Scanner;
public class User_Teller {
    public static void start() throws Exception{
        boolean loggedIn = true;
        while(loggedIn){
            Scanner scan = new Scanner(System.in);
            System.out.println("Select Option:");	
            System.out.println("Enter 1: Create/Remove Account");	
            System.out.println("Enter 2: Withdrawl");
            System.out.println("Enter 3: Deposit");
            System.out.println("Enter 4: Check Balance");
            System.out.println("Enter 5: Logout");
            int action = scan.nextInt();
            switch(action){
                    case 1:{
                        
                        break;
                    }
                    case 2:{
                        
                        break;
                    }
                    case 3:{
                        
                        break;
                    }
                    case 4:{
                        
                        break;
                    }
                    case 5:{
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
