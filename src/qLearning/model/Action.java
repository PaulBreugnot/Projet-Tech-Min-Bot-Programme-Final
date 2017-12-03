package qLearning.model;

import java.util.ArrayList;

public interface Action {

	public static Object getRandomAction(ArrayList<Action> availableActions) {
		return availableActions.get((int) Math.floor(Math.random() * availableActions.size()));
	}
	
	Object getValue();
}
