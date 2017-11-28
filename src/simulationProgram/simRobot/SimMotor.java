package simulationProgram.simRobot;

import commonInterface.Motor;

public class SimMotor implements Motor {

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
