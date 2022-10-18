
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class Main extends Application {
	private GameCenter gameCenter;
	private BoardLayout board;
	@Override
	public void start(Stage primaryStage) throws Exception {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, UISizes.APP_WIDTH,UISizes.APP_HEIGHT);
			initLayout(root);
			primaryStage.setScene(scene);
			primaryStage.show();
	}
	private void initLayout(BorderPane root) {
		initGameCenter(root);
		initTileBoard(root);
	}
	private void initGameCenter(BorderPane root) {
		gameCenter = new GameCenter();
		gameCenter.setStartButtonOnAction(startNewGame()); // set startButton
		root.getChildren().add(gameCenter.getStackPane()); // add GameCenter to root (stackPane created in GameCenter class)
	}
	// event handler for startButton
	private EventHandler<ActionEvent> startNewGame() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				gameCenter.hideStartButton(); // hide startButton when pressed
				gameCenter.updateMessage("Player X's turn"); // when startButton is pressed update displayMessage
				board.startNewGame(); // clears all tiles of values to start new game
			}
		};
	}
	private void initTileBoard(BorderPane root) {
		board = new BoardLayout(gameCenter);
		root.getChildren().add(board.getStackPane()); // add board to pane
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
}
