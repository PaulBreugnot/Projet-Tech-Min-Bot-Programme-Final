package realRobotInterface.JavaArduinoDialog.graphic;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import qLearning.model.DiscretisedAction;
import realRobotInterface.MainRemoteControl;
import realRobotInterface.JavaArduinoDialog.Hardware.SimpleWrite;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GraphicWindow {

	String distance;
	private MainRemoteControl mainRemoteControl;
	private Stage stage;
	private Scene scene;
	private Button runButton;
	private Button upButton;
	private Button leftButton;
	private Button rightButton;
	private Label distanceLabel;

	private boolean keyReleased = true;

	public GraphicWindow(Stage stage, MainRemoteControl mainRemoteControl) {
		this.mainRemoteControl = mainRemoteControl;
		this.stage = stage;
		setMainView();
	}

	private void setMainView() {

		stage.setWidth(500);
		stage.setHeight(500);
		stage.setTitle("Remote Control Robot");
		AnchorPane root = new AnchorPane();
		scene = new Scene(root);

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.Z) {
					if (keyReleased) {
						upButton.arm();
						keyReleased = false;
						System.out.println("GO_FORWARD");
						DiscretisedAction action = new DiscretisedAction(DiscretisedAction.Actions.GO_FORWARD);
						MainRemoteControl.executeDiscretisedAction(action);
					}
				} else if (event.getCode() == KeyCode.Q) {
					if (keyReleased) {
						leftButton.arm();
						keyReleased = false;
						System.out.println("TURN_LEFT");
						DiscretisedAction action = new DiscretisedAction(DiscretisedAction.Actions.TURN_LEFT);
						MainRemoteControl.executeDiscretisedAction(action);
					}
				} else if (event.getCode() == KeyCode.D) {
					if (keyReleased) {
						rightButton.arm();
						keyReleased = false;
						System.out.println("TURN_RIGHT");
						DiscretisedAction action = new DiscretisedAction(DiscretisedAction.Actions.TURN_RIGHT);
						MainRemoteControl.executeDiscretisedAction(action);
					}
				}
			}
		});

		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.Z || event.getCode() == KeyCode.Q || event.getCode() == KeyCode.D) {
					keyReleased = true;
					upButton.disarm();
					leftButton.disarm();
					rightButton.disarm();
					SimpleWrite sendSomething = new SimpleWrite(MainRemoteControl.readingThread, "STOP",
							SimpleWrite.sendingMode.SIMPLE_SENDING);
				}
			}
		});

		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(50);
		AnchorPane.setTopAnchor(vbox, 10.0);
		AnchorPane.setBottomAnchor(vbox, 10.0);
		AnchorPane.setLeftAnchor(vbox, 10.0);
		AnchorPane.setRightAnchor(vbox, 10.0);
		
		setRunButton();
		vbox.getChildren().add(runButton);
		
		setDistanceLabel();
		vbox.getChildren().add(distanceLabel);
		
		vbox.getChildren().add(setButtons());

		root.getChildren().add(vbox);

		stage.setScene(scene);
		stage.show();
	}

	private void setRunButton() {
		runButton = new Button("Run");
		runButton.setOnAction(event -> {
			Task<Void> task = new Task<Void>() {
				@Override
				public Void call() throws Exception {
					mainRemoteControl.runApp();
					return null;
				}
			};
			new Thread(task).start();
		});
	}
	private void setDistanceLabel() {
		distanceLabel = new Label("Distance");
	}

	private HBox setButtons() {
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(30);

		ArrayList<Button> buttons = new ArrayList<>();

		leftButton = new Button("GO LEFT");
		leftButton.setPrefHeight(50);
		leftButton.setPrefWidth(100);
		leftButton.setDisable(true);
		leftButton.setOpacity(100);

		upButton = new Button("GO FORWARD");
		upButton.setPrefHeight(50);
		upButton.setPrefWidth(100);
		upButton.setDisable(true);
		upButton.setOpacity(100);

		rightButton = new Button("GO RIGHT");
		rightButton.setPrefHeight(50);
		rightButton.setPrefWidth(100);
		rightButton.setDisable(true);
		rightButton.setOpacity(100);

		buttons.add(leftButton);
		buttons.add(upButton);
		buttons.add(rightButton);

		hbox.getChildren().addAll(buttons);
		return hbox;
	}

	public void updateDistance(String distance) {
		Platform.runLater(() -> distanceLabel.setText(distance));
	}
}
