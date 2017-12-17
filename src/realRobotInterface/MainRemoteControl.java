package realRobotInterface;

import javafx.application.Application;
import javafx.stage.Stage;
import qLearning.model.DiscretisedAction;
import realRobotInterface.JavaArduinoDialog.Hardware.SimpleRead;
import realRobotInterface.JavaArduinoDialog.Hardware.SimpleWrite;
import realRobotInterface.JavaArduinoDialog.graphic.GraphicWindow;

public class MainRemoteControl extends Application {

	String distance = "2";
	public static final SimpleRead readingThread = new SimpleRead();
	private GraphicWindow graphicWindow;

	public static void main(String[] args) {
		launch(args);
	}

	private void getDistance() {
		SimpleWrite askForSomething = new SimpleWrite(MainRemoteControl.readingThread, "CPTR",
				SimpleWrite.sendingMode.SENDING_WITH_RESPONSE);
		distance = askForSomething.getResponse();
	}

	@Override
	public void start(Stage stage) {
		graphicWindow = new GraphicWindow(stage, this);
	}
	
	public void runApp() {
		while (true) {
			//getDistance();
			//graphicWindow.updateDistance(distance);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void executeDiscretisedAction(DiscretisedAction action) {
		SimpleWrite sendOrder;
		switch ((DiscretisedAction.Actions) action.getValue()) {
		case TURN_RIGHT:
			sendOrder = new SimpleWrite(readingThread, "RGHT", SimpleWrite.sendingMode.SIMPLE_SENDING);
			break;
		case GO_FORWARD:
			sendOrder = new SimpleWrite(readingThread, "FRWD", SimpleWrite.sendingMode.SIMPLE_SENDING);
			break;
		case TURN_LEFT:
			sendOrder = new SimpleWrite(readingThread, "LEFT", SimpleWrite.sendingMode.SIMPLE_SENDING);
			break;
		default:
			sendOrder = new SimpleWrite(readingThread, "STOP", SimpleWrite.sendingMode.SIMPLE_SENDING);
			break;
		}
	}

}
