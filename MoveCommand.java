class MoveCommand implements Command {
    private double dx, dy;

    public MoveCommand(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void execute(Player player) {
        player.move(dx, dy);
    }
}