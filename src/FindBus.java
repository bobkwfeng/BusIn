

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FindBus
 */
@WebServlet("/FindBus")
public class FindBus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindBus() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; text/plain;  charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    String latitude = request.getParameter("latitude");
	    String longitude = request.getParameter("longitude");
        try {
            out.println(bus(latitude, longitude));
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.println();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
                builder.append(item.getRoute() + ";" + item.getDirection() + ";" + item.getWaitingMinute() + " Minute;" + item.getLatitude() + ";" + item.getLongitude() + "<br>");
            } else {
                builder.append(item.getRoute() + ";" + item.getDirection() + ";" + item.getWaitingMinute() + " Minutes;" + item.getLatitude() + ";" + item.getLongitude() + "<br>");
            }
        }
        return builder.toString();
    }
    
}
