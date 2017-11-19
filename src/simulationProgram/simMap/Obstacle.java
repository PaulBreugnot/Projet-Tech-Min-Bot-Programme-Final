package simulationProgram.simMap;

public class Obstacle {
	
	//Circular Obstacles
	public double xPos;
	public double yPos;
	public double radius;
	

	public  Obstacle (double xPos, double yPos, double radius) {
			this.xPos = xPos;
			this.yPos = yPos;
			this.radius=radius;
	}

	public double getXPos() {
		return xPos;
		}
	
	public double getYPos() {
		return yPos;
		}
	
	public double getRadius() {
		return radius;
		}
}
