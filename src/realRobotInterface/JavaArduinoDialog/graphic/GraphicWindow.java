package realRobotInterface.JavaArduinoDialog.graphic;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GraphicWindow {

	private Stage stage;
	private Scene scene;

	public GraphicWindow(Stage stage) {
		this.stage = stage;
		setMainView();
	}

	private void setMainView() {

		stage.setWidth(500);
		stage.setHeight(200);
		stage.setTitle("Remote Control Robot");
		AnchorPane root = new AnchorPane();
		scene = new Scene(root);

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.Z) {
					System.out.println("GO_FORWARD");
				}
				else if (event.getCode() == KeyCode.Q) {
					System.out.println("TURN_LEFT");
				}
				else if (event.getCode() == KeyCode.D) {
					System.out.println("TURN_RIGHT");
				}
			}
		});
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		AnchorPane.setTopAnchor(vbox, 10.0);
		AnchorPane.setBottomAnchor(vbox, 10.0);
		AnchorPane.setLeftAnchor(vbox, 10.0);
		AnchorPane.setRightAnchor(vbox, 10.0);

		vbox.getChildren().addAll(setButtons());

		root.getChildren().add(vbox);

		stage.setScene(scene);
		stage.show();
	}

	private ArrayList<Button> setButtons() {
		ArrayList<Button> buttons = new ArrayList<>();
		Button upButton = new Button("GO_FORWARD");
		upButton.setPrefHeight(50);
		upButton.setPrefWidth(100);

		buttons.add(upButton);
		return buttons;
	}
}
