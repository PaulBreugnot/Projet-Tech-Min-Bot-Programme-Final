package simulationProgram.graph;

import simulationProgram.simMap.Obstacle;
import simulationProgram.MainSimulationProgram;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GraphicWindow {
	private Stage stage;
	private MainSimulationProgram mainSimulationProgram;
	private double scale;
	private Scene scene;

	private Circle robot;
	
	private GridPane dataGridPane;

	public GraphicWindow(Stage stage, MainSimulationProgram mainSimulationProgram) {
		this.stage = stage;
		this.mainSimulationProgram = mainSimulationProgram;

		setMainView();
	}

	public void setMainView() {
		stage.setWidth(1000);
		stage.setHeight(800);
		stage.setTitle("Simulation Robot");
		AnchorPane root = new AnchorPane();
		scene = new Scene(root);

		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		AnchorPane.setTopAnchor(vbox, 10.0);
		AnchorPane.setBottomAnchor(vbox, 10.0);
		AnchorPane.setLeftAnchor(vbox, 10.0);
		AnchorPane.setRightAnchor(vbox, 10.0);

		vbox.getChildren().addAll(setRunButton(), setMapView(), setDataView());

		root.getChildren().add(vbox);

		stage.setScene(scene);
		stage.show();

	}

	public Button setRunButton() {
		Button runButton = new Button("Run Simulation");

		runButton.setOnAction(event -> {
			Task<Void> task = new Task<Void>() {
				@Override
				public Void call() throws Exception {
					for (int i = 1; i <= Integer.MAX_VALUE; i++) {
						//robot.setCenterX(robot.getCenterX() + 10);
						updateDataLabels();
						System.out.println("loop");
						Thread.sleep(500);
					}
					return null;
				}
			};
			new Thread(task).start();
		});

		return runButton;
	}

	public StackPane setMapView() {

		int mapPaneMaxWidth = 900;
		int mapPaneMaxHeight = 600;

		StackPane mapRoot = new StackPane();
		mapRoot.setMaxSize(980, 620);
		VBox.setVgrow(mapRoot, Priority.ALWAYS);

		AnchorPane mapPane = new AnchorPane(); // Every elements will be place using this pane landmark
		setMapPaneSize(mapPane, mapPaneMaxWidth, mapPaneMaxHeight);
		System.out.println(scale);

		setObstacles();

		Rectangle map = new Rectangle(mainSimulationProgram.getMap().getWidth() * scale, mainSimulationProgram.getMap().getHeight() * scale);
		map.setFill(Color.YELLOW);
		map.setOpacity(0.5);
		mapPane.getChildren().add(map);

		setRobot(mapPane);
		setObstacles(mapPane);

		mapRoot.getChildren().add(mapPane);

		return mapRoot;

	}

	public AnchorPane setDataView() {

		AnchorPane dataRoot = new AnchorPane();
		dataRoot.setPrefHeight(0);// Forces the dataRoot pane to locate at the bottom of the window
		dataRoot.setMaxWidth(400);
		VBox.setVgrow(dataRoot, Priority.NEVER);
		setDataGridPane(dataRoot);

		return dataRoot;
	}

	public void setDataGridPane(AnchorPane dataRoot) {
		dataGridPane = new GridPane();

		ColumnConstraints column1 = new ColumnConstraints(75);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(25);
		ColumnConstraints column3 = new ColumnConstraints(70);
		ColumnConstraints column4 = new ColumnConstraints();
		column2.setPercentWidth(25);
		dataGridPane.getColumnConstraints().addAll(column1, column2, column3, column4);

		AnchorPane.setTopAnchor(dataGridPane, 5.0);
		AnchorPane.setLeftAnchor(dataGridPane, 5.0);
		AnchorPane.setBottomAnchor(dataGridPane, 5.0);
		AnchorPane.setRightAnchor(dataGridPane, 5.0);

		dataGridPane.add(new Label("Capteur 1 :"), 0, 0);
		dataGridPane.add(new Label("Capteur 2 :"), 0, 1);
		dataGridPane.add(new Label("Capteur 3 :"), 0, 2);
		dataGridPane.add(new Label("Capteur 4 :"), 0, 3);
		dataGridPane.add(new Label("Capteur 5 :"), 0, 4);

		dataGridPane.add(new Label("Motor 1 :"), 2, 0);
		dataGridPane.add(new Label("Motor 2 :"), 2, 1);
		setDataLabels();

		dataRoot.getChildren().add(dataGridPane);
	}

	private void setMapPaneSize(AnchorPane mapPane, int mapPaneMaxWidth, int mapPaneMaxHeight) {
		// Some tricky strange operations to optimize the mapPane size, preserving scale
		int magicPixelScale = (mapPaneMaxHeight * 1000) / (mapPaneMaxWidth);
		int magicMeterScale = ((int) (mainSimulationProgram.getMap().getHeight() * 1000))
				/ ((int) (mainSimulationProgram.getMap().getWidth() * 1));

		if (magicPixelScale == magicMeterScale) {
			scale = mapPaneMaxHeight / mainSimulationProgram.getMap().getHeight();
			setSize(mapPane, mapPaneMaxWidth, mapPaneMaxHeight);
		} else if (magicPixelScale > magicMeterScale) {
			scale = mapPaneMaxWidth / mainSimulationProgram.getMap().getWidth();
			setSize(mapPane, mapPaneMaxWidth, (int) (mainSimulationProgram.getMap().getHeight() * scale));
		} else if (magicPixelScale < magicMeterScale) {
			scale = mapPaneMaxHeight / mainSimulationProgram.getMap().getHeight();
			setSize(mapPane, (int) (mainSimulationProgram.getMap().getWidth() * scale), mapPaneMaxHeight);
		}
	}

	private void setSize(AnchorPane mapPane, int width, int height) {
		mapPane.setMaxWidth(width);
		mapPane.setMinWidth(width);
		mapPane.setMaxHeight(height);
		mapPane.setMinHeight(height);
	}

	public void setObstacles() {

	}

	public void setRobot(AnchorPane mapPane) {
		robot = new Circle(mainSimulationProgram.getRobot().getXPos() * scale, mainSimulationProgram.getRobot().getYPos() * scale,
				mainSimulationProgram.getRobot().getRobotSize() * scale);
		robot.setFill(Color.RED);

		mapPane.getChildren().addAll(robot);
	}

	public void setObstacles(AnchorPane mapPane) {
		for (Obstacle obstacle : mainSimulationProgram.getObstacles()) {
			Circle obstacleShape = new Circle(obstacle.getXPos() * scale, obstacle.getYPos() * scale,
					obstacle.getRadius() * scale);
			obstacleShape.setFill(Color.GREEN);
			mapPane.getChildren().add(obstacleShape);
		}
	}
	
	private void setDataLabels() {
		dataGridPane.add(new Label(Double.toString(mainSimulationProgram.getRobot().getRobotCaptors().get(0).getDistance())), 1, 0);
		dataGridPane.add(new Label(Double.toString(mainSimulationProgram.getRobot().getRobotCaptors().get(1).getDistance())), 1, 1);
		dataGridPane.add(new Label(Double.toString(mainSimulationProgram.getRobot().getRobotCaptors().get(2).getDistance())), 1, 2);
		dataGridPane.add(new Label(Double.toString(mainSimulationProgram.getRobot().getRobotCaptors().get(3).getDistance())), 1, 3);
		dataGridPane.add(new Label(Double.toString(mainSimulationProgram.getRobot().getRobotCaptors().get(4).getDistance())), 1, 4);
		
		dataGridPane.add(new Label(Double.toString(mainSimulationProgram.getRobot().getV1())), 3, 0);
		dataGridPane.add(new Label(Double.toString(mainSimulationProgram.getRobot().getV2())), 3, 1);
	}
	
	private void updateDataLabels() {
		mainSimulationProgram.getRadar().updateCaptorDistances();
		setDataLabels();
	}
}
