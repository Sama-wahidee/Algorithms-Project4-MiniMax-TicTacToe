package application;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TwoPersonGame {
	char cPlayer;
	Label[][] labels;
	Stage primaryStage;
	int round;
	int Trounds;
	int Xscour = 0;
	int Oscour = 0;
	Label roundL;
	Label turnL;
	Label XscourL;
	Label OscourL;
char start;
	public TwoPersonGame(Stage primaryStage, Label[][] labels, int Trounds, Character start, Label roundL,
			Label turnL, Label XscourL, Label OscourL) {
		this.primaryStage = primaryStage;
		this.labels = labels;
		this.Trounds = Trounds;
		this.round = 0;
		this.Xscour = 0;
		this.Oscour = 0;
		this.roundL = roundL;
		this.turnL = turnL;
		this.XscourL = XscourL;
		this.OscourL = OscourL;
		this.roundL.setText(round + "/" + Trounds);
		this.turnL.setText(start + " turn");
		XscourL.setText(Xscour + "");
		OscourL.setText(Oscour + "");
		this.cPlayer=start;
		this.start=start;

	}
	public void startGame() {
		resetRound();
		primaryStage.show();
	}

	Label createLabel(Character text, double x, double y) {
	    Label label = new Label(String.valueOf(text));
	    label.setStyle("-fx-font-size: 100; -fx-background-color: transparent; -fx-border-width: 0;");
	    label.setAlignment(Pos.CENTER);
	    label.setPrefSize(200, 200);
	    StackPane.setAlignment(label, Pos.TOP_LEFT); // Adjust alignment
	    label.setLayoutX(x);
	    label.setLayoutY(y);
	    label.setOnMouseClicked(e -> {
	        if (label.getText().isEmpty()) {
	            label.setText(String.valueOf(cPlayer));
	            turnL.setText(cPlayer+" turn...");
	            if (checkForWinner()) {
	                dialog(AlertType.INFORMATION, "Player " + cPlayer + " wins Round " + round + "!");
	                if (cPlayer == 'X') {
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
	            } else {
	                cPlayer = (cPlayer == 'X') ? 'O' : 'X';
	                turnL.setText(cPlayer + " turn...");
	            }

	            if (isGameOver()) {
	                resetRound();
	            }
	        }
	    });

	    return label;
	}


	private boolean checkForWinner() {
		// Check rows, columns, and diagonals for a win
		for (int i = 0; i < 3; i++) {
			if (checkRow(i) || checkColumn(i)) {
				return true;
			}
		}
		return checkDiagonals();
	}

	private boolean checkDiagonals() {
		return labels[0][0].getText().equals(String.valueOf(cPlayer))
				&& labels[1][1].getText().equals(String.valueOf(cPlayer))
				&& labels[2][2].getText().equals(String.valueOf(cPlayer))
				|| labels[0][2].getText().equals(String.valueOf(cPlayer))
						&& labels[1][1].getText().equals(String.valueOf(cPlayer))
						&& labels[2][0].getText().equals(String.valueOf(cPlayer));
	}

	private boolean checkRow(int row) {
		return labels[row][0].getText().equals(String.valueOf(cPlayer))
				&& labels[row][1].getText().equals(String.valueOf(cPlayer))
				&& labels[row][2].getText().equals(String.valueOf(cPlayer));
	}

	private boolean checkColumn(int col) {
		return labels[0][col].getText().equals(String.valueOf(cPlayer))
				&& labels[1][col].getText().equals(String.valueOf(cPlayer))
				&& labels[2][col].getText().equals(String.valueOf(cPlayer));
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

	private void resetRound() {
		if (isGameOver()) {
			// Add logic to determine and display the final winner
			String winner = (Xscour > Oscour) ? "Player X" : "Player O";
			dialog(AlertType.INFORMATION, "Game Over! " + winner + " wins the game!");
			primaryStage.close();
		} else {
			round++;
			roundL.setText(round + "/" + Trounds + "\n");
			cPlayer = start;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					labels[i][j].setText("");
				}
			}
		}
	}

	private boolean isGameOver() {
		return round > Trounds;
	}

	public void dialog(AlertType t, String s) {
		Alert alert = new Alert(t);
		alert.setTitle("Dialog");
		alert.setHeaderText("");
		alert.setContentText(s);
		alert.showAndWait();
	}

}