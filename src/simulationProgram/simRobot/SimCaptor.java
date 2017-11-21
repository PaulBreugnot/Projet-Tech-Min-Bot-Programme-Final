package simulationProgram.simRobot;

import commonInterface.Captor;

public class SimCaptor implements Captor{

	private double distance; // Measured distance in meter
	
	@Override
	public double getDistance() {
		return distance;
	}
	
	@Override
	public void setDistance(double distance) {
		this.distance = distance;
	}

}
