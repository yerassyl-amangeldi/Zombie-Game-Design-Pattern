import javafx.animation.AnimationTimer;
import java.util.List;

public class GameController {
    private final GameManager model;
    private final GameView view;
    private final List<Player> players;
    private int currentPlayerIndex;

    public GameController(GameManager model, GameView view) {
        this.model = model;
        this.view = view;
        this.players = model.getPlayers();
        this.currentPlayerIndex = 0;
        setupGame();
        setupInput();
        startGameLoop();
    }

    private void setupGame() {
        PlayerFactory factory = new PlayerFactory();
        players.add(factory.createPlayer("Jack"));
        players.add(factory.createPlayer("Emily"));
    }

    private void setupInput() {
        view.getScene().setOnKeyPressed(event -> {
            Player currentPlayer = players.get(currentPlayerIndex);
            Command command = null;
            switch (event.getCode().toString().toLowerCase()) {
                case "w" -> command = new MoveCommand(0, -currentPlayer.getSpeed());
                case "a" -> command = new MoveCommand(-currentPlayer.getSpeed(), 0);
                case "s" -> command = new MoveCommand(0, currentPlayer.getSpeed());
                case "d" -> command = new MoveCommand(currentPlayer.getSpeed(), 0);
                case "k" -> command = new AttackCommand();
                case "e" -> command = new AbilityCommand();
                case "q" -> System.exit(0);
            }
            if (command != null) {
                command.execute(currentPlayer);
            }
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        });
    }

    private void startGameLoop() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!model.getGameOver()) {
                    model.update();
                    view.renderGame(players, model.getZombies(), model.getItems());
                    view.updateHUD(model.getWaveNumber(), ScoreManager.getInstance().getPoints(), players);
                } else {
                    stop();
                    System.out.println("Game Over!");
                }
            }
        }.start();
    }
}