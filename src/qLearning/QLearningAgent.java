package qLearning;

import java.util.ArrayList;
import java.util.Hashtable;

import qLearning.model.Action;
import qLearning.model.Reward;
import qLearning.model.State;
import qLearning.model.StateActionPair;

public class QLearningAgent {
	final static double alpha = 0.5;
	final static double gamma = 0.5;
	static double epsilon = 0.99;

	private State currentState;
	private Action lastAction;
	private Reward lastReward;

	private Action nextAction;

	static ArrayList<Action> availableActions = new ArrayList<>();
	static Hashtable<StateActionPair, Double> QLearningTable = new Hashtable<>();

	public QLearningAgent(State currentState, Action lastAction, Reward lastReward) {
		this.currentState = currentState;
		this.lastAction = lastAction;
		this.lastReward = lastReward;
	}

	public void setCurrentState(State state) {
		currentState = state; 
	}
	
	public void setLastAction(Action action) {
		lastAction = action;
	}
	
	public void setLastReward(Reward reward) {
		lastReward = reward;
	}
	
	public Action getAction() {
		StateActionPair stateActionPair = new StateActionPair(currentState, lastAction);

		LearnFrom(stateActionPair, lastReward);
		
		return nextAction;
	}

	private void LearnFrom(StateActionPair stateActionPair, Reward reward) {

		if (QLearningTable.get(stateActionPair) == null) {
			QLearningTable.put(stateActionPair, 0.0);
		}
		// Reinforcement
		double newQValue = QLearningTable.get(stateActionPair) + alpha * (reward.getValue()
				+ gamma * maxQValue(stateActionPair.getState()) - QLearningTable.get(stateActionPair));
		QLearningTable.put(stateActionPair, newQValue);

		double p = Math.random();
		if(p >= epsilon) {
			//Exploration
			nextAction = (Action) stateActionPair.getAction().getRandomAction();
		}
	}

	private double maxQValue(State state) {
		double maxQValue = Double.MIN_VALUE;
		for (StateActionPair stateActionPair : QLearningTable.keySet()) {
			if (stateActionPair.getState().equals(state)) {
				if (QLearningTable.get(stateActionPair) > maxQValue) {
					maxQValue = QLearningTable.get(stateActionPair);
					//Exploitation
					nextAction = stateActionPair.getAction();
				}
			}
		}
		return maxQValue;
	}

	public void setAvailableActions(ArrayList<Action> availableActions) {
		QLearningAgent.availableActions = availableActions;
	}
}
