class HealthItem extends Item {
    //health kit
    public HealthItem(double x, double y) {
        super(x, y);
    }

    @Override
    public void update() {
        //items don't move
    }

    //heal player
    @Override
    public void applyEffect(Player player) {
        player.setHealth(Math.min(player.getHealth() + 50, 150));
    }
}

