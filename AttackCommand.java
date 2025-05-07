class AttackCommand implements Command {
    @Override
    public void execute(Player player) {
        player.attack();
    }
}