import javafx.animation.AnimationTimer;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private final GameManager model;
    private final GameView view;
    private final List<Player> players;
    private int currentPlayerIndex;
    private AnimationTimer gameLoop;

    // Constructor
    public GameController(GameManager model, GameView view) {
        this.model = model;
        this.view = view;
        this.players = new ArrayList<>();
        this.currentPlayerIndex = 0;

        // Show character selection screen
        view.showCharacterSelection();
        view.setCharacterSelectionHandler("Jack", character -> startGame(character));
        view.setCharacterSelectionHandler("Emily", character -> startGame(character));
        view.setCharacterSelectionHandler("Brad", character -> startGame(character));
        view.setRestartHandler(this::restartGame);
    }

    // Start the game with the chosen character
    private void startGame(String character) {
        // Reset game state
        model.initialize();
        players.clear();
        model.getPlayers().clear();

        // Create the chosen player
        PlayerFactory factory = new PlayerFactory();
        if (character.equals("Jack")) {
            players.add(factory.createPlayer("Jack"));
        } else if (character.equals("Emily")) {
            players.add(factory.createPlayer("Emily"));
        } else if (character.equals("Brad")) {
            Player brad = factory.createPlayer("Brad");
            brad.setHealth(120);
            brad.setSpeed(6);
            players.add(brad);
        }

        // Add player to GameManager
        model.getPlayers().addAll(players);

        setupInput();
        view.showGameScreen();
        startGameLoop();
    }

    // Restart the game
    private void restartGame() {
        // Reset everything
        model.initialize();
        players.clear();
        model.getPlayers().clear();
        currentPlayerIndex = 0;
        if (gameLoop != null) {
            gameLoop.stop();
        }

        // Go back to character selection
        view.showCharacterSelection();
    }

    // Set up input handling
    private void setupInput() {
        view.getScene().setOnKeyPressed(event -> {
            if (players == null || players.isEmpty()) return;
            Player currentPlayer = players.get(currentPlayerIndex);
            if (currentPlayer == null) return;
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

    // Start the game loop
    private void startGameLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!model.getGameOver()) {
                    model.update();
                    List<Zombie> zombies = model.getZombies() != null ? model.getZombies() : new ArrayList<>();
                    List<Item> items = model.getItems() != null ? model.getItems() : new ArrayList<>();
                    view.renderGame(players, zombies, items);
                    view.updateHUD(model.getWaveNumber(), ScoreManager.getInstance().getPoints(), players);
                } else {
                    stop();
                    view.showGameOverScreen();
                }
            }
        };
        gameLoop.start();
    }
}