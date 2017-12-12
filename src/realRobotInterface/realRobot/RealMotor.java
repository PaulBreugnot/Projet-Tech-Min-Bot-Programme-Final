package realRobotInterface.realRobot;

import commonInterface.Motor;

public class RealMotor implements Motor {
	
	private double speed; // In rad/s

	@Override
	public double getSpeed() {
		return speed;
	}

	@Override
	public void setSpeed(double speed) {
		this.speed = speed;
	}
}
