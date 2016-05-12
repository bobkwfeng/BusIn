
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
        double a = Math.abs(latitude - this.latitude);
        double b = Math.abs(longitude - this.longitude);
        this.distance = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }
}
