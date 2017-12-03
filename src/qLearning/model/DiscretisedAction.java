package qLearning.model;

public class DiscretisedAction implements Action{
	private Actions action;
	
	public enum Actions{GO_FORWARD, TURN_RIGHT, TURN_LEFT}
	
	public DiscretisedAction() {}
	
	public DiscretisedAction(Actions action) {
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
		DiscretisedAction other = (DiscretisedAction) obj;
		if (action != other.action)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[action=" + action + "]";
	}
	
	
	
	
}
