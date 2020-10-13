package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {
	
	private GPSPoint[] gpspoints;
	
	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}
	
	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	// beregn total distances (i meter)
	public double totalDistance() {

		double distance = 0;
		
		for (int i=0;i<gpspoints.length-1;i++) {
			
		double distancePoint = GPSUtils.distance(gpspoints[i], gpspoints[i+1]);
		
		distance += distancePoint;
		}
		return distance;

	}

	// beregn totale høydemeter (i meter)
	public double totalElevation() {

		double elevation = 0;
		
		for (int i=0; i<gpspoints.length-1; i++) {
			double elevationPoint = gpspoints[i+1].getElevation() - gpspoints[i].getElevation();
			if (elevationPoint > 0) {
			elevation += elevationPoint;
			} 	
		}
		return elevation;
	}

	// beregn total tiden for hele turen (i sekunder)
	public int totalTime() {

		int time = 0;
		
		for (int i = 0; i < gpspoints.length-1; i++) {
			int timePoint = gpspoints[i+1].getTime() - gpspoints[i].getTime();
			time += timePoint;
		}
		return time;
	}
		
	// beregn gjennomsnitshastighets mellom hver av gps punktene

	public double[] speeds() {
		
		double averageSpeed = 0;
		
		double[] speedTab = new double[gpspoints.length-1];
		
		for (int i = 0; i < gpspoints.length-1; i++) {
			averageSpeed = GPSUtils.speed(gpspoints[i], gpspoints[i+1]);
			
			speedTab[i] = averageSpeed;
		}
		return speedTab;
	}
	
	public double maxSpeed() {
		
		double maxspeed = 0;
		for (int i= 0; i<gpspoints.length; i++) {
			maxspeed = GPSUtils.findMax(speeds());
		}
		return maxspeed;
//		
//		double maxspeed = 0;
//		for (int i = 0; i < gpspoints.length-1; i++) {
//
//		maxspeed = GPSUtils.findMax(gpspoints.speed(gpspoints[i], gpspoints[i+1]));
//		}
	}

	public double averageSpeed() {

		double average = 0;
		
		average = totalDistance() / totalTime();
		return average*60*60/1000;
	}
		

	/*
	 * bicycling, <10 mph, leisure, to work or for pleasure 4.0 bicycling,
	 * general 8.0 bicycling, 10-11.9 mph, leisure, slow, light effort 6.0
	 * bicycling, 12-13.9 mph, leisure, moderate effort 8.0 bicycling, 14-15.9
	 * mph, racing or leisure, fast, vigorous effort 10.0 bicycling, 16-19 mph,
	 * racing/not drafting or >19 mph drafting, very fast, racing general 12.0
	 * bicycling, >20 mph, racing, not drafting 16.0
	 * <10 = 6,2 - 
	 * 10-11.9 = 6,21 - 7,38
	 * 12-13.9 = 7,39 - 8,62
	 * 14-15.9 = 8,63 - 9.56
	 * 16-19.9 = 9.57 - 12.34
	 * >20 = 12.35
	 */

	// conversion factor m/s to miles per hour
	public static double MS = 2.236936;

	// beregn kcal gitt weight og tid der kjøres med en gitt hastighet
	public double kcal(double weight, int secs, double speed) {

		double kcal;

		// MET: Metabolic equivalent of task angir (kcal x kg-1 x h-1)
		double met = 0;		
		double speedmph = speed * MS;
		
		double speedkmt = speedmph*0.62;
		
		if (speedkmt < 6.2) {
			met = 4.0;
			} else if 
			(speedkmt >= 6.21 && speedkmt < 7.38) {
				met = 6.0;
			} else if 
				(speedkmt >= 7.39 && speedkmt < 8.62) {
					met = 8.0;
			} else if 
				(speedkmt >= 8.63 && speedkmt < 9.56) {
					met = 10.0;
			} else if 
				(speedkmt >= 9.57 && speedkmt < 12.34) {
					met = 12.0;
			} else
				met = 16.0;
			
		kcal = weight * secs/3600.0 * met;
	
	return kcal;
}
	
	public double totalKcal(double weight) {

		
		double totalkcal = 0;
		
		totalkcal = kcal(weight, totalTime(), averageSpeed());
		
		return totalkcal;
//		double speed = 0;
//		
//		double[] speeds = this.speeds();
//		for (int i = 0; i<gpspoints.length-1; i++) {
//			if (i < speeds.length) {
//				speed = speeds[i];
//			}
//		
////			totalkcal += this.kcal(weight, gpspoints[i].getTime(), speed);
//			totalkcal += kcal(weight, gpspoints[i+1].getTime() - gpspoints[i].getTime() , GPSUtils.speed(gpspoints[i], gpspoints[i+1]));
//		}
//				return totalkcal;
	}
	
	private static double WEIGHT = 80.0;
	
	public void displayStatistics() {

		System.out.println("==============================================");
		System.out.println("Total time" + "	" + ":" + "	      " + GPSUtils.formatTime(totalTime()) );
		System.out.println("Total distance" + "	" + ":" + "	  " + GPSUtils.formatDouble(totalDistance()/1000) + " km" );
		System.out.println("Total elevation" + "	" + ":" + "	   " + GPSUtils.formatDouble(totalElevation()) + " m" );
		System.out.println("Max speed" + "	" + ":" + "	" + GPSUtils.formatDouble(maxSpeed()) + " km/t" );
		System.out.println("Average speed" + "	" + ":" + "	" + GPSUtils.formatDouble(averageSpeed()) + " km/t" );
		System.out.println("Energy" + "		" + ":" + "	" + GPSUtils.formatDouble(totalKcal(WEIGHT)) +" kcal");
		System.out.println("==============================================");

	}

}
