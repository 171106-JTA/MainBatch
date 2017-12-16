package p1.revature.beans;

public class State {
	private String stateAbbreviation, stateName;
	
	public State() {
		super();
	}

	public State(String stateAbbreviation) {
		super();
		this.stateAbbreviation = stateAbbreviation;
	}

	public State(String stateAbbreviation, String stateName) {
		super();
		this.stateAbbreviation = stateAbbreviation;
		this.stateName = stateName;
	}

	public String getStateAbbreviation() {
		return stateAbbreviation;
	}

	public void setStateAbbreviation(String stateAbbreviation) {
		this.stateAbbreviation = stateAbbreviation;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
	
	
}
