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
			boolean obstacle = false;
			for (DiscretisedState.RadarStates state : radarStates) {
				if (state == DiscretisedState.RadarStates.S0) {
					obstacle = true;
				}
			}
			if (obstacle) {
				HashReward.put(stateActionPair, -1);
			} else if (A.getValue() == DiscretisedAction.Actions.GO_FORWARD) {
				HashReward.put(stateActionPair, 1);
			} else {
				HashReward.put(stateActionPair, 0);
			}
		}
		value = HashReward.get(stateActionPair);
		System.out.println("State : " + ((DiscretisedState) stateActionPair.getState()).getRadarStates());
		System.out.println("Action : " + stateActionPair.getAction());
		System.out.println("Reward : " + value);
	}

	public int getValue() {
		return value;
	}
}
