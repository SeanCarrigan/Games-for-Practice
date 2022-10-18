import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.event.ActionEvent;

public class GameCenter {
    private StackPane pane;
    private Label displayMessage;
    private Button startButton;
    // GameCenter constructor
    GameCenter() {
        pane = new StackPane();
        pane.setMinSize(UISizes.APP_WIDTH, UISizes.GAME_CENTER_HEIGHT);
        // positon within application (middle of window)
        pane.setTranslateX(UISizes.APP_WIDTH / 2);
        pane.setTranslateY(UISizes.GAME_CENTER_HEIGHT / 2);

        displayMessage = new Label("Tic-Tac-Toe");
        displayMessage.setMinSize(UISizes.APP_WIDTH, UISizes.GAME_CENTER_HEIGHT);
        // font settings for message (size, position)
        displayMessage.setFont(Font.font(24));
        displayMessage.setAlignment(Pos.CENTER); // directly in middle
        displayMessage.setTranslateY(-20); // message 20 pixels above center (Y axis)
        pane.getChildren().add(displayMessage); // add displayMessage to stack pane

        startButton = new Button("Start New Game");
        startButton.setMinSize(135,30); // set dimensions of button
        startButton.setTranslateY(20); // position button 20 pixels below mid point
        pane.getChildren().add(startButton); // add startButton to stack pane
    }

    // getter for stack pane
    public StackPane getStackPane() {
        return pane;
    }
    // update displayMessage
    public void updateMessage(String message) {
        displayMessage.setText(message);
    }
    // show startButton
    public void showStartButton() {
        startButton.setVisible(true);
    }
    // hide startButton
    public void hideStartButton() {
        startButton.setVisible(false);
    }
    // startButton action (logic inside main class)
    public void setStartButtonOnAction(EventHandler<ActionEvent> onAction) {
        startButton.setOnAction(onAction);
    }
}
