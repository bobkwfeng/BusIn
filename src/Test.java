import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class Test {
    
    private final static String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {
//        double lat = 40.443622;
//        double lng = -79.945917;
//        
//        List<StationBean> list = AbstractDAOFactory.getDAOFactory().getStationDAO().getStationList(lat, lng);
//        for (StationBean item: list) {
//            System.out.println(item.getStopId() + ", " + item.getName() + ", latitude: " + item.getLatitude() + ", longitude: " + item.getLongitude());
//        }
//        System.out.println("++++++++++++++++++++++++++++++");
//        StationBean near = AbstractDAOFactory.getDAOFactory().getStationDAO().getNearestStation(lat, lng);
//        System.out.println(near.getStopId() + ", " + near.getDistance() + ", " + near.getName());
        
        sendGet();
    }
    
    @SuppressWarnings("unused")
    private static void sendGet() throws Exception {

        String url = "http://realtime.portauthority.org/bustime/api/v1/getpredictions?key=ACQHPkGU8zrpCsEb2PKi3ffLH&stpid=4408";
        
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }


}
