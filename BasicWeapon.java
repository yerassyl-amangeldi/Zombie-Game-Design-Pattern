
class BasicWeapon implements Weapon {
    private int damage;
    private double fireRate;

    //basic weapon
    public BasicWeapon(int damage) {
        this.damage = damage;
        this.fireRate = 1.0; //1 shot per sec

import java.util.List;

public class BasicWeapon implements Weapon {
    private int damage;
    private double fireRate;

    public BasicWeapon(int damage) {
        this.damage = damage;
        this.fireRate = 1.0;
    }

    @Override
    public void shoot() {
        System.out.println("Weapon fired, damage: " + damage);

        Player player = GameManager.getInstance().getPlayers().get(0);
        if (player != null) {
            List<Zombie> zombies = GameManager.getInstance().getZombies();
            Zombie closestZombie = null;
            double minDistance = Double.MAX_VALUE;

            for (Zombie zombie : zombies) {
                double distance = Math.sqrt(Math.pow(zombie.getX() - player.getX(), 2) + Math.pow(zombie.getY() - player.getY(), 2));
                if (distance < minDistance && distance < 100) {
                    minDistance = distance;
                    closestZombie = zombie;
                }
            }

            if (closestZombie != null) {
                closestZombie.takeDamage(damage);
                System.out.println("Weapon dealt " + damage + " damage to a zombie!");
            }
        }
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public double getFireRate() {
        return fireRate;
    }
}