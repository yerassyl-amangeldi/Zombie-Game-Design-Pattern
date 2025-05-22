
class BradAbility implements Command {
    @Override
    public void execute(Player player) {
        // brad's ability: push zombies away
        GameManager gameManager = GameManager.getInstance();
        for (Zombie zombie : gameManager.getZombies()) {
            double dx = zombie.getX() - player.getX();
            double dy = zombie.getY() - player.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance < 50) {
                zombie.setHealth(zombie.getHealth() - 10); // damage
                if (distance > 0) {
                    zombie.setX(zombie.getX() + (dx / distance) * 50); // push
                    zombie.setY(zombie.getY() + (dy / distance) * 50);
                }
            }
        }

import java.util.List;

public class BradAbility implements Ability {
    @Override
    public void execute(Player player) {
        //brad damaging all zombies in 100 px
        List<Zombie> zombies = GameManager.getInstance().getZombies();
        for (Zombie zombie : zombies) {
            double distance = Math.sqrt(Math.pow(zombie.getX() - player.getX(), 2) + Math.pow(zombie.getY() - player.getY(), 2));
            if (distance < 100) {
                zombie.takeDamage(15); //15 damage
            }
        }
        System.out.println(player.getName() + " dealt 15 damage to nearby zombies using BradAbility!");
    }
}