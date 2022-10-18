import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;


public class BoardLayout {
    private GameCenter gameCenter;
    private StackPane pane;
    private Tile[][] boardTiles = new Tile[3][3]; // add tile to BoardLayout
    private char playerTurn = 'X';// keep track of which players turn it is
    private boolean isEndOfGame = false; // flag to indicate end of game
    private Line winningLine;

    //BoardLayout Constructor
    public BoardLayout(GameCenter gameCenter) {
        this.gameCenter = gameCenter;
        // make new pane and set size
        pane = new StackPane();
        pane.setMinSize(UISizes.APP_WIDTH, UISizes.BOARD_HEIGHT);
        pane.setTranslateX(UISizes.APP_WIDTH / 2); // set pane in middle of application
        pane.setTranslateY((UISizes.BOARD_HEIGHT / 2) + UISizes.GAME_CENTER_HEIGHT);
        addBoardTiles();
        winningLine = new Line();
        pane.getChildren().add(winningLine);
    }

    private void addBoardTiles() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Tile tile = new Tile(); // create a new tile
                tile.getStackPane().setTranslateX((col * 100) - 100);
                tile.getStackPane().setTranslateY((row * 100) - 100);
                pane.getChildren().add(tile.getStackPane());
                boardTiles[row][col] = tile;
            }
        }
    }
    public void startNewGame() {
        isEndOfGame = false;
        playerTurn = 'X';
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
               boardTiles[row][col].setTileValue("");
            }
        }
        winningLine.setVisible(false);
    }

    // stackPane getter
    public StackPane getStackPane() {
        return pane;
    }

    public void checkForWinner() {
        // helper functions to checkForWinner
        checkRowsForWinner();
        checkColsForWinner();
        checkTopLeftToBottomRight();
        checkTopRightToBottomLeft();
        checkForTie();
    }
    private void checkRowsForWinner() {
        if (!isEndOfGame) {
            for (int row = 0; row < 3; row++) {
                if (boardTiles[row][0].getTileValue().equals(boardTiles[row][1].getTileValue()) &&
                        boardTiles[row][0].getTileValue().equals(boardTiles[row][2].getTileValue()) &&
                        !boardTiles[row][0].getTileValue().isEmpty()) {
                    String winner = boardTiles[row][0].getTileValue();
                    endGame(winner, new WinningTiles(boardTiles[row][0], boardTiles[row][1], boardTiles[row][2]));
                    return;
                }
            }
        }
    }
    private void checkColsForWinner() {
        if (!isEndOfGame) {
            for (int col = 0; col < 3; col++) {
                if (boardTiles[0][col].getTileValue().equals(boardTiles[1][col].getTileValue()) &&
                        boardTiles[0][col].getTileValue().equals(boardTiles[2][col].getTileValue()) &&
                        !boardTiles[0][col].getTileValue().isEmpty()) {
                    String winner = boardTiles[0][col].getTileValue();
                    endGame(winner, new WinningTiles(boardTiles[0][col], boardTiles[1][col], boardTiles[2][col]));
                    return;
                }
            }
        }
    }
    private void checkTopLeftToBottomRight() {
        if (!isEndOfGame) {
            if (boardTiles[0][0].getTileValue().equals(boardTiles[1][1].getTileValue()) &&
                    boardTiles[0][0].getTileValue().equals(boardTiles[2][2].getTileValue()) &&
                    !boardTiles[0][0].getTileValue().isEmpty()) {
                String winner = boardTiles[0][0].getTileValue();
                endGame(winner, new WinningTiles(boardTiles[0][0], boardTiles[1][1], boardTiles[2][2]));
            }
        }
    }
    private void checkTopRightToBottomLeft() {
        if (!isEndOfGame) {
            if (boardTiles[0][2].getTileValue().equals(boardTiles[1][1].getTileValue()) &&
                    boardTiles[0][2].getTileValue().equals(boardTiles[2][0].getTileValue()) &&
                    !boardTiles[0][2].getTileValue().isEmpty()) {
                String winner = boardTiles[0][2].getTileValue();
                endGame(winner, new WinningTiles(boardTiles[0][2], boardTiles[1][1], boardTiles[2][0]));
            }
        }
    }
    private void checkForTie() {
        if (!isEndOfGame) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (boardTiles[row][col].getTileValue().isEmpty()) {
                        return;
                    }
                }
            }
            isEndOfGame = true;
            gameCenter.updateMessage("Tie game..."); // update display message
            gameCenter.showStartButton(); // display startGame button to players again
        }
    }

    private void endGame(String winner, WinningTiles winningTiles) {
        isEndOfGame = true;
        drawWinningLIne(winningTiles);
        gameCenter.updateMessage("Player " + winner + " Wins!!"); // update display message
        gameCenter.showStartButton(); // display startGame button to players again
    }
    private void drawWinningLIne(WinningTiles winningTiles) {
        winningLine.setStartX(winningTiles.start.getStackPane().getTranslateX());
        winningLine.setStartY(winningTiles.start.getStackPane().getTranslateY());
        winningLine.setEndX(winningTiles.end.getStackPane().getTranslateX());
        winningLine.setEndY(winningTiles.end.getStackPane().getTranslateY());
        winningLine.setTranslateX(winningTiles.middle.getStackPane().getTranslateX());
        winningLine.setTranslateY(winningTiles.middle.getStackPane().getTranslateY());
        winningLine.setVisible(true);
    }
    private class WinningTiles {
        Tile start;
        Tile middle;
        Tile end;
        public WinningTiles(Tile start,Tile middle, Tile end) {
            this.start = start;
            this.middle = middle;
            this.end = end;
        }
    }

    private class Tile {
        private StackPane pane;
        private Label label;

        public Tile() {
            pane = new StackPane();
            pane.setMinSize(100, 100); // size of each board tile
            // border for each individual tile on board
            Rectangle tileBorder = new Rectangle();
            tileBorder.setHeight(100);// height of border
            tileBorder.setWidth(100); // width of border
            tileBorder.setFill(Color.TRANSPARENT); // border fill type
            tileBorder.setStroke(Color.BLACK); // set color of border
            pane.getChildren().add(tileBorder); // add tile to stackPane
            // labels for tiles
            label = new Label("");
            label.setAlignment(Pos.CENTER); // label alignment in center of tile
            label.setFont(Font.font(24)); // set font size for tile label
            pane.getChildren().add(label); // add label to tile

            // handle click event on tile
            pane.setOnMouseClicked(event -> {
                // update value on tile if allowed
                if (label.getText().isEmpty() && !isEndOfGame) {
                    label.setText(getPlayerTurn()); // change label on tile
                    changePlayerTurn(); // change player turn
                    checkForWinner(); // check for winner after each turn
                }
            });
        }
        // StackPane getter
        public StackPane getStackPane() {
            return pane;
        }

        // Label getter
        public String getTileValue() {
            return label.getText();
        }

        // set Tile value
        public void setTileValue(String tileValue) {
            label.setText(tileValue);
        }

        // change player turn
        public void changePlayerTurn() {
            if (playerTurn == 'X') {
                playerTurn = 'O';
            } else {
                playerTurn = 'X';
            }
            // update displayMessage when playerTurn changes
            gameCenter.updateMessage("Player " + playerTurn + "'s turn");
        }

        // getter for playerTurn
        public String getPlayerTurn() {
            return String.valueOf(playerTurn);
        }
    }
}
