package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import java.time.LocalTime;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		
		return max;
	}

	public static double findMin(double[] da) {

		double min;
		
		min = da[0];
		
		for (double d : da) {
			if (d < min) {
				min = d;
			}
		}
		return min;
	}
	
	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		double[] latTab = new double[gpspoints.length];
		
		for (int i=0;i<gpspoints.length;i++) {
			double latitude = gpspoints[i].getLatitude();
		
			latTab[i] = latitude;
		}
		return latTab;
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

	double[] longTab = new double[gpspoints.length];
		
		for (int i=0;i<gpspoints.length;i++) {
			double latitude = gpspoints[i].getLongitude();
		
			longTab[i] = latitude;
		}
		return longTab;

	}

	private static int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double d;
		double latitude1, longitude1, latitude2, longitude2;
		latitude1 = gpspoint1.getLatitude();
		latitude2 = gpspoint2.getLatitude();
		longitude1 = gpspoint1.getLongitude();
		longitude2 = gpspoint2.getLongitude();
		
		double deltalat = toRadians(latitude2 - latitude1);
		double deltalong = toRadians(longitude2 - longitude1);
		
		double a = pow(sin(deltalat/2),2.0) + cos(toRadians(latitude1)) * cos (toRadians(latitude2)) * pow(sin(deltalong/2),2.0);
		double c = 2.0 * atan2(sqrt(a),sqrt(1-a));

		d = R * c;
		return d;

	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double secs;
		double speed;
		
		secs = gpspoint2.getTime() - gpspoint1.getTime();
		double distance = distance(gpspoint1, gpspoint2);
		
		speed = (distance/1000)/(secs/3600.0);
		return speed;
	}
	

	public static String formatTime(int secs) {

		return String.format("  " + "%02d:%02d:%02d", secs / 3600, (secs % 3600) / 60, secs % 60);
//		
//		while (String.format.length <=10) {
//			
//		}

//		String timestr;
//		String TIMESEP = ":";
//
//	LocalTime timeOfDay = LocalTime.ofSecondOfDay(secs);
//	String time = timeOfDay.toString();
//	while (time.length() <=10) {
//		time = " " + time;
//	}
//	return time;

	}
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

		String str;

		double roundOff = Math.round(d * 100.0) / 100.0;
		
		str = Double.toString(roundOff);
		while (str.length() <=TEXTWIDTH)
			str = " " + str;
		return str;

		
	}
}
