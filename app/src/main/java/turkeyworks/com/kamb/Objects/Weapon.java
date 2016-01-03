package turkeyworks.com.kamb.Objects;

import turkeyworks.com.kamb.Utils;

/*
 * Created by Karl Wiens on 1/3/2016.
 */
public class Weapon extends BaseItem {

    final public static Weapon[] SafeWeapon = {
            new Weapon("Ice Pick", "Good for ice")
    };

    final public static Weapon[] DangerWeapon = {
            new Weapon("Heavy club", "very smashy")
    };

    public Weapon(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static Weapon getRandomWeapon(boolean pickSafe) {
        if (pickSafe) {
            int roll = Utils.roll(SafeWeapon.length) - 1;
            return SafeWeapon[roll];
        }
        else {
            int roll = Utils.roll(DangerWeapon.length) - 1;
            return DangerWeapon[roll];
        }
    }
}
