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
			ArrayList<DiscretisedState.RadarStates> radarStates = ((DiscretisedState) stateActionPair.getState())
					.getRadarStates();
			Action A = stateActionPair.getAction();
			for (DiscretisedState.RadarStates state : radarStates) {
				if (state == DiscretisedState.RadarStates.S0) {
					HashReward.put(stateActionPair, -1);
				} else if (A.getValue() == DiscretisedAction.Actions.GO_FORWARD) {
					HashReward.put(stateActionPair, 1);
				} else {
					HashReward.put(stateActionPair, 0);
				}
			}
		}
		value = HashReward.get(stateActionPair);
	}

	public int getValue() {
		return value;
	}
}
