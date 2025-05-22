
import java.util.*;

class Player extends Entity {
    //player info
    private String name;
    private Weapon weapon;
    private int id;
    private Command ability;
    private double abilityCooldown;
    private double abilityDuration;
    private boolean abilityActive;
    private double speed;
    private double poisonTimer;
    private double poisonDamagePerSecond;
    private boolean isPoisoned;

    //constructor with all the stuff
    public Player(String name, double x, double y, int health, Weapon weapon, int id, Command ability) {
        super(x, y, health);
        this.name = name;
        this.weapon = weapon;
        this.id = id;
        this.ability = ability;
        this.abilityCooldown = 0;
        this.abilityDuration = 0;
        this.abilityActive = false;
        this.speed = 3; //pretty fast
        this.poisonTimer = 0;
        this.poisonDamagePerSecond = 0;
        this.isPoisoned = false;
    }

    //update player state
    @Override
    public void update() {
        //if dead, do nothing
        if (health <= 0) {
            health = 0;
            return;
        }

        //handle poison damage
        if (poisonTimer > 0) {
            poisonTimer -= 1.0 / 60; //assuming 60 fps
            int poisonDamage = (int) (health * poisonDamagePerSecond / 60);
            health = Math.max(0, health - poisonDamage);
            if (poisonTimer <= 0) {
                isPoisoned = false;
            }
        }

        //update ability timers
        if (abilityCooldown > 0) {
            abilityCooldown -= 1.0 / 60;
        }
        if (abilityDuration > 0) {
            abilityDuration -= 1.0 / 60;
            if (abilityDuration <= 0) {
                abilityActive = false;
                resetAbilityEffects();
            }
        }
    }

    //move player
    public void move(double dx, double dy) {
        Map map = GameManager.getInstance().getMap();
        double newX = x + dx * speed;
        double newY = y + dy * speed;
        //check map bounds and obstacles
        if (newX >= 0 && newX <= map.getWidth() - 20 && newY >= 0 && newY <= map.getHeight() - 20 && !map.hasObstacle(newX, newY)) {
            x = newX;
            y = newY;
        }
    }

    //attack nearby zombies
    public void attack() {
        GameManager gameManager = GameManager.getInstance();
        for (Zombie zombie : gameManager.getZombies()) {
            double dx = zombie.getX() - x;
            double dy = zombie.getY() - y;
            double distance = Math.sqrt(dx * dx + dy * dy);
            //attack range is 50
            if (distance < 50) {
                zombie.setHealth(zombie.getHealth() - weapon.getDamage());
            }
        }
    }

    //use special ability
    public void useAbility() {
        //only if not on cooldown
        if (abilityCooldown <= 0 && !abilityActive) {
            ability.execute();
            abilityCooldown = 15; //15 sec cooldown
            abilityDuration = 5; //5 sec duration
            abilityActive = true;
        }
    }

    //apply poison effect
    public void applyPoison(double duration, double damagePerSecond) {
        poisonTimer = duration;
        poisonDamagePerSecond = damagePerSecond;
        isPoisoned = true;
    }

    //reset ability effects
    private void resetAbilityEffects() {
        if (name.equals("Jack")) {
            weapon = new BasicWeapon(10);
        } else if (name.equals("Emily")) {
            weapon = new BasicWeapon(15);
        } else if (name.equals("Brad")) {
            weapon = new BasicWeapon(8);
        }
    }

    //getters
    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isPoisoned() {
        return isPoisoned;
=======
import javafx.scene.paint.Color;

public class Player {
    private String name;
    private double x, y;
    private int health;
    private int speed;
    private Weapon weapon;
    private int layer;
    private Ability ability;
    private double targetX, targetY;
    private double smoothedTargetX, smoothedTargetY;
    private boolean moving;
    private double velocityX, velocityY;
    private Color color;

    public Player(String name, int x, int y, int speed, Weapon weapon, int layer, Ability ability) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.health = 100;
        this.weapon = weapon;
        this.layer = layer;
        this.ability = ability;
        this.targetX = x;
        this.targetY = y;
        this.smoothedTargetX = x;
        this.smoothedTargetY = y;
        this.moving = false;
        this.velocityX = 0;
        this.velocityY = 0;
    }

    public void setTarget(double targetX, double targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
        this.moving = true;
    }

    public void update() {
        if (!moving) {
            velocityX *= 0.9; //speed decrease
            velocityY *= 0.9;
            if (Math.abs(velocityX) < 0.1) velocityX = 0;
            if (Math.abs(velocityY) < 0.1) velocityY = 0;
        }

        //smooth position
        double smoothingFactor = 0.1; //for better smooth
        smoothedTargetX += (targetX - smoothedTargetX) * smoothingFactor;
        smoothedTargetY += (targetY - smoothedTargetY) * smoothingFactor;

        double dx = smoothedTargetX - x;
        double dy = smoothedTargetY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < 5) {
            x = smoothedTargetX;
            y = smoothedTargetY;
            moving = false;
            return;
        }

        //smooth speed change
        if (distance > 0) {
            double maxVelocity = speed * 0.05;
            velocityX += ((dx / distance) * maxVelocity - velocityX) * 0.05;
            velocityY += ((dy / distance) * maxVelocity - velocityY) * 0.05;
        }

        //update position
        x += velocityX;
        y += velocityY;

        x = Math.max(0, Math.min(800, x));
        y = Math.max(0, Math.min(600, y));

        GameManager.getInstance().getParticles().add(
                new Particle(x, y, 0, 0, 0.5, Color.CYAN)
        );

    }

    public void attack() {
        if (weapon != null) {
            weapon.shoot();
            System.out.println(name + " attacked with " + weapon.getDamage() + " damage!");
        }
    }

    public void useAbility() {
        if (ability != null) {
            ability.execute(this);
        } else {
            System.out.println(name + " has no ability to use!");
        }
    }

    public String getName() {
        return name;
    }
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x; }
    public double getY() { return y;
    }
    public void setY(double y) {
        this.y = y;
    }
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public void takeDamage(int damage) {

    }
}