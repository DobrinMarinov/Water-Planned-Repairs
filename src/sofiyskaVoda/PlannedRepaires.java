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

import org.json.JSONArray;
import org.json.JSONObject;



public class PlannedRepaires {
	

	public static void main(String[] args) throws IOException {
		
		String serverName = "gis.sofiyskavoda.bg";
		
		Socket client = new Socket(InetAddress.getByName(serverName), 6080);
		PrintWriter pw = new PrintWriter(client.getOutputStream());
		
		pw.println("GET /arcgis/rest/services/InfrastructureAlerts/MapServer/3/query?d=1478461691883&f=json&where=ACTIVESTATUS%20%3D%20%27Confirmed%27&returnGeometry=true&spatialRel=esriSpatialRelIntersects&outFields=*&outSR=102100 HTTP/1.1");
		pw.println("Host: gis.sofiyskavoda.bg");
		pw.println("");
		pw.flush();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
		
		String line;
		String nextLine = null;
		while ((line = br.readLine()) != null
				&& (line = br.readLine()) != "2000") {

			System.out.println(line);
//			if (line.length() == 0) {
//				
//				nextLine = br.readLine();
//				System.out.println("JSON content: " + nextLine);
//				
//			}

		}
		br.close();
		client.close();
		
//		JSONObject hop = new JSONObject(nextLine);
//		
//		JSONArray featuresArray = hop.getJSONArray("features");
//		System.out.println("feature array: " + featuresArray + "\n");
//		
//		JSONObject featureObject = featuresArray.getJSONObject(0);
//		System.out.println("feature obj: " + featureObject + "\n");
//		
//		JSONObject featureGeo = featureObject.getJSONObject("geometry");
//		System.out.println("feature geo: " + featureGeo + "\n");
//		
//		JSONArray rings = featureGeo.getJSONArray("rings");
//		System.out.println("rings: " + rings + "\n");
//		
//		JSONArray array = rings.getJSONArray(0);
//		System.out.println("array: " + array + "\n");

		
		System.out.println("End of program!");

		
	}

}
