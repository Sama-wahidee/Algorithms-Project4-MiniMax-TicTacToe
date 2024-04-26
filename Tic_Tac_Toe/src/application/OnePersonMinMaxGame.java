package application;

import java.util.Optional;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class OnePersonMinMaxGame {
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
	boolean userStartsFirst;

	public static final int WIN_STATE = 2;
	public static final int LOSE_STATE = -2;
	public static final int DRAW_STATE = 1;
	public static final int NOT_FINISHED_STATE = 0;

	public OnePersonMinMaxGame(Stage primaryStage, Label[][] labels, int Trounds, char user, boolean userStartsFirst,
			Label roundL, Label XscourL, Label OscourL) {
		this.primaryStage = primaryStage;
		this.labels = labels;
		this.Trounds = Trounds;
		this.round = 1;
		this.Xscour = 0;
		this.Oscour = 0;
		this.roundL = roundL;
		this.XscourL = XscourL;
		this.OscourL = OscourL;
		this.roundL.setText(round + "/" + Trounds);
		XscourL.setText(Xscour + "");
		OscourL.setText(Oscour + "");
		initializePlayers(user, userStartsFirst);
	}

	private void initializePlayers(char userChoice, boolean userStartsFirst) {
		this.userStartsFirst = userStartsFirst;
		if (userChoice == 'X') {
			userPlayer = 'X';
			computerPlayer = 'O';
		} else {
			userPlayer = 'O';
			computerPlayer = 'X';
		}

		cPlayer = userStartsFirst ? userPlayer : computerPlayer;
	}

	public void startGame() {
		System.out.println("Hi");
		resetRound();
		primaryStage.show();
	}

	private void resetRound() {
		if (isGameOver()) {
			primaryStage.close();
		} else {
			resetGameBoard();
			if (!userStartsFirst) {
				makeComputerMove();
			}
		}
	}

	private boolean isGameOver() {
		System.out.println(Xscour + "," + Oscour);

		if ((Xscour > Trounds / 2 || Oscour > Trounds / 2) && roundsFinished()) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Dialog");
			alert.setHeaderText("");
			alert.setContentText("Do you want to continue?");
			System.out.println("dfghjklksdjcnhnjfh");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() != ButtonType.OK) {
				primaryStage.close();
				return true;
			} else {
				return false; // End the game

			}
		}

		return round > Trounds;
	}

	private boolean roundsFinished() {
		return round >= Trounds;
	}

	public Label createLabel(Character text, double x, double y) {
		Label label = new Label(text + "");
		label.setStyle("-fx-font-size: 100; -fx-background-color: transparent; -fx-border-width: 0;");
		label.setAlignment(Pos.CENTER);
		label.setPrefSize(200, 200);
		StackPane.setAlignment(label, Pos.TOP_LEFT);
		label.setLayoutX(x);
		label.setLayoutY(y);
		label.setOnMouseClicked(e -> handleLabelClick(label));

		return label;
	}

	private void handleLabelClick(Label label) {
		if (label.getText().equals(' ' + "")) {
			label.setText(userPlayer + "");
			if (checkGameState() != NOT_FINISHED_STATE) {
				displayResult();
			} else {
				makeComputerMove();
				if (checkGameState() != NOT_FINISHED_STATE) {
					displayResult();
				}
			}
		}
	}

	private void makeComputerMove() {
		Move bestMove = findBestMove();
		if (bestMove.row != -1 && bestMove.column != -1) {
			labels[bestMove.row][bestMove.column].setText(computerPlayer + "");
			if (checkGameState() != NOT_FINISHED_STATE) {
				displayResult();
			}
		}
	}

	private void displayResult() {
		int gameFinalState = checkGameState();

		if (gameFinalState == DRAW_STATE) {
			dialog(AlertType.INFORMATION, "It's a draw in Round " + round + "!");
		} else if (gameFinalState == LOSE_STATE) {
			dialog(AlertType.INFORMATION, "You win Round " + round + "!");
			if (userPlayer == 'X') {
				Xscour++;
				XscourL.setText(Xscour + "");
			} else {
				Oscour++;
				OscourL.setText(Oscour + "");
			}
		} else {
			dialog(AlertType.INFORMATION, "Computer wins Round " + round + "!");
			if (computerPlayer == 'X') {
				Xscour++;
				XscourL.setText(Xscour + "");
			} else {
				Oscour++;
				OscourL.setText(Oscour + "");
			}
		}

		if (round < Trounds) {
			round++;
			roundL.setText(round + "/" + Trounds);
			resetGameBoard();
			if (!userStartsFirst) {
				makeComputerMove();
			}
		} else {
			displayFinalResult();
		}
	}

	private void resetGameBoard() {
		isGameOver();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				labels[i][j].setText(" ");
			}
		}

	}

	private void displayFinalResult() {
		dialog(AlertType.INFORMATION, "Game Over!\nX Score: " + Xscour + "\nO Score: " + Oscour);
	}

	// Minimax Algorithm
	public int minimax(int depth,boolean isMaximizing) {
		int gameState = checkGameState();
		if (gameState != NOT_FINISHED_STATE) {
			return gameState;
		}

		if (isMaximizing) {
			int possibility = -10;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					// Is the cell available?
					if (labels[i][j].getText().equals(' ' + "")) {
						labels[i][j].setText(computerPlayer + "");
						int score = minimax(depth-1,false);
						labels[i][j].setText(' ' + "");
						possibility = Integer.max(score, possibility);
					}
				}
			}
			return possibility;
		} else {
			int possibility = 10;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					// Is the cell available?
					if (labels[i][j].getText().equals(' ' + "")) {
						labels[i][j].setText(userPlayer + "");
						int score = minimax(depth-1,true);
						labels[i][j].setText(' ' + "");
						possibility = Integer.min(score, possibility);
					}

				}
			}
			return possibility;
		}
	}

	public Move findBestMove() {
		int bestVal = Integer.MIN_VALUE;
		Move Move = new Move();
		Move.row = -1;
		Move.column = -1;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				// Check if cell is empty
				if (labels[i][j].getText().equals(' ' + "")) {
					labels[i][j].setText(computerPlayer + "");
					int moveVal = minimax(8,false);
					labels[i][j].setText(' ' + "");
					if (moveVal > bestVal) {
						Move.row = i;
						Move.column = j;
						bestVal = moveVal;
					}
				}
			}
		}
		return Move;
	}

	public int checkGameState() {
		int gameState = NOT_FINISHED_STATE;

		// Check for computerPlayer
		if (checkPlayerWin(computerPlayer)) {
			gameState = WIN_STATE;
		}

		// Check for userPlayer
		if (checkPlayerWin(userPlayer)) {
			gameState = LOSE_STATE;
		}

		int emptyCells = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (labels[i][j].getText().equals(' ' + "")) {
					emptyCells++;
				}
			}
		}

		// Check for a draw
		if (gameState == NOT_FINISHED_STATE && emptyCells == 0) {
			gameState = DRAW_STATE;
		}

		return gameState;
	}

	private boolean checkPlayerWin(char player) {
		for (int i = 0; i < 3; i++) {
			// Check rows and columns
			if ((equals3(labels[i][0], labels[i][1], labels[i][2]) && labels[i][0].getText().equals(player + ""))
					|| (equals3(labels[0][i], labels[1][i], labels[2][i])
							&& labels[0][i].getText().equals(player + ""))) {
				return true;
			}
		}

		// Check diagonals
		if (equals3(labels[0][0], labels[1][1], labels[2][2]) && labels[0][0].getText().equals(player + "")) {
			return true;
		}
		if (equals3(labels[0][2], labels[1][1], labels[2][0]) && labels[0][2].getText().equals(player + "")) {
			return true;
		}

		return false;
	}

	private boolean equals3(Label a, Label b, Label c) {
		String valueA = a.getText();
		String valueB = b.getText();
		String valueC = c.getText();

		return valueA.equals(valueB) && valueB.equals(valueC) && !valueA.isEmpty();
	}

	public void dialog(AlertType t, String s) {
		Alert alert = new Alert(t);
		alert.setTitle("Dialog");
		alert.setHeaderText("");
		alert.setContentText(s);
		alert.showAndWait();
	}

	class Move {
		public int row;
		public int column;
	}
}
