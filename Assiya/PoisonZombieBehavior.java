class PoisonZombieBehavior implements ZombieBehavior {
    //poison zombie applies poison
    @Override
    public void execute(Zombie zombie, Player target) {
        if (target == null) return;
        double dx = target.getX() - zombie.getX();
        double dy = target.getY() - zombie.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance > 0) {
            zombie.setX(zombie.getX() + (dx / distance) * zombie.getSpeed());
            zombie.setY(zombie.getY() + (dy / distance) * zombie.getSpeed());
        }
        if (distance < 10) {
            zombie.attack(target);
        }
    }
}