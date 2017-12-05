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
		System.out.println("State : " + ((DiscretisedState) stateActionPair.getState()).getRadarStates());
		System.out.println("Action : " + stateActionPair.getAction());
		System.out.println("Reward : " + value);
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
			HashReward.put(stateActionPair, -20);
		} else {
			int reward = 0;
			for (DiscretisedState.RadarStates state : radarStates) {
				switch (state) {
				case S1:
					reward += 1;
					break;
				case S2:
					reward += 2;
					break;

				case S3:
					reward += 3;
					break;

				case S4:
					reward += 4;
					break;

				case S5:
					reward += 5;
					break;

				case S6:
					reward += 6;
					break;

				case S7:
					reward += 7;
					break;

				case S8:
					reward += 8;
					break;

				case S9:
					reward += 9;
					break;

				case S10:
					reward += 50;
					break;

				default:
					break;
				}
			}
			HashReward.put(stateActionPair, reward);
		}
	}

	public int getValue() {
		return value;
	}
}
