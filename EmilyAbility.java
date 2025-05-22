
class EmilyAbility implements Command {
    @Override
    public void execute(Player player) {
        // emily's ability: faster shooting
        Weapon currentWeapon = player.getWeapon();
        player.setWeapon(new FireRateBoostWeapon(currentWeapon));
public class EmilyAbility implements Ability {
    @Override
    public void execute(Player player) {
        //emily increasing speed to 10%
        int newSpeed = (int)(player.getSpeed() * 1.1);
        player.setSpeed(newSpeed);
        System.out.println(player.getName() + " increased speed to " + newSpeed + " using EmilyAbility!");

    }
}