package commonInterface;

import java.util.ArrayList;

public interface Robot {
	
	void setRobotMotors(); //Set a list of implemented interface Motor
	void setRobotCaptors(); //Set a list of implemented interface Motor

	ArrayList<Captor> getRobotCaptors();
}
