
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOFactory extends AbstractDAOFactory {
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String DBURL = "jdbc:mysql://localhost:3306/station";
    public static List<Connection> connectionPool = new ArrayList<Connection>();
    public static Connection createConnection() throws Exception {
        if (connectionPool.size() > 0) {
            return connectionPool.remove(connectionPool.size() - 1);
        }
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot Find MySQL Driver.");
            throw new Exception(e);
        }
        try {
            return (Connection) DriverManager.getConnection(DBURL, "", "");
        } catch (SQLException e) {
            System.out.println("Cannot Get Connection.");
            throw new Exception(e);
        }
    }
    @Override
    public StationDAO getStationDAO() {
        return new StationDAO();
    }
}
