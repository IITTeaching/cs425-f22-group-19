import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class Main {

	public static final String JDBC_DRIVER = "org.postgresql.Driver";
	// postgres URLs are of the form: jdbc:postgresql://host:port/database
	public static final String JDBC_DB = "bank";
	public static final String JDBC_PORT = "5432";
	public static final String JDBC_HOST = "104.194.100.186";
	public static final String JDBC_URL = "jdbc:postgresql://" + JDBC_HOST + ":" + JDBC_PORT + "/" + JDBC_DB;
	public static final String DBUSER = "postgres";
	public static final String DBPASSWD = "abc123";
    public static Connection c;
	public static Statement s;

	public static void main (String[] args) throws Exception {
		try {
			// load the driver based on the drivers class name
			Class.forName(JDBC_DRIVER);
			// create a connection
			c = DriverManager.getConnection(JDBC_URL, DBUSER, DBPASSWD);
            
            System.out.println("Connection to Bank Database Successful.");	
            
            login();
            // PreparedStatement pStmt = c.prepareStatement("INSERT INTO branch (add1, add2, city, state, zip) VALUES(?,?,?,?,?)");
			
			// pStmt.setString(1, "Test Branch Add1");
			// pStmt.setString(2, "Test Branch Add2");
			// pStmt.setString(3, "Chicago");
			// pStmt.setString(4, "IL");
            // pStmt.setInt(5, 60305);
			// pStmt.executeUpdate();

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
	
    public static void login() throws Exception{
            char user_type;
            while(true){
            user_type = 'X';
            System.out.println("Select Login Role:");	
            System.out.println("Enter 1: Manager");	
            System.out.println("Enter 2: Loan Manager");
            System.out.println("Enter 3: Teller");
            System.out.println("Enter 4: Customer");
            System.out.println("Enter 5: Exit");
            user_type = ((char)System.in.read());
            switch((int)user_type-48){
                case 1:{
                    System.out.println("Logged in as Manager.");
                    User_Manager.start();
                    break;
                }
                case 2:{
                    System.out.println("Logged in as Loan Manager.");
                    User_LoanManager.start();
                    break;
                }
                case 3:{
                    System.out.println("Logged in as Teller.");
                    User_Teller.start();
                    break;
                }
                case 4:{
                    System.out.println("Logged in as Customer.");
                    User_Customer.start();
                    break;
                }
                case 5:{
                    System.out.println("Exited.");
                    return;
                }
                default:{
                    System.out.println("Invalid role selected, please try again.");
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
