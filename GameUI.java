import javafx.application.Application;
import javafx.stage.Stage;

public class GameUI extends Application {
    @Override
    public void start(Stage primaryStage) {
        GameManager model = GameManager.getInstance();
        model.initialize();

        GameView view = new GameView();
        new GameController(model, view);

        primaryStage.setTitle("ZombieSurvival");
        primaryStage.setScene(view.getScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}