
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StationDAO {
    private Connection con = null;
    private String tableName = "stops";
    public StationDAO() {
        try {
            this.con = DAOFactory.createConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<StationBean> getStationList(double lat, double lng) throws Exception {
        double[] range = getRange(lat, lng);
        double left = range[0];
        double right = range[1];
        double botton = range[2];
        double top = range[3];
        return getStationList(left, right, botton, top);
    }
    public List<StationBean> getStationList(double left, double right, double botton, double top) throws Exception {
        try {
            List<StationBean> list = new ArrayList<StationBean>();
            String sql = String.format("select stop_id, stop_name, latitude, longitude from %s where latitude > %s and latitude < %s and longitude > %s and longitude < %s", this.tableName, left, right, botton, top);
            PreparedStatement pstmt = this.con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                StationBean station = null;
                station = new StationBean();
                station.setStopId(rs.getLong("stop_id"));
                station.setName((rs.getString("stop_name")));
                station.setLatitude(rs.getDouble("latitude"));
                station.setLongitude(rs.getDouble("longitude"));
                list.add(station);
            }
            rs.close();
            pstmt.close();
            DAOFactory.connectionPool.add(this.con);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            if (this.con != null) {
                try {
                    this.con.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            throw new Exception(e);
        }
    }
    public StationBean getNearestStation(double lat, double lng) throws Exception {
        List<StationBean> list = getStationList(lat, lng);
        double minDistance = Double.MAX_VALUE;
        StationBean nearest = null;
        for (StationBean item: list) {
            item.setDistance(lat, lng);
            if (minDistance > item.getDistance()) {
                nearest = item;
                minDistance = item.getDistance();
            }
        }
        return nearest;
    }
    private double[] getRange(double lat, double lng) {
        double[] range = new double[4];
        range[0] = lat - 0.005000d;
        range[1] = lat + 0.005000d;
        range[2] = lng - 0.005000d;
        range[3] = lng + 0.005000d;
        return range;
    }
}
