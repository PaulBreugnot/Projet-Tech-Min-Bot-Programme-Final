package simulationProgram;

import java.util.ArrayList;

import commonInterface.Captor;
import simulationProgram.simMap.Map;
import simulationProgram.simMap.Obstacle;
import simulationProgram.simRobot.SimRobot;
import simulationProgram.util.Move;
import simulationProgram.util.Radar;
import javafx.application.Application;
import javafx.stage.Stage;
import qLearning.QLearningAgent;
import qLearning.model.Action;
import qLearning.model.Reward;
import qLearning.model.DiscretisedAction;
import qLearning.model.DiscretisedState;
import simulationProgram.graph.GraphicWindow;

public class MainSimulationProgram extends Application {

	SimRobot titi = new SimRobot(2.5, 2.5, 180);
	ArrayList<Obstacle> obstaclesList = new ArrayList<>();
	Map map = new Map(5, 5);
	Radar radar;
	GraphicWindow graphicWindow;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		System.out.println("Start method inside Thread : " + Thread.currentThread().getName());
		/*obstaclesList.add(new Obstacle(4, 1.5, 0.5));
		obstaclesList.add(new Obstacle(4, 4, 0.7));
		obstaclesList.add(new Obstacle(0.5, 3, 0.4));*/
		obstaclesList.add(new Obstacle(0.1, 0.1, 0.1));
		obstaclesList.add(new Obstacle(4.9, 0.1, 0.1));
		obstaclesList.add(new Obstacle(0.1, 4.9, 0.1));
		obstaclesList.add(new Obstacle(4.9, 4.9, 0.1));
		radar = new Radar(obstaclesList, titi, map);
		radar.updateCaptorDistances();
		Reward.setRobot(titi);
		graphicWindow = new GraphicWindow(stage, this);
	}
	
	public void runSimulation() {
		
		DiscretisedState initState = getCurrentState();
		QLearningAgent qLearningAgent = new QLearningAgent(initState, new DiscretisedAction(DiscretisedAction.Actions.GO_FORWARD));
		qLearningAgent.setAvailableActions(getAvailableActions(initState));
		while (true) {
			Action nextAction = qLearningAgent.getAction();
			if (nextAction == null) {
				System.out.println("Fail! Restart simulation.");
				//titi = new SimRobot(2.5, 2.5, 180);
				titi = new SimRobot(2.5, 2.5, 180);
				Reward.setRobot(titi);
				radar = new Radar(obstaclesList, titi, map);
				initState = getCurrentState();
				qLearningAgent = new QLearningAgent(initState, new DiscretisedAction(DiscretisedAction.Actions.GO_FORWARD));
				//QLearningAgent.refreshEpsilon(0.1);
				qLearningAgent.setAvailableActions(getAvailableActions(getCurrentState()));
				radar.updateCaptorDistances();
			}
			else {
				executeDiscretisedAction((DiscretisedAction) nextAction);
				qLearningAgent.setCurrentState(getCurrentState());
				qLearningAgent.setAvailableActions(getAvailableActions(getCurrentState()));
				qLearningAgent.setLastAction(nextAction);
			}
			graphicWindow.updateDataLabels();
			graphicWindow.updateGraphicItems();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//simpleTest();
	}
	
	private void executeDiscretisedAction(DiscretisedAction action) {
		switch ((DiscretisedAction.Actions) action.getValue()) {
		case GO_FORWARD:
			titi.getRobotMotors().get(0).setSpeed(5.24);
			titi.getRobotMotors().get(1).setSpeed(5.24);
			break;
		case TURN_RIGHT:
			titi.getRobotMotors().get(0).setSpeed(-2);
			titi.getRobotMotors().get(1).setSpeed(5.24);
			break;
		case TURN_LEFT:
			titi.getRobotMotors().get(0).setSpeed(5.24);
			titi.getRobotMotors().get(1).setSpeed(-2);
			break;
		}
		Move.move(titi);
		radar.updateCaptorDistances();
	}
	
	private DiscretisedState getCurrentState() {
		return new DiscretisedState(titi.getRobotCaptors().get(0).getDistance(),
				titi.getRobotCaptors().get(1).getDistance(), titi.getRobotCaptors().get(2).getDistance(),
				titi.getRobotCaptors().get(3).getDistance(), titi.getRobotCaptors().get(4).getDistance());
	}
	
	private void simpleTest() {
		boolean obstacleEncountered = false;
		titi.getRobotMotors().get(0).setSpeed(5.24);
		titi.getRobotMotors().get(1).setSpeed(5.24);
		while(!obstacleEncountered) {
			Move.move(getRobot());
			for (Captor captor : getRobot().getRobotCaptors()) {
				if (captor.getDistance() < 0.01) {
					obstacleEncountered = true;
				}
			}
			getRadar().updateCaptorDistances();
			graphicWindow.updateDataLabels();
			graphicWindow.updateGraphicItems();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private ArrayList<Action> getAvailableActions(DiscretisedState discretisedState) {
		ArrayList<Action> availableActions = new ArrayList<>();
		boolean allCaptorInfinite = true;
		for(DiscretisedState.RadarStates radarState : discretisedState.getRadarStates()) {
			if (radarState == DiscretisedState.RadarStates.S0) {
				return new ArrayList<Action>();
			}
			if (radarState != DiscretisedState.RadarStates.S10) {
				allCaptorInfinite = false;
			}
		}
		if (allCaptorInfinite) {
			availableActions.add(new DiscretisedAction(DiscretisedAction.Actions.GO_FORWARD));
		}
		else {
			availableActions.add(new DiscretisedAction(DiscretisedAction.Actions.GO_FORWARD));
			availableActions.add(new DiscretisedAction(DiscretisedAction.Actions.TURN_RIGHT));
			availableActions.add(new DiscretisedAction(DiscretisedAction.Actions.TURN_LEFT));
		}
		return availableActions;
	}

	public Map getMap() {
		return map;
	}

	public SimRobot getRobot() {
		return titi;
	}

	public ArrayList<Obstacle> getObstacles() {
		return obstaclesList;
	}

	public Radar getRadar() {
		return radar;
	}
}
