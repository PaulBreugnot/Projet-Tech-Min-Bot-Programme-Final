package realRobotInterface.JavaArduinoDialog.Data;

import java.util.ArrayList;

public class CaptorsData {
	private ArrayList<Integer> captorsDistances = new ArrayList<>(5);
	private String receivedString;

	public CaptorsData(String receivedString) {
		setReceivedString(receivedString);
		buildDistanceArray();
	}
	
	public void setReceivedString(String receivedString){
		this.receivedString = receivedString;
	}
	
	private void buildDistanceArray() {
		String[] splitString = receivedString.split("-");
		for (int i = 0; i<5; i++) {
			captorsDistances.add(Integer.parseInt(splitString[i]));
		}
	}
	
	public ArrayList<Integer> getCaptorsDistance() {
		return captorsDistances;
	}
}
