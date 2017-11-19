package simulationProgram.simMap;

public class Map {
	private double width;
	private double height;

	public Map(double width, double height) {
		this.width = width;
		this.height = height;

	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getWidth() {
		return width;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getHeight() {
		return height;
	}
}
