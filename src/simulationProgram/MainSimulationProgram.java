package simulationProgram;

import java.util.ArrayList;

import simulationProgram.simMap.Map;
import simulationProgram.simMap.Obstacle;
import simulationProgram.simRobot.SimRobot;
import simulationProgram.util.Radar;
import javafx.application.Application;
import javafx.stage.Stage;
import qLearning.QLearningAgent;
import qLearning.model.Action;
import qLearning.model.DiscretisedState;
import qLearning.model.Reward;
import simulationProgram.graph.GraphicWindow;

public class MainSimulationProgram extends Application {

	SimRobot titi = new SimRobot(2.5, 2.5, 0);
	ArrayList<Obstacle> obstaclesList = new ArrayList<>();
	Map map = new Map(5, 5);
	Radar radar;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		System.out.println("Start method inside Thread : " + Thread.currentThread().getName());
		obstaclesList.add(new Obstacle(4, 1.5, 0.5));
		obstaclesList.add(new Obstacle(4, 4, 0.7));
		obstaclesList.add(new Obstacle(0.5, 3, 0.4));
		GraphicWindow graphicWindow = new GraphicWindow(stage, this);
		radar = new Radar(obstaclesList, titi, map);

		DiscretisedState initState = new DiscretisedState(titi.getRobotCaptors().get(0).getDistance(),
				titi.getRobotCaptors().get(1).getDistance(), titi.getRobotCaptors().get(2).getDistance(),
				titi.getRobotCaptors().get(3).getDistance(), titi.getRobotCaptors().get(4).getDistance());
		
		initRobotSpeed();
		QLearningAgent qLearningAgent = new QLearningAgent(initState, new Action(Action.Actions.SPEED_UP_RIGHT), new Reward(0));
		while (true) {
			
		}
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
