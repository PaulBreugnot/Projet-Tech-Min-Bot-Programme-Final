package qLearning.model;

import java.util.ArrayList;

public class DiscretisedState extends State {

	public enum RadarStates {S0, S1, S2, S3, S4, S5, S6, S7, S8, S9, S10}
	public RadarStates radar1;
	public RadarStates radar2;
	public RadarStates radar3;
	public RadarStates radar4;
	public RadarStates radar5;

	
	public DiscretisedState (RadarStates radar1, RadarStates radar2, RadarStates radar3, RadarStates radar4, RadarStates radar5) {
		this.radar1 = radar1;
		this.radar2 = radar2;
		this.radar3 = radar3;
		this.radar4 = radar4;
		this.radar5 = radar5;
	}
	
	public DiscretisedState (double d1, double d2, double d3, double d4, double d5) {
		radar1 = discretized(d1);
		radar2 = discretized(d2);
		radar3 = discretized(d3);
		radar4 = discretized(d4);
		radar5 = discretized(d5);
	}
	
	public RadarStates discretized(double d) {
		if (0 <= d && d < 5) {
			return RadarStates.S0;
		}
		
		if (5 <= d && d < 10) {
			return RadarStates.S1;
		}
		
		if (10 <= d && d < 15) {
			return RadarStates.S2;
		}
		
		if (15 <= d && d < 20) {
			return RadarStates.S3;
		}
		
		if (20 <= d && d < 25) {
			return RadarStates.S4;
		}
		
		if (25 <= d && d < 30) {
			return RadarStates.S5;
		}
		
		if (30 <= d && d < 35) {
			return RadarStates.S6;
		}
		
		if (35 <= d && d < 40) {
			return RadarStates.S7;
		}
		
		if (40 <= d && d < 45) {
			return RadarStates.S8;
		}
		
		if (45 <= d && d < 50) {
			return RadarStates.S9;
		}
		
		else {
			return RadarStates.S10;
		}
	}
	
	public ArrayList<RadarStates> getRadarStates(){
		ArrayList<RadarStates> states = new ArrayList<>();
		states.add(radar1);
		states.add(radar2);
		states.add(radar3);
		states.add(radar4);
		states.add(radar5);
		return states;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((radar1 == null) ? 0 : radar1.hashCode());
		result = prime * result + ((radar2 == null) ? 0 : radar2.hashCode());
		result = prime * result + ((radar3 == null) ? 0 : radar3.hashCode());
		result = prime * result + ((radar4 == null) ? 0 : radar4.hashCode());
		result = prime * result + ((radar5 == null) ? 0 : radar5.hashCode());
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
		DiscretisedState other = (DiscretisedState) obj;
		if (radar1 != other.radar1)
			return false;
		if (radar2 != other.radar2)
			return false;
		if (radar3 != other.radar3)
			return false;
		if (radar4 != other.radar4)
			return false;
		if (radar5 != other.radar5)
			return false;
		return true;
	}
	
	
}
