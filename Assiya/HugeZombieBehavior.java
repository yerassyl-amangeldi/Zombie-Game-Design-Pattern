class HugeZombieBehavior implements ZombieBehavior {
    //huge zombie smashes obstacles
    @Override
    public void execute(Zombie zombie, Player target) {
        if (target == null) return;
        Map map = GameManager.getInstance().getMap();
        double dx = target.getX() - zombie.getX();
        double dy = target.getY() - zombie.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance > 0) {
            double newX = zombie.getX() + (dx / distance) * zombie.getSpeed();
            double newY = zombie.getY() + (dy / distance) * zombie.getSpeed();
            //destroy obstacle if in way
            if (map.hasObstacle(newX, newY)) {
                map.destroyObstacle(newX, newY);
            }
            zombie.setX(newX);
            zombie.setY(newY);
        }
        if (distance < 10) {
            zombie.attack(target);
        }
    }
}