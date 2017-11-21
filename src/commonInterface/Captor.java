package commonInterface;

//This interface needs to be implemented by the Simulation program and the real robot control program.
public interface Captor {

	double getDistance(); // Return captor distance measurement, in meter

	void setDistance(double distance);
}
