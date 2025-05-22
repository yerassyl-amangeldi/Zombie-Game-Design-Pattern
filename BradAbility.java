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
    }
}