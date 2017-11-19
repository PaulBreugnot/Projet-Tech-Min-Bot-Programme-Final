package simulationProgram.simRobot;

import commonInterface.Motor;

public class SimMotor implements Motor {

	private int speed; // An int between 0 and 255

	@Override
	public int getSpeed() {
		return speed;
	}

	@Override
	public void setSpeed(int speed) {
		this.speed = speed;
	}

}
