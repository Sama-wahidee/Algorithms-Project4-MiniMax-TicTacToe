package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class OnePersonRandomeGame {
    char cPlayer;
    Label[][] labels;
    Stage primaryStage;
    int round;
    int Trounds;
    int Xscour = 0;
    int Oscour = 0;
    Label roundL;
    Label XscourL;
    Label OscourL;
    char computerPlayer;
    char userPlayer;
    char start;
    public OnePersonRandomeGame(Stage primaryStage, Label[][] labels, int Trounds, Character start, Character palyer,
            Label roundL, Label XscourL, Label OscourL) {
        this.primaryStage = primaryStage;
        this.labels = labels;
        this.Trounds = Trounds;
        this.round = 0;
        this.Xscour = 0;
        this.Oscour = 0;
        this.roundL = roundL;
        this.XscourL = XscourL;
        this.OscourL = OscourL;
        this.start=start;
        this.roundL.setText(round + "/" + Trounds);
        XscourL.setText(Xscour + "");
        OscourL.setText(Oscour + "");
        if (palyer == 'X') {
            computerPlayer = 'O';
            userPlayer = 'X';
        } else {
            computerPlayer = 'X';
            userPlayer = 'O';
        }
        this.cPlayer = start;
    }

    
    public void startGame() {
        resetRound();
        if (cPlayer == computerPlayer) {
            makeComputerMove(); // Initiating the first move if the computer starts
        }
        primaryStage.show();
    }


    private void resetRound() {
        if (isGameOver()) {
            // Add logic to determine and display the final winner
            String winner = (Xscour > Oscour) ? "Player X" : "Player O";
            dialog(AlertType.INFORMATION, "Game Over! " + winner + " wins the game!");
            primaryStage.close();
        } else {
        	if (round<Trounds) {
                round++;
        	}
            cPlayer=start;
            roundL.setText(round + "/" + Trounds + "\n");
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    labels[i][j].setText("");
                }
            }
            if(cPlayer==computerPlayer) {
            	makeComputerMove();
            }
        }
    }

    Label createLabel1(Character text, double x, double y) {
        Label label = new Label(text + "");
        label.setStyle("-fx-font-size: 100; -fx-background-color: transparent; -fx-border-width: 0;");
        label.setAlignment(Pos.CENTER);
        label.setPrefSize(200, 200);
        StackPane.setAlignment(label, Pos.TOP_LEFT); // Adjust alignment
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setOnMouseClicked(e -> {
            int row = (int) (label.getLayoutY() / 200); // Assuming each cell is 200 pixels in height
            int col = (int) (label.getLayoutX() / 200); // Assuming each cell is 200 pixels in width
            if (label.getText().equals("") && makeMove(row, col, cPlayer)) {
                label.setText(String.valueOf(cPlayer));
                if (!checkForWinner() && !isBoardFull()) {
                    // Switch to the other player
                    makeComputerMove();
                    // updateBoard((Pane) label.getParent());
                }
            }
            if (checkForWinner()) {
                dialog(AlertType.INFORMATION, "Player " + cPlayer + " wins Round " + round + "!");
                if ('X' == userPlayer) {
                    Xscour++;
                    XscourL.setText(Xscour + "");
                } else {
                    Oscour++;
                    OscourL.setText(Oscour + "");
                }
                resetRound();
            } else if (isBoardFull()) {
                dialog(AlertType.INFORMATION, "It's a draw in Round " + round + "!");
                resetRound();
            }
            if (isGameOver()) {
                resetRound();
            }
        });
        return label;
    }

    private boolean isGameOver() {
        return round > Trounds;
    }

    private void makeComputerMove() {
    	
        int[] randomMove = random_option(labels, computerPlayer);

        if (randomMove != null) {
            makeMove(randomMove[0], randomMove[1], computerPlayer);
            if (checkForWinnerC()) {
                dialog(Alert.AlertType.INFORMATION, "Player " + computerPlayer + " wins Round " + round + "!");
                if (computerPlayer == 'X') {
                    Xscour++;
                    XscourL.setText(Xscour + "");

                } else {
                    Oscour++;
                    OscourL.setText(Oscour + "");
                }
                resetRound();
            } else if (isBoardFull()) {
                dialog(Alert.AlertType.INFORMATION, "It's a draw in Round " + round + "!");
                resetRound();
            } else {
                cPlayer = userPlayer; // Switch back to the user after the computer's move
            }
        }
    }

    private boolean makeMove(int row, int col, char player) {
        if (row >= 0 && row < 3 && col >= 0 && col < 3 && labels[row][col].getText().isEmpty()) {
            labels[row][col].setText(player + "");
            return true;
        }
        return false;
    }

    private int[] random_option(Label[][] board, char currentPlayer) {
        List<int[]> legalMoves = legalMoves(board);

        if (!legalMoves.isEmpty()) {
            Random random = new Random();
            return legalMoves.get(random.nextInt(legalMoves.size()));
        } else {
            return null; // No legal moves available
        }
    }

    private List<int[]> legalMoves(Label[][] board) {
        List<int[]> legalMoves = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getText().isEmpty()) {
                    legalMoves.add(new int[] { i, j });
                }
            }
        }

        return legalMoves;
    }

    private boolean isBoardFull() {
        // Check if all buttons are filled
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (labels[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkForWinner() {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if (checkRow(i, userPlayer) || checkColumn(i, userPlayer)) {
                return true;
            }
        }
        return checkDiagonals(userPlayer);
    }
    private boolean checkForWinnerC() {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if (checkRow(i, computerPlayer) || checkColumn(i, computerPlayer)) {
                return true;
            }
        }
        return checkDiagonals(computerPlayer);
    }

    private boolean checkDiagonals(char player) {
        return labels[0][0].getText().equals(String.valueOf(player))
                && labels[1][1].getText().equals(String.valueOf(player))
                && labels[2][2].getText().equals(String.valueOf(player))
                || labels[0][2].getText().equals(String.valueOf(player))
                        && labels[1][1].getText().equals(String.valueOf(player))
                        && labels[2][0].getText().equals(String.valueOf(player));
    }

    private boolean checkRow(int row, char player) {
        return labels[row][0].getText().equals(String.valueOf(player))
                && labels[row][1].getText().equals(String.valueOf(player))
                && labels[row][2].getText().equals(String.valueOf(player));
    }

    private boolean checkColumn(int col, char player) {
        return labels[0][col].getText().equals(String.valueOf(player))
                && labels[1][col].getText().equals(String.valueOf(player))
                && labels[2][col].getText().equals(String.valueOf(player));
    }

    public void dialog(AlertType t, String s) {
        Alert alert = new Alert(t);
        alert.setTitle("Dialog");
        alert.setHeaderText("");
        alert.setContentText(s);
        alert.showAndWait();
    }
}
