package commonInterface;

public interface Robot {

	boolean tooCloseFromAnObstacle();
	double getCollisionTreshold();
	
	void setRobotMotors(); //Set a list of implemented interface Motor
	void setRobotCaptors(); //Set a list of implemented interface Motor
}
