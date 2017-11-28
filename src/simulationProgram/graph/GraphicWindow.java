package simulationProgram.graph;

import simulationProgram.simMap.Obstacle;
import simulationProgram.simRobot.SimCaptor;
import simulationProgram.util.Move;
import simulationProgram.util.Round;
import simulationProgram.MainSimulationProgram;

import java.util.ArrayList;

import commonInterface.Captor;
import javafx.application.Platform;
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
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GraphicWindow {
	private Stage stage;
	private MainSimulationProgram mainSimulationProgram;
	private double scale;
	private Scene scene;

	private Circle robotCircle;
	private Label labelCaptor1;
	private Label labelCaptor2;
	private Label labelCaptor3;
	private Label labelCaptor4;
	private Label labelCaptor5;
	private Line captorLine1;
	private Line captorLine2;
	private Line captorLine3;
	private Line captorLine4;
	private Line captorLine5;
	private Label labelV1;
	private Label labelV2;
	
	public ArrayList<Double[]> testSequence = Move.moveTest();
	
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
					for (Double[] coordinates : testSequence) {
						mainSimulationProgram.getRobot().setXPos(coordinates[0]);
						mainSimulationProgram.getRobot().setYPos(coordinates[1]);
						mainSimulationProgram.getRobot().setAlphaOrientation(coordinates[2]);
						mainSimulationProgram.getRadar().updateCaptorDistances();
						updateDataLabels();
						updateGraphicItems();
						Thread.sleep(50);
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

		Rectangle map = new Rectangle(mainSimulationProgram.getMap().getWidth() * scale, mainSimulationProgram.getMap().getHeight() * scale);
		map.setFill(Color.YELLOW);
		map.setOpacity(0.5);
		mapPane.getChildren().add(map);

		setRobot(mapPane);
		setObstacles(mapPane);
		initRadarLines(mapPane);

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

	public void setRobot(AnchorPane mapPane) {
		robotCircle = new Circle(mainSimulationProgram.getRobot().getXPos() * scale, mainSimulationProgram.getRobot().getYPos() * scale,
				mainSimulationProgram.getRobot().getRobotSize() * scale);
		robotCircle.setFill(Color.RED);

		mapPane.getChildren().add(robotCircle);
	}
	
	public void updateRobotCoordinates() {
		robotCircle.setCenterX(mainSimulationProgram.getRobot().getXPos() * scale);
		robotCircle.setCenterY(mainSimulationProgram.getRobot().getYPos() * scale);
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
		labelCaptor1 = new Label(Double.toString(mainSimulationProgram.getRobot().getRobotCaptors().get(0).getDistance()));
		dataGridPane.add(labelCaptor1, 1, 0);
		labelCaptor2 = new Label(Double.toString(mainSimulationProgram.getRobot().getRobotCaptors().get(1).getDistance()));
		dataGridPane.add(labelCaptor2, 1, 1);
		labelCaptor3 = new Label(Double.toString(mainSimulationProgram.getRobot().getRobotCaptors().get(2).getDistance()));
		dataGridPane.add(labelCaptor3, 1, 2);
		labelCaptor4 = new Label(Double.toString(mainSimulationProgram.getRobot().getRobotCaptors().get(3).getDistance()));
		dataGridPane.add(labelCaptor4, 1, 3);
		labelCaptor5 = new Label(Double.toString(mainSimulationProgram.getRobot().getRobotCaptors().get(4).getDistance()));
		dataGridPane.add(labelCaptor5, 1, 4);
		
		labelV1 = new Label(Double.toString(mainSimulationProgram.getRobot().getV1()));
		dataGridPane.add(labelV1, 3, 0);
		labelV2 = new Label(Double.toString(mainSimulationProgram.getRobot().getV2()));
		dataGridPane.add(labelV2, 3, 1);
	}
	
	private void updateDataLabels() {
		Platform.runLater(() -> labelCaptor1.setText(Double.toString(Round.RoundDouble(mainSimulationProgram.getRobot().getRobotCaptors().get(0).getDistance(),3))));
		Platform.runLater(() -> labelCaptor2.setText(Double.toString(Round.RoundDouble(mainSimulationProgram.getRobot().getRobotCaptors().get(1).getDistance(),3))));
		Platform.runLater(() -> labelCaptor3.setText(Double.toString(Round.RoundDouble(mainSimulationProgram.getRobot().getRobotCaptors().get(2).getDistance(),3))));
		Platform.runLater(() -> labelCaptor4.setText(Double.toString(Round.RoundDouble(mainSimulationProgram.getRobot().getRobotCaptors().get(3).getDistance(),3))));
		Platform.runLater(() -> labelCaptor5.setText(Double.toString(Round.RoundDouble(mainSimulationProgram.getRobot().getRobotCaptors().get(4).getDistance(),3))));
	}
	
	private void updateGraphicItems() {
		Platform.runLater(() -> updateLinesCoordinates());
		Platform.runLater(() -> updateRobotCoordinates());
	}
	
	private void initRadarLines(AnchorPane mapPane) {
		setLinesCoordinates();
		
		mapPane.getChildren().add(captorLine1);
		mapPane.getChildren().add(captorLine2);
		mapPane.getChildren().add(captorLine3);
		mapPane.getChildren().add(captorLine4);
		mapPane.getChildren().add(captorLine5);
		
		}
	
	private void setLinesCoordinates() {
		ArrayList<Captor> captors = mainSimulationProgram.getRobot().getRobotCaptors();
		
		ArrayList<Double> lineCoordinates = getLineCoordinates((SimCaptor) captors.get(0));
		captorLine1 = new Line(lineCoordinates.get(0), lineCoordinates.get(1), lineCoordinates.get(2), lineCoordinates.get(3));
		
		lineCoordinates = getLineCoordinates((SimCaptor) captors.get(1));
		captorLine2 = new Line(lineCoordinates.get(0), lineCoordinates.get(1), lineCoordinates.get(2), lineCoordinates.get(3));
		
		lineCoordinates = getLineCoordinates((SimCaptor) captors.get(2));
		captorLine3 = new Line(lineCoordinates.get(0), lineCoordinates.get(1), lineCoordinates.get(2), lineCoordinates.get(3));
		
		lineCoordinates = getLineCoordinates((SimCaptor) captors.get(3));
		captorLine4 = new Line(lineCoordinates.get(0), lineCoordinates.get(1), lineCoordinates.get(2), lineCoordinates.get(3));
		
		lineCoordinates = getLineCoordinates((SimCaptor) captors.get(4));
		captorLine5 = new Line(lineCoordinates.get(0), lineCoordinates.get(1), lineCoordinates.get(2), lineCoordinates.get(3));
	}
	
	private void updateLinesCoordinates() {
		ArrayList<Captor> captors = mainSimulationProgram.getRobot().getRobotCaptors();
		
		ArrayList<Double> lineCoordinates = getLineCoordinates((SimCaptor) captors.get(0));
		updateCoordinates(captorLine1, lineCoordinates);
		
		lineCoordinates = getLineCoordinates((SimCaptor) captors.get(1));
		updateCoordinates(captorLine2, lineCoordinates);
		
		lineCoordinates = getLineCoordinates((SimCaptor) captors.get(2));
		updateCoordinates(captorLine3, lineCoordinates);
		
		lineCoordinates = getLineCoordinates((SimCaptor) captors.get(3));
		updateCoordinates(captorLine4, lineCoordinates);
		
		lineCoordinates = getLineCoordinates((SimCaptor) captors.get(4));
		updateCoordinates(captorLine5, lineCoordinates);
	}
	
	private void updateCoordinates(Line line, ArrayList<Double> coordinates) {
		line.setStartX(coordinates.get(0));
		line.setStartY(coordinates.get(1));
		line.setEndX(coordinates.get(2));
		line.setEndY(coordinates.get(3));
	}
	
	private ArrayList<Double> getLineCoordinates(SimCaptor captor){
		ArrayList<Double> coordinates = new ArrayList<>();
		double xRobot = mainSimulationProgram.getRobot().getXPos()*scale;
		double yRobot = mainSimulationProgram.getRobot().getYPos()*scale;
		double captorOrientation = mainSimulationProgram.getRobot().getAlphaOrientation() + captor.getCaptorOrientation();
		System.out.println("Orientation = " + captorOrientation);
		double xFinal = xRobot + captor.getDistance()* Math.cos(captorOrientation * Math.PI/180) * scale;
		double yFinal = yRobot + captor.getDistance()* Math.sin(captorOrientation * Math.PI/180) * scale;
		System.out.println("xFinal : " + xFinal/scale);
		System.out.println("yFinal : " + yFinal/scale);
		coordinates.add(xRobot);
		coordinates.add(yRobot);
		coordinates.add(xFinal);
		coordinates.add(yFinal);
		return coordinates;
	}
}
