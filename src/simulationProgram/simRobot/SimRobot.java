package simulationProgram.simRobot;

import commonInterface.AbstractRobot;
import commonInterface.Robot;

public class SimRobot extends AbstractRobot implements Robot {

	private double xPos;
	private double yPos;
	private double alphaOrientation;
	private double wheelRadius = 0.069/2;
	private double robotSize = 0.2; // Radius of the robot modeled by a circle

	public SimRobot(double initXPos, double initYPos, double initAlphaOrientation) {
		xPos = initXPos;
		yPos = initYPos;
		alphaOrientation = initAlphaOrientation;
		setRobotMotors();
		setRobotCaptors();
	}

	@Override
	public void setRobotMotors() {
		robotMotors.add(new SimMotor());
		robotMotors.add(new SimMotor());
	}

	@Override
	public void setRobotCaptors() {
		for (int cptrID = 0; cptrID < 5; cptrID++) {
			robotCaptors.add(new SimCaptor(-80 + 40 * cptrID));
		}

	}

	public double getXPos() {
		return xPos;
	}

	public double getYPos() {
		return yPos;
	}

	public double getAlphaOrientation() {
		return alphaOrientation;
	}

	public void setXPos(double xPos) {
		this.xPos = xPos;
	}

	public void setYPos(double yPos) {
		this.yPos = yPos;
	}

	public void setAlphaOrientation(double alphaOrientation) {
		while (alphaOrientation < 0) {
			alphaOrientation = alphaOrientation + 360;
		}

		while (alphaOrientation > 360) {
			alphaOrientation = alphaOrientation - 360;
		}
		this.alphaOrientation = alphaOrientation;
	}

	public double getWheelRadius() {
		return wheelRadius;
	}

	public double getRobotSize() {
		return robotSize;
	}

}
