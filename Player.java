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
    }
}