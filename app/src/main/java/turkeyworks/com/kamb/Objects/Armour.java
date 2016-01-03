package turkeyworks.com.kamb.Objects;

import turkeyworks.com.kamb.Utils;

/*
 * Created by Karl Wiens on 1/3/2016.
 */
public class Armour extends BaseItem {

    final public static Armour[] SafeArmour = {
            new Armour("Copper Pot", "Looks shiny")
    };

    final public static Armour[] DangerArmour = {
            new Armour("Kite Shield", "Too big to use")
    };

    public Armour(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static Armour getRandomArmour(boolean pickSafe) {
        if (pickSafe) {
            int roll = Utils.roll(SafeArmour.length) - 1;
            return SafeArmour[roll];
        }
        else {
            int roll = Utils.roll(DangerArmour.length) - 1;
            return DangerArmour[roll];
        }
    }
}
