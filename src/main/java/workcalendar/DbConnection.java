package workcalendar;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbConnection {
    private static final String dbUrl="jdbc:mysql://localhost";
    private static final String dbPort=":3306";
    private static final String dbName="/workcalendar";
    private static final String dbUser="root";
    private static final String dbPassword= StringUtils.EMPTY;
    private Connection myConnection;
    public DbConnection(){
        try {
            this.myConnection= DriverManager.getConnection(dbUrl+dbPort+dbName, dbUser , dbPassword);
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public Connection getMyConnection() {
        return myConnection;
    }
}