package realRobotInterface.realRobot;

import commonInterface.AbstractRobot;
import commonInterface.Robot;
import simulationProgram.simRobot.SimCaptor;
import simulationProgram.simRobot.SimMotor;

public class RealRobot extends AbstractRobot implements Robot {

	public RealRobot() {
		setRobotMotors();
		setRobotCaptors();
	}

	@Override
	public void setRobotMotors() {
		robotMotors.add(new RealMotor());
		robotMotors.add(new RealMotor());
	}

	@Override
	public void setRobotCaptors() {
		for (int cptrID = 0; cptrID < 5; cptrID++) {
			robotCaptors.add(new RealCaptor());
		}

	}
}
