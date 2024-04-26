package application;

import java.util.stream.IntStream;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Main extends Application {
	ObservableList<Integer> roundsL = FXCollections.observableArrayList();
	ObservableList<Character> firstL = FXCollections.observableArrayList();
	ObservableList<String> typeL = FXCollections.observableArrayList();

	ComboBox<Integer> roundsCB = new ComboBox<>(roundsL);
	ComboBox<Character> startCB = new ComboBox<>(firstL);
	ComboBox<String> typeCB = new ComboBox<>(typeL);

	Image oneX = new Image(getClass().getResourceAsStream("/resources/OneX.png"));
	ImageView oneXIV = new ImageView(oneX);
	Image oneO = new Image(getClass().getResourceAsStream("/resources/OneO.png"));
	ImageView oneOIV = new ImageView(oneO);

	private Label[][] labels = new Label[3][3];
	private char cPlayer = 'X';
	Stage primaryStage;
	int Trounds = 1;
	int Xscour = 0;
	int Oscour = 0;
	int round = 0;
	Label roundL;
	Label turnL;
	Label XscourL = new Label(Xscour + "");
	Label OscourL = new Label(Oscour + "");

	@Override
	public void start(Stage primaryStage) {

		try {
			startCB.setStyle("-fx-text-fill: black;-fx-background-color: #C7C29C;-fx-font-size:20;");
			roundsCB.setStyle("-fx-text-fill: black;-fx-background-color: #C7C29C;-fx-font-size:20;");
			typeCB.setStyle("-fx-text-fill: black;-fx-background-color: #C7C29C;-fx-font-size:20;");

			IntStream.rangeClosed(1, 10).forEach(roundsL::add);
			firstL.add('X');
			firstL.add('O');
			typeL.add("Random");
			typeL.add("Unbeatable");
			BorderPane root = new BorderPane();
			backGround(root);
			Label titel = new Label("Tic Tac Toe");
			titel.setStyle("-fx-border-color: #C7C29C; " + // Border color
					"-fx-border-width: 2px; " + // Border width
					"-fx-background-color: #C7C29C; " + // Background color
					"-fx-text-fill: #FBE2CE; " + // Text fill color
					"-fx-font-size: 70px; -fx-font-weight: bold;" // Font size
			);
			Image one = new Image(getClass().getResourceAsStream("/resources/One.png"));
			ImageView oneIV = new ImageView(one);
			Image two = new Image(getClass().getResourceAsStream("/resources/Two.png"));
			ImageView twoIV = new ImageView(two);
			oneIV.setOnMouseClicked(m -> {
				root.getChildren().removeAll(titel, oneIV, twoIV);
				Image You = new Image(getClass().getResourceAsStream("/resources/You.png"));
				ImageView YouIV = new ImageView(You);
				ComboBox<Character> youCB = new ComboBox<>(firstL);
				youCB.setStyle("-fx-text-fill: black;-fx-background-color: #C7C29C;-fx-font-size:20;");
				HBox youHB = new HBox();
				youHB.getChildren().addAll(YouIV, youCB);
				youHB.setSpacing(10);
				youHB.setAlignment(Pos.CENTER);
				Label rounds = new Label("Number of rounds: ");
				rounds.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
				HBox roundsHB = new HBox();
				roundsHB.getChildren().addAll(rounds, roundsCB);
				roundsHB.setSpacing(10);
				roundsHB.setAlignment(Pos.CENTER);
				Label startWith = new Label("Start with: ");
				startWith.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
				HBox startHB = new HBox();
				startHB.getChildren().addAll(startWith, startCB);
				startHB.setSpacing(10);
				startHB.setAlignment(Pos.CENTER);
				Label Gtybe = new Label("Game tybe: ");
				Gtybe.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
				HBox GtybeHB = new HBox();
				VBox Pvb = new VBox();
				HBox commentsHB = new HBox();
				Button startBT = new Button("Start Game");
				typeCB.setOnAction(e -> {
					if (typeCB.getValue() == "Unbeatable") {
						Pvb.getChildren().clear();
						Pvb.getChildren().addAll(youHB, roundsHB, startHB, GtybeHB, commentsHB, startBT);
					} else {
						Pvb.getChildren().clear();
						Pvb.getChildren().addAll(youHB, roundsHB, startHB, GtybeHB, startBT);
					}
				});

				GtybeHB.getChildren().addAll(Gtybe, typeCB);
				GtybeHB.setSpacing(10);
				GtybeHB.setAlignment(Pos.CENTER);
				RadioButton with = new RadioButton("With comments");
				with.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
				RadioButton without = new RadioButton("Without comments");
				without.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
				ToggleGroup toggleGroup = new ToggleGroup();
				with.setToggleGroup(toggleGroup);
				without.setToggleGroup(toggleGroup);
				with.setSelected(true);
				commentsHB.getChildren().addAll(with, without);
				commentsHB.setSpacing(10);
				commentsHB.setAlignment(Pos.CENTER);
				startBT.setStyle(
						"-fx-border-color: #FBE2CE;-fx-border-width: 2px;-fx-background-color: #C7C29C;-fx-text-fill: #FBE2CE;-fx-font-size: 50px; -fx-font-weight: bold;");
				startBT.setOnAction(o -> {
					cPlayer = startCB.getValue();
					Pvb.getChildren().removeAll(youHB);
					BorderPane pb = new BorderPane();
					pb.setStyle("-fx-background-color: #C7C29C;");
					Scene s = new Scene(pb, 800, 750);
					Trounds = roundsCB.getValue();
					roundL = new Label(round + "/" + roundsCB.getValue() + "\n");
					Label Xname = new Label();
					Xname.setStyle("-fx-text-fill: black;-fx-font-size: 20px;-fx-font-weight: bold;");
					Label Oname = new Label();
					Oname.setStyle("-fx-text-fill: black;-fx-font-size: 20px;-fx-font-weight: bold;");
					if (youCB.getValue() == 'X') {
						Xname.setText("You");
						Oname.setText("Computer");
					} else {
						Xname.setText("Computer");
						Oname.setText("You");
					}
					XscourL.setStyle("-fx-text-fill: black;-fx-font-size: 30px;-fx-font-weight: bold;");
					OscourL.setStyle("-fx-text-fill: black;-fx-font-size: 30px;-fx-font-weight: bold;");
					roundL.setStyle("-fx-text-fill: black;-fx-font-size: 50px;-fx-font-weight: bold;");
					Label possibility = new Label();
					possibility.setStyle("-fx-text-fill: black;-fx-font-size: 20px;-fx-font-weight: bold;");
					HBox hb1 = new HBox(roundL);
					hb1.setAlignment(Pos.CENTER);
					HBox hb2 = new HBox(possibility);
					hb2.setAlignment(Pos.CENTER);
					VBox Xvb = new VBox();
					Xvb.getChildren().addAll(oneXIV, Xname, XscourL);
					Xvb.setSpacing(10);
					Xvb.setAlignment(Pos.CENTER);
					VBox Ovb = new VBox();
					Ovb.getChildren().addAll(oneOIV, Oname, OscourL);
					Ovb.setSpacing(10);
					Ovb.setAlignment(Pos.CENTER);
					Image XO = new Image(getClass().getResourceAsStream("/resources/XO.png"));
					ImageView XOIV = new ImageView(XO);
					pb.setRight(Xvb);
					pb.setLeft(Ovb);
					pb.setBottom(hb1);
					Pane p = new Pane();
					p.setStyle("-fx-background-color: transparent;");
					p.setPrefSize(600, 600);
					p.getChildren().addAll(XOIV);
					OnePersonMinMaxGame1 gameMinMax1 = new OnePersonMinMaxGame1(primaryStage, labels, roundsCB.getValue(),youCB.getValue(),
							startCB.getValue() == youCB.getValue(), roundL, XscourL, OscourL,possibility);
					OnePersonRandomeGame game = new OnePersonRandomeGame(primaryStage, labels, roundsCB.getValue(),
							startCB.getValue(), youCB.getValue(), roundL, XscourL, OscourL);
					OnePersonMinMaxGame gameMinMax = new OnePersonMinMaxGame(primaryStage, labels, roundsCB.getValue(),youCB.getValue(),
							startCB.getValue() == youCB.getValue(), roundL, XscourL, OscourL);
				
					if (typeCB.getValue() == "Random") {
						for (int row = 0; row < 3; row++) {
							for (int col = 0; col < 3; col++) {
								labels[row][col] = game.createLabel1(' ', col * 200, row * 200);
								p.getChildren().add(labels[row][col]);
							}
						}
						game.startGame();
					} else if (without.isSelected()) {
						for (int row = 0; row < 3; row++) {
							for (int col = 0; col < 3; col++) {
								labels[row][col] = gameMinMax.createLabel(' ', col * 200, row * 200);
								p.getChildren().add(labels[row][col]);
							}
						}
						gameMinMax.startGame();

					}
					else {
						pb.setTop(hb2);
						for (int row = 0; row < 3; row++) {
							for (int col = 0; col < 3; col++) {
								labels[row][col] = gameMinMax1.createLabel(' ', col * 200, row * 200);
								p.getChildren().add(labels[row][col]);
							}
						}
						gameMinMax1.startGame();
						
					}
					Scene scene = new Scene(p, 600, 600);
					primaryStage.setScene(scene);
					primaryStage.show();

					// Helper method to create labels
					pb.setCenter(p);
					primaryStage.setScene(s);

				});
				Pvb.getChildren().addAll(youHB, roundsHB, startHB, GtybeHB, startBT);
				Pvb.setSpacing(40);
				Pvb.setAlignment(Pos.CENTER);
				root.setCenter(Pvb);

			});
			// twoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			VBox vb = new VBox();
			twoIV.setOnMouseClicked(m -> {
				root.getChildren().removeAll(titel, oneIV, twoIV);
				TextField playerX = new TextField();
				playerX.setStyle("-fx-text-fill: black; -fx-background-color: #C7C29C;-fx-font-size: 20px;");
				TextField playerO = new TextField();
				playerO.setStyle("-fx-text-fill: black; -fx-background-color: #C7C29C;-fx-font-size: 20px;");
				Label rounds = new Label("Number of rounds: ");
				rounds.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
				Label startWith = new Label("Start with: ");
				startWith.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
				HBox xHB = new HBox();
				xHB.getChildren().addAll(oneXIV, playerX);
				xHB.setSpacing(10);
				xHB.setAlignment(Pos.CENTER);
				HBox oHB = new HBox();
				oHB.getChildren().addAll(oneOIV, playerO);
				oHB.setSpacing(10);
				oHB.setAlignment(Pos.CENTER);
				HBox roundsHB = new HBox();
				roundsHB.getChildren().addAll(rounds, roundsCB);
				roundsHB.setSpacing(10);
				roundsHB.setAlignment(Pos.CENTER);
				HBox startHB = new HBox();
				startHB.getChildren().addAll(startWith, startCB);
				startHB.setSpacing(10);
				startHB.setAlignment(Pos.CENTER);
				VBox Pvb = new VBox();
				Button startBT = new Button("Start Game");
				startBT.setStyle(
						"-fx-border-color: #FBE2CE;-fx-border-width: 2px;-fx-background-color: #C7C29C;-fx-text-fill: #FBE2CE;-fx-font-size: 50px; -fx-font-weight: bold;");
				startBT.setOnAction(o -> {
					vb.getChildren().removeAll(xHB, oHB);
					BorderPane pb = new BorderPane();
					pb.setStyle("-fx-background-color: #C7C29C;");
					Scene s = new Scene(pb, 800, 750);
					Trounds = roundsCB.getValue();
					roundL = new Label(round + "/" + roundsCB.getValue() + "\n");
					turnL = new Label(startCB.getValue().toString() + " turn...");
					turnL.setStyle("-fx-text-fill: black;-fx-font-size: 40px;-fx-font-weight: bold;");
					Label Xname = new Label(playerX.getText());
					Xname.setStyle("-fx-text-fill: black;-fx-font-size: 20px;-fx-font-weight: bold;");
					Label Oname = new Label(playerO.getText());
					Oname.setStyle("-fx-text-fill: black;-fx-font-size: 20px;-fx-font-weight: bold;");
					XscourL.setStyle("-fx-text-fill: black;-fx-font-size: 30px;-fx-font-weight: bold;");
					OscourL.setStyle("-fx-text-fill: black;-fx-font-size: 30px;-fx-font-weight: bold;");
					roundL.setStyle("-fx-text-fill: black;-fx-font-size: 50px;-fx-font-weight: bold;");
					HBox hb1 = new HBox(roundL);
					hb1.setAlignment(Pos.CENTER);
					HBox hb2 = new HBox(turnL);
					hb2.setAlignment(Pos.CENTER);
					VBox Xvb = new VBox();
					Xvb.getChildren().addAll(oneXIV, Xname, XscourL);
					Xvb.setSpacing(10);
					Xvb.setAlignment(Pos.CENTER);
					VBox Ovb = new VBox();
					Ovb.getChildren().addAll(oneOIV, Oname, OscourL);
					Ovb.setSpacing(10);
					Ovb.setAlignment(Pos.CENTER);
					Image XO = new Image(getClass().getResourceAsStream("/resources/XO.png"));
					ImageView XOIV = new ImageView(XO);
					pb.setRight(Xvb);
					pb.setLeft(Ovb);
					pb.setBottom(hb1);
					Pane p = new Pane();
					p.setStyle("-fx-background-color: transparent;");
					p.setPrefSize(600, 600);
					p.getChildren().addAll(XOIV);
					TwoPersonGame object = new TwoPersonGame(primaryStage, labels, roundsCB.getValue(),
							startCB.getValue(), roundL, turnL, XscourL, OscourL);
					object.labels = labels;
					object.cPlayer = cPlayer;
					for (int row = 0; row < 3; row++) {
						for (int col = 0; col < 3; col++) {
							labels[row][col] = object.createLabel(' ', col * 200, row * 200);
							p.getChildren().add(labels[row][col]);
						}
					}
					object.startGame();
					Scene scene = new Scene(p, 600, 600);
					primaryStage.setScene(scene);
					primaryStage.show();

					// Helper method to create labels
					pb.setCenter(p);
					pb.setTop(hb2);
					primaryStage.setScene(s);

				});
				Pvb.getChildren().addAll(xHB, oHB, roundsHB, startHB, startBT);
				Pvb.setSpacing(40);
				Pvb.setAlignment(Pos.CENTER);
				root.setCenter(Pvb);

			});
			vb.setSpacing(80);
			vb.setAlignment(Pos.CENTER);
			vb.getChildren().addAll(titel, oneIV, twoIV);
			Scene scene = new Scene(root, 467, 700);
			root.setCenter(vb);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Tic Tac Toe");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void backGround(Pane p) {
		try {
			BackgroundImage bGI = new BackgroundImage(new Image(getClass().getResourceAsStream("/resources/XO.jpg")),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
					BackgroundSize.DEFAULT);
			Background bGround = new Background(bGI);
			p.setBackground(bGround);
		} catch (Exception e) {
			dialog(AlertType.ERROR, "Sorry, there was an error while uploading the background");
		}

	}

	public void dialog(AlertType t, String s) {
		Alert alert = new Alert(t);
		alert.setTitle("Dialog");
		alert.setHeaderText("");
		alert.setContentText(s);
		alert.showAndWait();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
