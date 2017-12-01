package qLearning.model;

public class ContinuousState extends State {
	private double D1;
	private double D2;
	private double D3;
	private double D4;
	private double D5;
	
	public ContinuousState(double D1, double D2, double D3, double D4, double D5) {
		this.D1 = D1;
		this.D2 = D2;
		this.D3 = D3;
		this.D4 = D4;
		this.D5 = D5;
	}

	public double getD1() {
		return D1;
	}
	
	public double getD2() {
		return D2;
	}
	
	public double getD3() {
		return D3;
	}
	
	public double getD4() {
		return D4;
	}
	
	public double getD5() {
		return D5;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(D1);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(D2);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(D3);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(D4);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(D5);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		ContinuousState other = (ContinuousState) obj;
		if (Double.doubleToLongBits(D1) != Double.doubleToLongBits(other.D1))
			return false;
		if (Double.doubleToLongBits(D2) != Double.doubleToLongBits(other.D2))
			return false;
		if (Double.doubleToLongBits(D3) != Double.doubleToLongBits(other.D3))
			return false;
		if (Double.doubleToLongBits(D4) != Double.doubleToLongBits(other.D4))
			return false;
		if (Double.doubleToLongBits(D5) != Double.doubleToLongBits(other.D5))
			return false;
		return true;
	}
}
