package qLearning.model;

public class ContinuousAction implements Action{
	private Actions action;

	public enum Actions {
		SPEED_UP_RIGHT, SPEED_UP_LEFT, SLOW_DOWN_RIGHT, SLOW_DOWN_LEFT
	}

	public ContinuousAction(Actions action) {
		this.action = action;
	}
	
	@Override
	public Object getValue() {
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
		ContinuousAction other = (ContinuousAction) obj;
		if (action != other.action)
			return false;
		return true;
	}
	
	
}
