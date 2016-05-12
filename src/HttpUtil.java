import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class HttpUtil {
    private final static String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {
        System.out.println(getBuses(4408));
    }
    
    private static String getBusList(long stopId) throws Exception {
        String url = "http://realtime.portauthority.org/bustime/api/v1/getpredictions?key=ACQHPkGU8zrpCsEb2PKi3ffLH&stpid=" + stopId;        
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        if (responseCode != 200) {
            System.out.println("bad response");
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            return null;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
    private static void addLocation(List<BusBean> list) throws Exception {
        StringBuilder sbuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sbuilder.append(list.get(i).getVid());
            if (i != list.size() - 1) {
                sbuilder.append(",");
            }
        }
        String xml = getLocationList(sbuilder.toString());
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        InputSource is;
        try {
            builder = factory.newDocumentBuilder();
            is = new InputSource(new StringReader(xml));
            Document doc = builder.parse(is);
            NodeList latList = doc.getElementsByTagName("lat");
            NodeList lonList = doc.getElementsByTagName("lon");
            for (int i = 0; i < latList.getLength(); i++) {
                list.get(i).setLatitude(latList.item(i).getTextContent());
                list.get(i).setLongitude(lonList.item(i).getTextContent());
            }
        } catch (ParserConfigurationException e) {
        } catch (SAXException e) {
        } catch (IOException e) {
        }
    }
    private static String getLocationList(String ids) throws Exception {
        String url = "http://realtime.portauthority.org/bustime/api/v1/getvehicles?key=ACQHPkGU8zrpCsEb2PKi3ffLH&vid=" + ids;        
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        if (responseCode != 200) {
            System.out.println("bad response");
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            return null;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
    public static List<BusBean> getBuses(long stopId) throws Exception {
        String xml = getBusList(stopId);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        InputSource is;
        try {
            builder = factory.newDocumentBuilder();
            is = new InputSource(new StringReader(xml));
            Document doc = builder.parse(is);
            NodeList vidList = doc.getElementsByTagName("vid");
            NodeList curList = doc.getElementsByTagName("tmstmp");
            NodeList arrList = doc.getElementsByTagName("prdtm");
            NodeList routeList = doc.getElementsByTagName("rt");
            NodeList dirList = doc.getElementsByTagName("rtdir");
            List<BusBean> list = new ArrayList<BusBean>();
            for (int i = 0; i < curList.getLength(); i++) {
                BusBean item = new BusBean();
                item.setVid(vidList.item(i).getTextContent());
                item.setCurrentTime(curList.item(i).getTextContent());
                item.setArriveTime(arrList.item(i).getTextContent());
                item.setRoute(routeList.item(i).getTextContent());
                item.setDirection(dirList.item(i).getTextContent());
                item.setWaitingMinute(item.getCurrentTime(), item.getArriveTime());
                list.add(item);
            }
            addLocation(list);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getLatitude() == null || list.get(i).getLongitude() == null) {
                    list.remove(i);
                }
            }
            return list;
        } catch (ParserConfigurationException e) {
        } catch (SAXException e) {
        } catch (IOException e) {
        }
        return null;
    }
}
