package realRobotInterface.realRobot;

import commonInterface.Captor;

public class RealCaptor implements Captor{
	
	private double distance;

	@Override
	public double getDistance() {
		return distance;
	}

	@Override
	public void setDistance(double distance) {
		this.distance = distance;
	}

	@Override
	public double getCaptorOrientation() {
		// TODO Auto-generated method stub
		return 0;
	}

}
