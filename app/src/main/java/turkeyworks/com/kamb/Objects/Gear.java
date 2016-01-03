package turkeyworks.com.kamb.Objects;

import turkeyworks.com.kamb.Utils;

/*
 * Created by Karl Wiens on 1/3/2016.
 */
public class Gear extends BaseItem {

    final public static Gear[] SafeGear = {
            new Gear("Thieves Tools", "Actually a lead pipe")
    };

    final public static Gear[] DangerGear = {
            new Gear("butane torch", "very fiery")
    };

    public Gear(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static Gear getRandomGear(boolean pickSafe) {
        if (pickSafe) {
            int roll = Utils.roll(SafeGear.length) - 1;
            return SafeGear[roll];
        }
        else {
            int roll = Utils.roll(DangerGear.length) - 1;
            return DangerGear[roll];
        }
    }
}
