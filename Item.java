public abstract class Item extends Entity {
    //items like health kits
    public Item(double x, double y) {
        super(x, y, 1);
    }

    //check if player picks it up
    public boolean isPickedUp(Player player) {
        double dx = player.getX() - x;
        double dy = player.getY() - y;
        return Math.sqrt(dx * dx + dy * dy) < 20;
    }

    //apply item effect
    public abstract void applyEffect(Player player);
}