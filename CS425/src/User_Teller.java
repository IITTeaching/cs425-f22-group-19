import java.util.Scanner;
public class User_Teller {
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
            System.out.println("Enter 1: Create/Remove Account");	
            System.out.println("Enter 2: Withdrawl");
            System.out.println("Enter 3: Deposit");
            System.out.println("Enter 4: Check Balance");
            System.out.println("Enter 5: Logout");
            int action = scan.nextInt();
            switch (action) {
                case 1 -> {
                    System.out.println("Select Option:");
                    System.out.println("Enter 1: Create Account");
                    System.out.println("Enter 2: Remove Account");
                    int action1 = scan.nextInt();
                    switch (action1) {
                        case 1 ->
                            Account.create(SSN);

                        case 2 ->
                            Account.delete(SSN);
                    }
                }
                case 2 ->
                    Account.withdrawal(SSN);

                case 3 ->
                    Account.deposit(SSN);

                case 4 ->
                    Account.accInfo(SSN);

                case 5 -> {
                    System.out.println("Logged out.");
                    loggedIn = false;
                }
                default ->
                    System.out.println("Invalid option selected, please try again.");
            }
        }
    }
}
