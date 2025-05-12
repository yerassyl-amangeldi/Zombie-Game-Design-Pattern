import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager {
    private static GameManager instance;
    private List<Player> players;
    private List<Zombie> zombies;
    private List<Item> items;
    private Map map;
    private ScoreManager scoreManager;
    private GameEventManager eventManager;
    private WaveStrategy waveStrategy;
    private int waveNumber;
    private boolean gameOver;

    //private constructor for singleton
    private GameManager() {
        players = new ArrayList<>();
        zombies = new ArrayList<>();
        items = new ArrayList<>();
        scoreManager = ScoreManager.getInstance();
        eventManager = new GameEventManager();
        map = new Map(800, 600);
        waveNumber = 0;
        waveStrategy = new UniformWave();
        gameOver = false;
    }

    //get the single instance
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    //set up the game
    public void initialize() {
        players.clear(); //clear existing players
        zombies.clear();
        items.clear();
        waveNumber = 0;
        gameOver = false;
        startNewWave();
        spawnItem(); //spawn initial item
    }

    //main game loop, updates everything
    public void update() {
        if (gameOver) {
            scoreManager.saveScores();
            System.out.println("Game Over! Final Score: " + scoreManager.getPoints());
            return;
        }

        //every 5 updates, new wave
        if (waveNumber % 5 == 0 && zombies.isEmpty()) {
            waveNumber++;
            startNewWave();
            eventManager.notify(EventType.NEW_WAVE, waveNumber);
        }

        //update players
        for (Player player : players) {
            player.update(players);
            if (player.getHealth() <= 0) {
                gameOver = true;
                eventManager.notify(EventType.GAME_OVER, null);
                return;
            }
            List<Item> itemsToRemove = new ArrayList<>();
            for (Item item : items) {
                if (item.isPickedUp(player)) {
                    item.applyEffect(player);
                    itemsToRemove.add(item);
                    eventManager.notify(EventType.ITEM_PICKED_UP, item);
                }
            }
            items.removeAll(itemsToRemove);
        }

        //update zombies
        List<Zombie> zombiesToRemove = new ArrayList<>();
        for (Zombie zombie : zombies) {
            zombie.update(players);
            if (zombie.getHealth() <= 0) {
                scoreManager.addPoints(zombie.getPointValue());
                zombiesToRemove.add(zombie);
                eventManager.notify(EventType.ZOMBIE_KILLED, zombie);
            }
        }
        zombies.removeAll(zombiesToRemove);

        //20% chance to spawn an item
        if (new Random().nextDouble() < 0.2) {
            spawnItem();
        }

        //print game state to console
        System.out.println("Wave: " + waveNumber + ", Score: " + scoreManager.getPoints());
        for (Player player : players) {
            System.out.println(player.getName() + " HP: " + player.getHealth() + ", Pos: (" + player.getX() + ", " + player.getY() + ")");
        }
        System.out.println("Zombies: " + zombies.size() + ", Items: " + items.size());
    }

    //start a new wave based on wave number
    public void startNewWave() {
        if (waveNumber % 5 == 0 && waveNumber > 0) {
            waveStrategy = new BossWave();
        } else if (waveNumber % 3 == 0) {
            waveStrategy = new ClusterWave();
        } else {
            waveStrategy = new UniformWave();
        }
        waveStrategy.spawnWave();
    }

    //spawn a random health item
    private void spawnItem() {
        double x = new Random().nextDouble() * (map.getWidth() - 20);
        double y = new Random().nextDouble() * (map.getHeight() - 20);
        if (!map.hasObstacle(x, y)) {
            items.add(new HealthItem(x, y));
        }
    }

    // Getters for other classes
    public List<Player> getPlayers() {
        return players;
    }

    public List<Zombie> getZombies() {
        return zombies;
    }

    public Map getMap() {
        return map;
    }

    public List<Item> getItems() {
        return items;
    }

    public int getWaveNumber() {
        return waveNumber;
    }

    public GameEventManager getEventManager() {
        return eventManager;
    }

    public boolean getGameOver() {
        return gameOver;
    }
}