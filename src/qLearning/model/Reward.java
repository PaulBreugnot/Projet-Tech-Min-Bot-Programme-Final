package qLearning.model;

import java.util.ArrayList;
import java.util.Hashtable;

public class Reward {
	private int value;
	private static Hashtable<StateActionPair, Integer> HashReward = new Hashtable<>();

	public Reward(int value) {
		this.value = value;
	}

	public Reward(StateActionPair stateActionPair) {
		if (HashReward.get(stateActionPair) == null) {
			setNewReward(stateActionPair);
		}
		value = HashReward.get(stateActionPair);
	}

	private void setNewReward(StateActionPair stateActionPair) {
		ArrayList<DiscretisedState.RadarStates> radarStates = ((DiscretisedState) stateActionPair.getState())
				.getRadarStates();
		Action A = stateActionPair.getAction();
		boolean obstacle = false;
		for (DiscretisedState.RadarStates state : radarStates) {
			if (state == DiscretisedState.RadarStates.S0) {
				obstacle = true;
			}
		}
		if (obstacle) {
			HashReward.put(stateActionPair, -1);
		} else {
			/*int reward = 0;
			for (DiscretisedState.RadarStates state : radarStates) {
				switch (state) {
				case S1:
					reward += 5;
					break;
				case S2:
					reward += 10;
					break;

				case S3:
					reward += 15;
					break;

				case S4:
					reward += 20;
					break;

				case S5:
					reward += 30;
					break;

				case S6:
					reward += 40;
					break;

				case S7:
					reward += 55;
					break;

				case S8:
					reward += 60;
					break;

				case S9:
					reward += 80;
					break;

				case S10:
					reward += 100;
					break;

				default:
					break;
				}
			}*/
			//HashReward.put(stateActionPair, reward);
			HashReward.put(stateActionPair, 1);
		}
	}

	public int getValue() {
		return value;
	}
}
