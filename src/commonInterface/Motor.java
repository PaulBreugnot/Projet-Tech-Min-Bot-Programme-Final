package commonInterface;

//This interface needs to be implemented by the Simulation program and the real robot control program.
public interface Motor {

	double getSpeed(); //In rad/s
	void setSpeed(double speed);
	
}
