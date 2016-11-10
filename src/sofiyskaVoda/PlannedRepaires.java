package sofiyskaVoda;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONArray;
import org.json.JSONObject;



public class PlannedRepaires {
	

	public static void main(String[] args) throws IOException {
		
		String serverName = "gis.sofiyskavoda.bg";
		int port = 6080;
		
		String result = getJsonBoject(serverName, port);
		
		getMapPointsOnFirstRepair(result);
		
		
		
		//TODO: find the algorithm to convert it to longitude and latitude
		
//		JSONArray point = mapPoints.getJSONArray(0);
//		System.out.println("point: " + point);
//				
//		for (int i = 0; i < point.length(); i++) {
////			double coords = Double.parseDouble(point.get(i).toString());
////			System.out.println(coords);
//			
//			double distanceFromEquator = point.getDouble(0) / 1000;
//			double distanceFromGreenwich = point.getDouble(1) / 1000;
//
//			double greenwichLatitude = 51.4825757;
//			double greenwichLongitute = 0;
//			double equatorLat = 0;
//			double equatorLon = 0;
//			
//			
//			double r = 6378.1; //radius on Earth
//			double brng = 1.57; //Bearing is 90 degrees converted to radians
//			
//			double lat1 = Math.toRadians(greenwichLatitude);
//			double lon1 = Math.toRadians(greenwichLongitute);
//			
//			double lat2 = Math.asin(Math.sin(lat1) * Math.cos(distanceFromEquator / r) + Math.cos(lat1) * Math.sin(distanceFromEquator / r) * Math.cos(brng));
//			double lon2 = lon1 + Math.atan2(Math.sin(brng) * Math.sin(distanceFromEquator / r) * Math.cos(lat1), Math.cos(distanceFromEquator / r) - Math.sin(lat1) * Math.sin(lat2));
//
//			lat2 = Math.toDegrees(lat2);
//			lon2 = Math.toDegrees(lon2);
//			
//			System.out.println("latitude is: " + lat2 + "     |     longitude is: " + lon2);
//			
//		}
		
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
		
		/*
		 * next while loop is used for a test to check server's response
		 */
//		String nextLine = null;		
//		while ((line = br.readLine()) != null) {
//			
//			System.out.println(line);
////			if (line.length() == 0) {
////				nextLine = br.readLine();
////				System.out.println("JSON content: " + nextLine);
////			}
//		}
		
		while(true) {
			line = br.readLine();
			
			if(line.length() <= 4 && line.length() > 0) result += result = br.readLine();
			if(line.equals("0")) break;
		}
		
		System.out.println("result is: " + result + "\n");
		br.close();
		client.close();
		return result;
	}

	public static void getMapPointsOnFirstRepair(String result) {
		JSONObject obj = new JSONObject(result);
		
		JSONArray featuresArray = obj.getJSONArray("features");
//		System.out.println("feature array: " + featuresArray + "\n");
		
		JSONObject featureObject = featuresArray.getJSONObject(0);
//		System.out.println("feature object: " + featureObject + "\n");
		
		JSONObject featureGeo = featureObject.getJSONObject("geometry");
//		System.out.println("feature geo: " + featureGeo + "\n");
		
		JSONArray rings = featureGeo.getJSONArray("rings");
//		System.out.println("rings: " + rings + "\n");
		
		JSONArray mapPoints = rings.getJSONArray(0);
		System.out.println("MAP POINTS ON FIRST OBJECT: " + mapPoints + "\n");
	}
	
}
