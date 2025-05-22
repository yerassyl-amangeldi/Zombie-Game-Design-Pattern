
class JackAbility implements Command {
    @Override
    public void execute(Player player) {
        // jack's ability: more damage
        Weapon currentWeapon = player.getWeapon();
        player.setWeapon(new DamageBoostWeapon(currentWeapon));
public class JackAbility implements Ability {
    @Override
    public void execute(Player player) {
        //jack restore 20 hp
        player.setHealth(player.getHealth() + 20);
        System.out.println(player.getName() + " restored 20 HP using JackAbility!");

    }
}