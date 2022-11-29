import java.sql.*;

public class JDBC {
    public static final String JDBC_DRIVER = "org.postgresql.Driver";
    // postgres URLs are of the form: jdbc:postgresql://host:port/database
    public static final String JDBC_DB = "university";
    public static final String JDBC_PORT = "5450";
    public static final String JDBC_HOST = "127.0.0.1";
    public static final String JDBC_URL = "jdbc:postgresql://" + JDBC_HOST + ":" + JDBC_PORT + "/" + JDBC_DB;
    public static final String DBUSER = "postgres";
    public static final String DBPASSWD = "test";

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
