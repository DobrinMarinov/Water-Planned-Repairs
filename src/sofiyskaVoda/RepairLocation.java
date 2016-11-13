package sofiyskaVoda;

import java.util.ArrayList;
import java.util.List;

public class RepairLocation {

	private List<RepairPoint> points = new ArrayList<>();
	
	public void addPoint(double easting, double northing) {
		double rMajor = 6378137;
		double shift = Math.PI * rMajor;
		double lon = easting / shift * 180.0;
		double lat = northing / shift * 180.0;
		lat = 180 / Math.PI * (2 * Math.atan(Math.exp(lat * Math.PI / 180.0)) - Math.PI / 2.0);
//		System.out.println(lat + " | " + lon);
		
		RepairPoint repairPoint = new RepairPoint(lat, lon);
		points.add(repairPoint);
	}
	
	public void print() {
		System.out.println("Planned Repair Location: ");
		for (RepairPoint repairPoint : points) {
			System.out.println("latitude: " + repairPoint.getLat() + " | longitude: " + repairPoint.getLon());
		}
	}
	
	
}
