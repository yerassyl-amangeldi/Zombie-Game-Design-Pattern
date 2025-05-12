import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class GameView {
    private final Pane gamePane;
    private final Label waveLabel;
    private final Label scoreLabel;
    private final Label hpLabel;
    private final Scene scene;
    private final List<Circle> playerCircles = new ArrayList<>();
    private final List<Circle> zombieCircles = new ArrayList<>();
    private final List<Rectangle> itemRectangles = new ArrayList<>();

    public GameView() {
        // Setup UI
        gamePane = new Pane();
        gamePane.setPrefSize(600, 600);

        // HUD
        waveLabel = new Label("Wave: 0");
        scoreLabel = new Label("Score: 0");
        hpLabel = new Label("HP: N/A");

        VBox hud = new VBox(10, waveLabel, scoreLabel, hpLabel);
        VBox root = new VBox(hud, gamePane);
        scene = new Scene(root, 600, 650);
    }

    public Scene getScene() {
        return scene;
    }

    public void updateHUD(int wave, int score, List<Player> players) {
        waveLabel.setText("Wave: " + wave);
        scoreLabel.setText("Score: " + score);
        hpLabel.setText("HP: Jack (" + players.get(0).getHealth() + "), Emily (" + players.get(1).getHealth() + ")");
    }

    public void renderGame(List<Player> players, List<Zombie> zombies, List<Item> items) {
        // Clear only if necessary (optimization)
        if (playerCircles.size() != players.size() || zombieCircles.size() != zombies.size() || itemRectangles.size() != items.size()) {
            gamePane.getChildren().clear();
            playerCircles.clear();
            zombieCircles.clear();
            itemRectangles.clear();
        }

        // Render players
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            Circle circle;
            if (i >= playerCircles.size()) {
                circle = new Circle(player.getX(), player.getY(), 10, Color.BLUE);
                playerCircles.add(circle);
                gamePane.getChildren().add(circle);
            } else {
                circle = playerCircles.get(i);
                circle.setCenterX(player.getX());
                circle.setCenterY(player.getY());
            }
        }

        // Render zombies
        for (int i = 0; i < zombies.size(); i++) {
            Zombie zombie = zombies.get(i);
            Circle circle;
            if (i >= zombieCircles.size()) {
                circle = new Circle(zombie.getX(), zombie.getY(), 8, Color.RED);
                zombieCircles.add(circle);
                gamePane.getChildren().add(circle);
            } else {
                circle = zombieCircles.get(i);
                circle.setCenterX(zombie.getX());
                circle.setCenterY(zombie.getY());
            }
        }

        // Render items
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            Rectangle rect;
            if (i >= itemRectangles.size()) {
                rect = new Rectangle(item.getX(), item.getY(), 10, 10);
                rect.setFill(Color.GREEN);
                itemRectangles.add(rect);
                gamePane.getChildren().add(rect);
            } else {
                rect = itemRectangles.get(i);
                rect.setX(item.getX());
                rect.setY(item.getY());
            }
        }
    }
}