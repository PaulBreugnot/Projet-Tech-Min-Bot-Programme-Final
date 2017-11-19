package commonInterface;

import java.util.ArrayList;

public abstract class AbstractRobot {

	private double collisionTreshold = 0.2; // In meter

	protected ArrayList<Motor> robotMotors = new ArrayList<>();
	protected ArrayList<Captor> robotCaptors = new ArrayList<>();

	public ArrayList<Motor> getRobotMotors() {
		return robotMotors;
	}

	public ArrayList<Captor> getRobotCaptors() {
		return robotCaptors;
	}

	double getCollisionTreshold() {
		return collisionTreshold;
	}
	
	public boolean isTooCloseFromAnObstacle() {
		for (Captor captor : robotCaptors) {
			if(captor.getDistance() < collisionTreshold) {
				return true;
			}
		}
		return false;
	}
}
