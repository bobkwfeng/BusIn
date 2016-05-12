
public abstract class AbstractDAOFactory {
    public abstract StationDAO getStationDAO();
    public static AbstractDAOFactory getDAOFactory() {
        return new DAOFactory();
    }
}
