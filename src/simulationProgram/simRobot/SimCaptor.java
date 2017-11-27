package simulationProgram.simRobot;

import commonInterface.Captor;

public class SimCaptor implements Captor{

	private double distance = 0.2; // Measured distance in meter
	private double captorOrientation; //captor orientation in robot's mark
	
	public SimCaptor(double captorOrientation) {
		this.captorOrientation = captorOrientation;
	}
	
	@Override
	public double getDistance() {
		return distance;
	}
	
	@Override
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public double getCaptorOrientation() {
		return captorOrientation;
	}
	
	public void setCaptorOrientation(double captorOrientation) {
		this.captorOrientation = captorOrientation;
	}

}
