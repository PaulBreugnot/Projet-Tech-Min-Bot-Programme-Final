package simulationProgram.util;

import java.util.ArrayList;

import simulationProgram.simRobot.SimRobot;

public abstract class Move {
	public Move(SimRobot titi) {
		// angles en rad ou rad/s
		this(titi, 10);
	}

	// 2 constructeur si on veut presiser le pas de temps
	public Move(SimRobot titi, int delta) {
		// angles en rad ou rad/s
		titi.setAlphaOrientation(titi.getAlphaOrientation()
				+ titi.getWheelRadius() / 2 / titi.getRobotSize() * (titi.getV1() + titi.getV2()));

		titi.setXPos(titi.getXPos() + titi.getWheelRadius() / 2 * (titi.getV1() + titi.getV2())
				* Math.cos(titi.getAlphaOrientation()) * delta / 1000);// angle de départ, possibilité de limiter les
																		// erreurs en prenant une moyenne ou en
																		// rajoutant des pas

		titi.setYPos(titi.getYPos() + titi.getWheelRadius() / 2 * (titi.getV1() + titi.getV2())
				* Math.sin(titi.getAlphaOrientation()) * delta / 1000);

	}

	public static ArrayList<Double[]> moveTest() {
		ArrayList<Double[]> sequence = new ArrayList<>();
		Double[] initCoordinates = { 0.25, 0.25, 0.0 };
		sequence.add(initCoordinates);
		while (sequence.get(sequence.size() - 1)[2] < 45) {
			Double[] nextCoordinates = { 0.25, 0.25, sequence.get(sequence.size() - 1)[2] + 1 };
			sequence.add(nextCoordinates);
		}

		/*while (sequence.get(sequence.size() - 1)[0] < 2.5 && sequence.get(sequence.size() - 1)[1] < 2.5) {
			Double[] nextCoordinates = { sequence.get(sequence.size() - 1)[0] + 0.1,
					sequence.get(sequence.size() - 1)[1] + 0.1, 45.0 };
			sequence.add(nextCoordinates);
		}

		Double[] initRotation = { sequence.get(sequence.size() - 1)[0], sequence.get(sequence.size() - 1)[1],
				sequence.get(sequence.size() - 1)[2] + 1 };
		sequence.add(initRotation);
		for (int i = 0; i < 360; i++) {
			Double[] nextCoordinates = { sequence.get(sequence.size() - 1)[0], sequence.get(sequence.size() - 1)[1],
					sequence.get(sequence.size() - 1)[2] + 1 };
			sequence.add(nextCoordinates);
		}*/
		return sequence;
	}
}
