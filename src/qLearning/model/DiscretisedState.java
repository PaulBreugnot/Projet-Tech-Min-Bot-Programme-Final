package qLearning.model;

import java.util.ArrayList;

public class DiscretisedState extends State {

	public enum RadarStates {
		S0, S1, S2, S3, S4, S5, S6, S7, S8, S9, S10
	}

	/*public static final double S0value = 0.10;
	public static final double S1value = 0.129;
	public static final double S2value = 0.216;
	public static final double S3value = 0.361;
	public static final double S4value = 0.564;
	public static final double S5value = 0.825;
	public static final double S6value = 1.144;
	public static final double S7value = 1.521;
	public static final double S8value = 1.956;
	public static final double S9value = 2.449;*/
	/*public static final double S0value = 0.10;
	public static final double S1value = 0.25;
	public static final double S2value = 0.40;
	public static final double S3value = 0.55;
	public static final double S4value = 0.70;
	public static final double S5value = 0.85;
	public static final double S6value = 1.10;
	public static final double S7value = 1.25;
	public static final double S8value = 1.40;
	public static final double S9value = 1.55;*/
	
	public static final double S0value = 0.10;
	public static final double S1value = 0.50;
	public static final double S2value = 0.90;
	public static final double S3value = 1.50;
	public static final double S4value = 2.00;
	public static final double S5value = 2.00;
	public static final double S6value = 2.00;
	public static final double S7value = 2.00;
	public static final double S8value = 2.00;
	public static final double S9value = 2.00;
	
	public RadarStates radar1;
	public RadarStates radar2;
	public RadarStates radar3;
	public RadarStates radar4;
	public RadarStates radar5;

	public DiscretisedState(RadarStates radar1, RadarStates radar2, RadarStates radar3, RadarStates radar4,
			RadarStates radar5) {
		this.radar1 = radar1;
		this.radar2 = radar2;
		this.radar3 = radar3;
		this.radar4 = radar4;
		this.radar5 = radar5;
	}

	public DiscretisedState(double d1, double d2, double d3, double d4, double d5) {
		radar1 = discretized1(d1);
		radar2 = discretized1(d2);
		radar3 = discretized1(d3);
		radar4 = discretized1(d4);
		radar5 = discretized1(d5);
	}

	public static RadarStates discretized1(double d) {
		if (0.0 <= d && d < S0value) {
			return RadarStates.S0;
		}

		else if (S0value <= d && d < S1value) {
			return RadarStates.S1;
		}

		else if (S1value <= d && d < S2value) {
			return RadarStates.S2;
		}

		else if (S2value <= d && d < S3value) {
			return RadarStates.S3;
		}

		else if (S3value <= d && d < S4value) {
			return RadarStates.S4;
		}

		else if (S4value <= d && d < S5value) {
			return RadarStates.S5;
		}

		else if (S5value <= d && d < S6value) {
			return RadarStates.S6;
		}

		else if (S6value <= d && d < S7value) {
			return RadarStates.S7;
		}

		else if (S7value <= d && d < S8value) {
			return RadarStates.S8;
		}

		else if (S8value <= d && d < S9value) {
			return RadarStates.S9;
		}

		else {
			return RadarStates.S10;
		}
	}

	public static RadarStates discretized2(double d) {
		if (0.0 <= d && d < 0.2) {
			return RadarStates.S0;
		}

		if (0.2 <= d && d < 0.5) {
			return RadarStates.S1;
		} else {
			return RadarStates.S2;
		}
	}

	public static double value(RadarStates radarStates) {
		switch (radarStates) {
		case S0:
			return S0value;
		case S1:
			return S1value;
		case S2:
			return S2value;
		case S3:
			return S3value;
		case S4:
			return S4value;
		case S5:
			return S5value;
		case S6:
			return S6value;
		case S7:
			return S7value;
		case S8:
			return S8value;
		case S9:
			return S9value;
		default:
			return Double.POSITIVE_INFINITY;
		}
	}

	public ArrayList<RadarStates> getRadarStates() {
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

	@Override
	public String toString() {
		return "DiscretisedState [radar1=" + radar1 + ", radar2=" + radar2 + ", radar3=" + radar3 + ", radar4=" + radar4
				+ ", radar5=" + radar5 + "]";
	}

}
