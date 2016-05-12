
public class StationBean {
    private long stopId;
    private String name;
    private double latitude;
    private double longitude;
    private double distance;
    public long getStopId() {
        return stopId;
    }
    public void setStopId(long stopId) {
        this.stopId = stopId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getDistance() {
        return distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }
    public void setDistance(double latitude, double longitude) {
    	double a = latitude - this.latitude;
        double b = longitude - this.longitude;
        double c = Math.pow(Math.sin(a / 2), 2) + Math.cos(latitude) * Math.cos(this.latitude) * Math.pow(Math.sin(b / 2), 2);
        double d = 2 * Math.atan2(Math.sqrt(c), Math.sqrt(1 - c));
        this.distance = d * 6371000;
    }
}
