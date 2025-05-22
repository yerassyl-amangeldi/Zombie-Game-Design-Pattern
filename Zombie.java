import java.util.*;

class Zombie extends Entity {

    private String type;
    private double speed;
    private int damage;
    private int pointValue;
    private ZombieBehavior behavior;
    private boolean isPoisonous;
    private static final double POISON_DURATION = 10.0;
    private static final double POISON_DAMAGE_PER_SECOND = 0.02;


    public Zombie(String type, double x, double y, int health, double speed, int damage, int pointValue, ZombieBehavior behavior, boolean isPoisonous) {
        super(x, y, health);
        this.type = type;
        this.speed = speed;
        this.damage = damage;
        this.pointValue = pointValue;
        this.behavior = behavior;
        this.isPoisonous = isPoisonous;
    }

    //update zombie
    @Override
    public void update() {
        behavior.execute(this, findNearestPlayer());
    }

    //attack a player
    public void attack(Player player) {
        player.setHealth(player.getHealth() - damage);
        //apply poison if needed
        if (isPoisonous) {
            player.applyPoison(POISON_DURATION, POISON_DAMAGE_PER_SECOND);
        }
    }

    public void update(List<Player> players) {
        GameManager gameManager = GameManager.getInstance();
        behavior.execute(this, findNearestPlayer(), gameManager.getZombies());
    }

    public void attack(Player player) {
        player.setHealth(player.getHealth() - damage);
    }

    public Player findNearestPlayer() {
        GameManager gameManager = GameManager.getInstance();
        Player nearest = null;
        double minDistance = Double.MAX_VALUE;
        for (Player player : gameManager.getPlayers()) {
            double dx = player.getX() - x;
            double dy = player.getY() - y;
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance < minDistance) {
                minDistance = distance;
                nearest = player;
            }
        }
        return nearest;
    }

    //getters
    public double getSpeed() {
        return speed;
    }

    public int getPointValue() {
        return pointValue;
    }

    public String getType() {
        return type;
    }

}

    public void takeDamage(int damage) {
        health -= damage;
    }

    public boolean isPoisonous() {
        return isPoisonous;
    }

    // Добавляем геттеры и сеттеры для координат
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}

