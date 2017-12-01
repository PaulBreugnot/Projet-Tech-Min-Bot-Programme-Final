package simulationProgram.util;

import java.util.ArrayList;

import simulationProgram.simRobot.SimRobot;

public abstract class Move {
	public static void move(SimRobot titi) {
		// angles en rad ou rad/s
		move(titi, 10);
	}

	// 2 constructeur si on veut presiser le pas de temps
	public static void move(SimRobot titi, int delta) {
		System.out.println("V1 = " + titi.getV1());
		System.out.println("V2 = " + titi.getV2());

		double DeltaAlpha = titi.getWheelRadius() / (2 * titi.getRobotSize()) * (titi.getV1() - titi.getV2()) * delta
				/ 1000;
		double RobotSpeed = (titi.getWheelRadius() / 2) * (titi.getV1() + titi.getV2());

		System.out.println("Delta X = "
				+ RobotSpeed * Math.cos(titi.getAlphaOrientation() * Math.PI / 180 + DeltaAlpha / 2) * delta / 1000);
		System.out.println("Delta Y = "
				+ RobotSpeed * Math.sin(titi.getAlphaOrientation() * Math.PI / 180 + DeltaAlpha / 2) * delta / 1000);
		System.out.println("Delta Alpha = " + DeltaAlpha * 180 / Math.PI);

		titi.setXPos(titi.getXPos()
				+ RobotSpeed * Math.cos(titi.getAlphaOrientation() * Math.PI / 180 + DeltaAlpha / 2) * delta / 1000);

		titi.setYPos(titi.getYPos()
				+ RobotSpeed * Math.sin(titi.getAlphaOrientation() * Math.PI / 180 + DeltaAlpha / 2) * delta / 1000);

		titi.setAlphaOrientation(titi.getAlphaOrientation() + DeltaAlpha * 180 / Math.PI);

	}

	public static ArrayList<Double[]> moveTest() {
		ArrayList<Double[]> sequence = new ArrayList<>();
		Double[] initCoordinates = { 0.25, 0.25, 0.0 };
		sequence.add(initCoordinates);
		while (sequence.get(sequence.size() - 1)[2] < 45) {
			Double[] nextCoordinates = { 0.25, 0.25, sequence.get(sequence.size() - 1)[2] + 1 };
			sequence.add(nextCoordinates);
		}

		while (sequence.get(sequence.size() - 1)[0] < 2.5 && sequence.get(sequence.size() - 1)[1] < 2.5) {
			Double[] nextCoordinates = { sequence.get(sequence.size() - 1)[0] + 0.1,
					sequence.get(sequence.size() - 1)[1] + 0.1, 45.0 };
			sequence.add(nextCoordinates);
		}

		Double[] initRotation = { 2.5, 2.5, 45.0 };
		sequence.add(initRotation);
		for (int i = 0; i < 360; i++) {
			Double[] nextCoordinates = { sequence.get(sequence.size() - 1)[0], sequence.get(sequence.size() - 1)[1],
					sequence.get(sequence.size() - 1)[2] + 1 };
			sequence.add(nextCoordinates);
		}
		return sequence;
	}
}
