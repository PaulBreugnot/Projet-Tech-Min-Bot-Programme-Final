package commonInterface;

//This interface needs to be implemented by the Simulation program and the real robot control program.
public interface Motor {

	int getSpeed(); //An integer between 0 and 255
	void setSpeed();
	
}
