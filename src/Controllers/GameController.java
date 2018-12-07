package Controllers;

import Views.ChangeWindow;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    private List<Rectangle> recList;

    private BoardLogic boardLogic;

    private int player_now = 1;

    private boolean multiplayer = GameProperties.multiplayer;

    @FXML
    private ImageView boardImage;

    @FXML
    private Text textPlayerWon;

    @FXML
    private Button btnNewGame;

    @FXML
    private AnchorPane ap;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            System.out.println("Multiplayer: " + multiplayer);

            initBoard();
        });
    }

    // Makes the yellow rectangles you see and assigns them the handeInput method.
    public void initBoard() {
        boardLogic = new BoardLogic();

        double boardHeightPX = boardImage.getFitHeight();
        double boardWidthPX = boardImage.getFitWidth();

        double rectangleWidth = boardWidthPX / (int) boardLogic.getWidth();
        double rectangleHeight = boardHeightPX;

        recList = new ArrayList<>();

        for (int i = 0; i < boardLogic.getWidth(); i++) {
            Rectangle r = new Rectangle(rectangleWidth, rectangleHeight);

            r.setTranslateX(i * rectangleWidth);
            r.setFill(Color.rgb(200, 200, 0, 0.45));

            r.setOnMouseEntered(event -> { r.setOpacity(1);});
            r.setOnMouseExited(event -> { r.setOpacity(0.0);});

            final int x = i;

            r.setOnMouseClicked(event -> {
                placePiece(x);
            });

            r.setOpacity(0.0f);

            ap.getChildren().add(r);

            recList.add(r);
        }
    }

    // Places an image on a coordinates.
    private void renderPlacePiece(int x, int y) {
        // het plaatje dat wordt geplaatst wanneer, moet afwisselend rood en groen.
        ImageView iv;
        if (player_now == 1) {
            iv = new ImageView("./img/p/rood.png");
        } else {
            iv = new ImageView("./img/p/groen.png");
        }

        int renderX = (x * 91) + 11;
        int renderY = (y - 1) * 80 + 6;

        iv.setX(renderX);
        iv.setY(0);

        // Animation for piece falling down.
        TranslateTransition translateTransition = new TranslateTransition();

        translateTransition.setDuration(Duration.millis(500));
        translateTransition.setNode(iv);
        translateTransition.setByY(renderY);
        translateTransition.setAutoReverse(false);

        translateTransition.play();

        ap.getChildren().add(0, iv);
    }

    // places a piece on a row
    private boolean placePiece(int x) {
        // logic
        int success = boardLogic.place(x, player_now);

        if (success == -1) {
            // cannot place a piece there.
             return false;
        } else {
            renderPlacePiece(x, success);

            if (success == 1) {
                Rectangle r = recList.get(x);
                r.setFill(Color.rgb(200, 0, 0, 0.7));
            }

            boardLogic.printBoard();
        }

        changePlayer();

        if (!multiplayer && player_now == 2) {
            AiAction();
            System.out.println("AI Action");
        }


        // check if someone won.
        int win = boardLogic.checkWin();

        if (win == 0) {
            endGame("It's a tie!");
        } else if (win == 1) {
            endGame("Player 1 won!");
        } else if (win == 2) {
            if (multiplayer) {
                endGame("Player 2 won!");
            } else {
                endGame("AI won!");
            }
        }

        return true;
    }

    private void changePlayer() {
        if (player_now == 1) {
            player_now = 2;
        } else {
            player_now = 1;
        }
    }

    private void AiAction() {
        int randomInt = new Random().nextInt(7);

        System.out.println("AI placing at row: " + randomInt);

        // if the position where you are trying to place it is full.
        while(!placePiece(randomInt)) {
            System.out.println("Could not place at " + randomInt + ", trying again.");
            randomInt = new Random().nextInt(7);
        }
    }

    private void endGame(String str) {
        textPlayerWon.setVisible(true);
        btnNewGame.setVisible(true);

        textPlayerWon.setText(str);

        disableRect();
    }

    // can't add more pieces when the game is over
    private void disableRect() {
        for (int i = 0; i < recList.size(); i++) {
            recList.get(i).setDisable(true);
        }
    }

    @FXML
    public void onQuitAction(ActionEvent event) {
        ChangeWindow.changeWindow(ChangeWindow.MAIN_MENU, event);
    }

    @FXML
    public void onNewGameAction(ActionEvent event) {
        ChangeWindow.changeWindow(ChangeWindow.BOARD, event);
    }
}