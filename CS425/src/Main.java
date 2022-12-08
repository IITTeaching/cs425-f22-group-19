import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.Scanner;

public class Main {

	public static final String JDBC_DRIVER = "org.postgresql.Driver";
	public static final String JDBC_DB = "bank";
	public static final String JDBC_PORT = "5432";
	public static final String JDBC_HOST = "104.194.112.188";
	public static final String JDBC_URL = "jdbc:postgresql://" + JDBC_HOST + ":" + JDBC_PORT + "/" + JDBC_DB;
	public static final String DBUSER = "postgres";
	public static final String DBPASSWD = "abc123";
    public static Connection c;
	public static Statement s;
	public static int loggedinSSN = 0;

	public static void main (String[] args) throws Exception {
		try {
			// load the driver based on the drivers class name
			Class.forName(JDBC_DRIVER);
			// create a connection
			c = DriverManager.getConnection(JDBC_URL, DBUSER, DBPASSWD);
            System.out.println("Connection to Bank Database Successful.");	
            login();
		}
		catch (Exception e) {
			System.err.println("An error occurred: " + e.toString());
			System.err.println("\n\nFOR THIS PROGRAM TO WORK YOU HAVE TO HAVE A POSTGRES SERVER RUNNING LOCALLY (OR DOCKER) AT "
							   + JDBC_HOST
							   + " WITH PORT " + JDBC_PORT
							   + " AND DATABASE " + JDBC_DB
							   + " AND USER " + DBUSER
							   + " WITH PASSWORD " + DBPASSWD);
		}
	}
	
    public static void login() throws Exception
	{
			Scanner scan = new Scanner(System.in);
            while(true)
			{

            int role = -1;
			while(true)
			{
				System.out.println("Select Role:");	
				System.out.println("Enter 1: Customer");	
				System.out.println("Enter 2: Employee");
				System.out.println("Enter 3: Exit");
				role = scan.nextInt();
				if(role == 1 || role == 2 || role == 3)
					break;
			}
			if(role == 3){
				System.out.println("Exited.");
				return;
			}
		
            int user_type = -1;
			int SSN = 0;
			while(true)
			{
				

				// Find Customer
				if(role == 1)
				{
					System.out.println("Enter Customer SSN:");	
					SSN = scan.nextInt();
					try {					
						PreparedStatement pStmt = Main.c.prepareStatement("SELECT from customer WHERE ssn = ?");
						pStmt.setInt(1, SSN);
						ResultSet r1 = pStmt.executeQuery();
						int count = 0;
						while(r1.next()){
							count++;
						}
						r1.close();
						if(count > 0){
							user_type = 3;
							break;
						}
						else {
							System.out.println("Could not find any customer with entered SSN, please try again.");	
							user_type = -1;
						}
							
					} catch (Exception e) {
						System.out.println("Failed to find customer with entered SSN: " + e.getMessage());	
						user_type = -1;
				  }
				}// Find Employee
				else if(role == 2)
				{
					System.out.println("Enter Employee SSN:");	
					SSN = scan.nextInt();
					loggedinSSN = SSN;
					try {					
						PreparedStatement pStmt = Main.c.prepareStatement("SELECT* from employee WHERE ssn = ?");
						pStmt.setInt(1, SSN);
						ResultSet r1 = pStmt.executeQuery();
						int count = 0;
						String found_role = "";
						while(r1.next()){
							count++;
							found_role = r1.getString("role");
						}
						r1.close();
						if(count > 0)
						{
							
							if(found_role.equals("Manager") ){
								user_type = 1;
								break;
							}
							else if (found_role.equals("Teller")){
								user_type = 2;
								break;
							}
							else{
								System.out.println("Could not find correct role with entered SSN, please try again.");	
							}
						}						
						else 
						{
							System.out.println("Could not find any employee with entered SSN, please try again.");	
							user_type = -1;
						}
							
					} catch (Exception e) {
						System.out.println("Failed to find employee with entered SSN: " + e.getMessage());	
						user_type = -1;
				  }
				}				
			}
			
			// Ask the user to enter ssn
			// based on ssn search database to find what the user role 
			// if role is x set user_type to corresponding number
			// pass the ssn to start functions
			
            switch(user_type){
                case 1:{
                    System.out.println("Logged in as Manager.");
                    User_Manager.start(SSN);
                    break;
                }
                case 2:{
                    System.out.println("Logged in as Teller.");
                    User_Teller.start(SSN);
                    break;
                }
                case 3:{
                    System.out.println("Logged in as Customer.");
                    User_Customer.start(SSN);
                    break;
                }
                case 4:{
                    System.out.println("Exited.");
                    return;
                }
                default:{
                    System.out.println("Invalid user selected, please try again.");
                    break;
                }   
            }
        }
    }

    	// method for printing results for any query
	public static void printQueryResults(Connection c, String sql) {
		try {
			Statement s = c.createStatement();
			ResultSet r = s.executeQuery(sql);
			ResultSetMetaData md = r.getMetaData();
			int numCols = md.getColumnCount();

			System.out.printf("================================================================================" +
							  "\nQUERY: %s" +
							  "\n================================================================================\n",
							  sql);
			while(r.next()) {
				System.out.print("(");
				for(int i = 1; i <= numCols; i++) {
					System.out.printf("%s: %s%s",
									  md.getColumnName(i),
									  r.getString(i),
									  i < numCols ? ", ": ""
						);					
				}
				System.out.println(")");					
			}
			
			r.close();
			s.close();
		}
		catch (SQLException e) {
			System.err.println("An error occurred: " + e.toString());			
		}
	}
	
}
