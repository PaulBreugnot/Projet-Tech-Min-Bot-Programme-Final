package qLearning.model;

public class Action {
	private Actions action;

	public enum Actions {
		SPEED_UP_RIGHT, SPEED_UP_LEFT, SLOW_DOWN_RIGHT, SLOW_DOWN_LEFT
	}

	public Action(Actions action) {
		this.action = action;
	}

	public static Action getRandomAction() {
		int n = (int) Math.ceil(Math.random() * 4);
		switch (n) {
		case 0:
			return new Action(Actions.SPEED_UP_RIGHT);
		case 1:
			return new Action(Actions.SPEED_UP_LEFT);
		case 2:
			return new Action(Actions.SLOW_DOWN_RIGHT);
		case 3:
			return new Action(Actions.SLOW_DOWN_LEFT);
		default:
			return null;
		}
	}
	
	public Actions getAction() {
		return action;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Action other = (Action) obj;
		if (action != other.action)
			return false;
		return true;
	}
	
	
}
