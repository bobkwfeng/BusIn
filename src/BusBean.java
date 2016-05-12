
public class BusBean {
    private String vid;
    private String currentTime;
    private String arriveTime;
    private String route;
    private String direction;
    private int waitingMinute;
    private String latitude;
    private String longitude;
    public String getVid() {
        return vid;
    }
    public void setVid(String vid) {
        this.vid = vid;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public String getCurrentTime() {
        return currentTime;
    }
    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
    public String getArriveTime() {
        return arriveTime;
    }
    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }
    public String getRoute() {
        return route;
    }
    public void setRoute(String route) {
        this.route = route;
    }
    public String getDirection() {
        return direction;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }
    public int getWaitingMinute() {
        return waitingMinute;
    }
    public void setWaitingMinute(int waitingMinute) {
        this.waitingMinute = waitingMinute;
    }
    public void setWaitingMinute(String currentTime, String arriveTime) {
        String cur = currentTime.split(" ")[1];
        String arr = arriveTime.split(" ")[1];
        if (cur.split(":")[0].equals(arr.split(":")[0])) {
            int curr = Integer.parseInt(currentTime.split(":")[1]);
            int arrv = Integer.parseInt(arriveTime.split(":")[1]);
            this.waitingMinute = Math.abs(arrv - curr);
        } else {
            int curr = Integer.parseInt(currentTime.split(":")[1]);
            int arrv = Integer.parseInt(arriveTime.split(":")[1]);
            this.waitingMinute = arrv - curr + 60;
        }
    }
    public String toString() {
        return this.route + ";" + this.direction + ";" + this.waitingMinute + ";" + this.latitude + ";" + this.longitude;
    }
}
