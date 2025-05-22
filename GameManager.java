import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.paint.Color;

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
    private List<Particle> particles;
    private boolean gameOver;

    private GameManager() {
        players = new ArrayList<>();
        zombies = new ArrayList<>();
        items = new ArrayList<>();
        scoreManager = ScoreManager.getInstance();
        particles = new ArrayList<>();
        eventManager = new GameEventManager();
        map = new Map(800, 600);
        waveNumber = 0;
        waveStrategy = new UniformWave(zombies);
        gameOver = false;
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void initialize() {
        players.clear();
        zombies.clear();
        items.clear();
        waveNumber = 0;
        gameOver = false;
        startNewWave();
        spawnItem();
    }

    public void update() {
        if (gameOver) {
            scoreManager.saveScores();
            System.out.println("Game Over! Final Score: " + scoreManager.getPoints());
            return;
        }

        if (waveNumber % 5 == 0 && zombies.isEmpty()) {
            waveNumber++;
            startNewWave();
            eventManager.notify(EventType.NEW_WAVE, waveNumber);
        }

        for (Player player : players) {
            player.update();
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

        // Обновляем зомби и удаляем мёртвых
        List<Zombie> zombiesToRemove = new ArrayList<>();
        for (Zombie zombie : zombies) {
            zombie.update(players); // Обновляем зомби один раз
            if (zombie.getHealth() <= 0) {
                scoreManager.addPoints(zombie.getPointValue());
                zombiesToRemove.add(zombie);
                eventManager.notify(EventType.ZOMBIE_KILLED, zombie);
                // Добавляем частицы крови
                for (int i = 0; i < 10; i++) {
                    double dx = (new Random().nextDouble() - 0.5) * 5;
                    double dy = (new Random().nextDouble() - 0.5) * 5;
                    particles.add(new Particle(zombie.getX(), zombie.getY(), dx, dy, 1.0, Color.RED));
                }
            }
        }
        zombies.removeAll(zombiesToRemove);

        // 20% шанс спавна предмета
        if (new Random().nextDouble() < 0.2) {
            spawnItem();
        }

        // Обновляем частицы
        particles.removeIf(p -> !p.isAlive());
        for (Particle particle : particles) {
            particle.update();
        }

        // Вывод состояния игры
        System.out.println("Wave: " + waveNumber + ", Score: " + scoreManager.getPoints());
        for (Player player : players) {
            System.out.println(player.getName() + " HP: " + player.getHealth() + ", Pos: (" + player.getX() + ", " + player.getY() + ")");
        }
        System.out.println("Zombies: " + zombies.size() + ", Items: " + items.size());
    }

    public void startNewWave() {
        if (waveNumber % 5 == 0 && waveNumber > 0) {
            waveStrategy = new BossWave(zombies);
        } else if (waveNumber % 3 == 0) {
            waveStrategy = new ClusterWave(zombies);
        } else {
            waveStrategy = new UniformWave(zombies);
        }
        System.out.println("Starting wave " + waveNumber + " with strategy: " + waveStrategy.getClass().getSimpleName());
        waveStrategy.spawnWave();
    }

    private void spawnItem() {
        double x = new Random().nextDouble() * (map.getWidth() - 20);
        double y = new Random().nextDouble() * (map.getHeight() - 20);
        if (!map.hasObstacle(x, y)) {
            items.add(new HealthItem(x, y));
        }
    }

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

    public List<Particle> getParticles() {
        return particles;
    }
}