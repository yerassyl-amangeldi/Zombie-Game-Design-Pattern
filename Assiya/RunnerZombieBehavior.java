class RunnerZombieBehavior implements ZombieBehavior {
    //runner tries to dodge obstacles
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
            //slow down if obstacle
            if (!map.hasObstacle(newX, newY)) {
                zombie.setX(newX);
                zombie.setY(newY);
            } else {
                zombie.setX(zombie.getX() + (dx / distance) * zombie.getSpeed() * 0.5);
                zombie.setY(zombie.getY() + (dy / distance) * zombie.getSpeed() * 0.5);
            }
        }
        if (distance < 10) {
            zombie.attack(target);
        }
    }
}