package simulationProgram.util;

public abstract class Round {
	public static double RoundDouble(double dble, int decLength) {
		return (double) (Math.round((long) Math.pow(10, decLength) * dble))/Math.pow(10, decLength);
	}
}
