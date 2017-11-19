package simulationProgram.simRobot;

import commonInterface.AbstractRobot;
import commonInterface.Robot;

public class SimRobot extends AbstractRobot implements Robot{
	
	public double xPos;
	public double yPos;
	public double alphaOrientation;
	public double wheelRadius = 0.1;
	public static double robotSize = 0.2;
	
	public SimRobot(double xPos, double yPos, double alphaOrientation) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.alphaOrientation = alphaOrientation;
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
		for (int cptrID = 0; cptrID<5; cptrID++) {
			robotCaptors.add(new SimCaptor());
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

}
