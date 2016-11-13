package sofiyskaVoda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PlannedRepaires {

	public static void main(String[] args) throws IOException {
		
		String serverName = "gis.sofiyskavoda.bg";
		int port = 6080;
		
		String result = getJsonBoject(serverName, port);
		
		getMapPointsLatLon(result);
		
		System.out.println("End of program!");
		
	}
	
	public static String getJsonBoject(String serverName, int port) throws IOException {
		Socket client = new Socket(InetAddress.getByName(serverName), port);
		PrintWriter pw = new PrintWriter(client.getOutputStream());
		
		pw.println("GET /arcgis/rest/services/InfrastructureAlerts/MapServer/3/query?d=1478461691883&f=json&where=ACTIVESTATUS%20%3D%20%27Confirmed%27&returnGeometry=true&spatialRel=esriSpatialRelIntersects&outFields=*&outSR=102100 HTTP/1.1");
		pw.println("Host: gis.sofiyskavoda.bg");
		pw.println("");
		pw.flush();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
		
		String line;
		String result = "";
		
		while(true) {
			line = br.readLine();
			
			if(line.length() <= 4 && line.length() > 0) result += result = br.readLine();
			if(line.equals("0")) break;
		}
		
//		System.out.println("result is: " + result + "\n");
		br.close();
		client.close();
		return result;
	}

	public static void getMapPointsLatLon(String result) {
		JSONObject obj = new JSONObject(result);
		
		JSONArray featuresArray = obj.getJSONArray("features");
//		System.out.println("feature array: " + featuresArray + "\n");
		
		List<RepairLocation> allLocations = new ArrayList<>();
		
		for (int i = 0; i < featuresArray.length(); i++) {
			
			JSONObject featureObject = featuresArray.getJSONObject(i);
//			System.out.println("feature object: " + featureObject + "\n");
			
			JSONObject featureGeo = featureObject.getJSONObject("geometry");
//			System.out.println("feature geo: " + featureGeo + "\n");
			
			JSONArray rings = featureGeo.getJSONArray("rings");
//			System.out.println("rings: " + rings + "\n");
			
			RepairLocation repairLocation = new RepairLocation();
			JSONArray mapPoints = rings.getJSONArray(0);
			
			for (int j = 0; j < mapPoints.length(); j++) {
				JSONArray point = mapPoints.getJSONArray(j);
				repairLocation.addPoint(point.getDouble(0), point.getDouble(1));
			}
			
			allLocations.add(repairLocation);
		}
		
		for (RepairLocation repairLocation : allLocations) {
			repairLocation.print();
		}
	}
}
