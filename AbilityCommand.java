class AbilityCommand implements Command {
    @Override
    public void execute(Player player) {
        player.useAbility();
    }
}