package simulationProgram.simRobot;

import commonInterface.AbstractRobot;
import commonInterface.Robot;

public class SimRobot extends AbstractRobot implements Robot{
	
	public SimRobot() {
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

}
