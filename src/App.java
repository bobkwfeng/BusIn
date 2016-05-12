import java.util.List;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

public class App {
    public static void main(final String[] args) {
        Undertow server = Undertow.builder()
                .addHttpListener(8080, "ec2-52-87-181-156.compute-1.amazonaws.com")
                .setHandler(new HttpHandler() {
                    public void handleRequest(final HttpServerExchange exchange) throws Exception {
                        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                        try {
                            String path = exchange.getRequestPath();
                            String info = null;
                            String latitude = exchange.getQueryParameters().get("latitude").getFirst();
                            String longitude = exchange.getQueryParameters().get("longitude").getFirst();
                            if (path != null && path.equals("/stop")) {
                                info = stop(latitude, longitude);
                            }
                            if (path != null && path.equals("/bus")) {
                                info = bus(latitude, longitude);
                            }
                            exchange.getResponseSender().send(info);
                        } catch (Exception e) {
                            exchange.getResponseSender().send("this is an error page.");
                        }
                    }
                }).build();
        server.start();
    }
    private static String buildStopInfo(List<StationBean> list) {
        StringBuilder builder = new StringBuilder();
        for (StationBean item: list) {
            builder.append(item.getName() + ";" + item.getLatitude() + ";" + item.getLongitude() + "\n");
        }
        return builder.toString();
    }
    private static String stop(String latitude, String longitude) {
        double lat = Double.parseDouble(latitude);
        double lng = Double.parseDouble(longitude);
        List<StationBean> list;
        try {
            list = AbstractDAOFactory.getDAOFactory().getStationDAO().getStationList(lat, lng);
            return buildStopInfo(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private static String bus(String latitude, String longitude) throws Exception {
        double lat = Double.parseDouble(latitude);
        double lng = Double.parseDouble(longitude);
        StationBean stop = AbstractDAOFactory.getDAOFactory().getStationDAO().getNearestStation(lat, lng);
        List<BusBean> list = HttpUtil.getBuses(stop.getStopId());
        return buildBusInfo(list);
    }
    private static String buildBusInfo(List<BusBean> list) {
        StringBuilder builder = new StringBuilder();
        for (BusBean item: list) {
            if (item.getWaitingMinute() == 1 || item.getWaitingMinute() == 0) {
                builder.append(item.getRoute() + ";" + item.getDirection() + ";" + item.getWaitingMinute() + " Minute;" + item.getLatitude() + ";" + item.getLongitude() + "\n");
            } else {
                builder.append(item.getRoute() + ";" + item.getDirection() + ";" + item.getWaitingMinute() + " Minutes;" + item.getLatitude() + ";" + item.getLongitude() + "\n");
            }
        }
        return builder.toString();
    }
}