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
import qLearning.model.DiscretisedAction;
import qLearning.model.DiscretisedState;
import qLearning.model.Reward;
import simulationProgram.graph.GraphicWindow;

public class MainSimulationProgram extends Application {

	SimRobot titi = new SimRobot(2.5, 2.5, 0);
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
		obstaclesList.add(new Obstacle(4, 1.5, 0.5));
		obstaclesList.add(new Obstacle(4, 4, 0.7));
		obstaclesList.add(new Obstacle(0.5, 3, 0.4));
		radar = new Radar(obstaclesList, titi, map);
		radar.updateCaptorDistances();
		graphicWindow = new GraphicWindow(stage, this);
	}
	
	public void runSimulation() {
		DiscretisedState initState = new DiscretisedState(titi.getRobotCaptors().get(0).getDistance(),
				titi.getRobotCaptors().get(1).getDistance(), titi.getRobotCaptors().get(2).getDistance(),
				titi.getRobotCaptors().get(3).getDistance(), titi.getRobotCaptors().get(4).getDistance());
		
		initRobotSpeed();
		QLearningAgent qLearningAgent = new QLearningAgent(initState, new DiscretisedAction(DiscretisedAction.Actions.GO_FORWARD), new Reward(0));
		qLearningAgent.setAvailableActions(getAvailableAction(initState));
		while (true) {
			Action nextAction = qLearningAgent.getAction();
			if (nextAction == null) {
				titi = new SimRobot(2.5, 2.5, 0);
				qLearningAgent = new QLearningAgent(initState, new DiscretisedAction(DiscretisedAction.Actions.GO_FORWARD), new Reward(0));
			}
			else {
				
			}
			getRadar().updateCaptorDistances();
			graphicWindow.updateDataLabels();
			graphicWindow.updateGraphicItems();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	private void simpleTest() {
		boolean obstacleEncountered = false;
		getRobot().getRobotMotors().get(0).setSpeed(5.24);
		getRobot().getRobotMotors().get(1).setSpeed(0);
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
	
	private ArrayList<Action> getAvailableAction(DiscretisedState discretisedState) {
		ArrayList<Action> availableActions = new ArrayList<>();
		boolean allCaptorInfinite = true;
		for(DiscretisedState.RadarStates radarState : discretisedState.getRadarStates()) {
			if (radarState == DiscretisedState.RadarStates.S0) {
				return null;
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
	
	private void initRobotSpeed() {
		
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
