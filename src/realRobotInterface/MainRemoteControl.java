package realRobotInterface;

import javafx.application.Application;
import javafx.stage.Stage;
import qLearning.model.DiscretisedAction;
import realRobotInterface.JavaArduinoDialog.Hardware.SimpleRead;
import realRobotInterface.JavaArduinoDialog.Hardware.SimpleWrite;
import realRobotInterface.JavaArduinoDialog.graphic.GraphicWindow;
import simulationProgram.util.Move;

public class MainRemoteControl extends Application {
	private static final SimpleRead readingThread = new SimpleRead();
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		GraphicWindow graphicWindow = new GraphicWindow(stage);
	}
	
	private void executeDiscretisedAction(DiscretisedAction action) {
		switch ((DiscretisedAction.Actions) action.getValue()) {
		case GO_FORWARD:
			SimpleWrite sendSomething = new SimpleWrite(readingThread, "FRWD", SimpleWrite.sendingMode.SIMPLE_SENDING);
			break;
		case TURN_RIGHT:
			break;
		case TURN_LEFT:
			break;
		}
		/*for (int i = 0; i < 50; i++) {
			Move.move(titi);
			radar.updateCaptorDistances();
			qLearningAgent.setCurrentState(getCurrentState());
			qLearningAgent.setAvailableActions(getAvailableActions(getCurrentState()));
			graphicWindow.updateDataLabels();
			graphicWindow.updateGraphicItems();
		}*/
		// radar.updateCaptorDistances();
	}

}
