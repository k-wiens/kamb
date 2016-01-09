package turkeyworks.com.kamb.Objects;

import turkeyworks.com.kamb.Utils;

/*
 * Created by Karl Wiens on 1/3/2016.
 */
public class Armour extends BaseItem {

    final public static Armour[] SafeArmour = {
            new Armour("Spare Tire", "Looks shiny", 3),
            new Armour("Small Shield", "Looks shiny", 4),
            new Armour("Leather Vest", "Looks shiny", 3),
            new Armour("Hoodie", "Looks shiny", 2),
            new Armour("Kid's Clothes", "Looks shiny", 2),
            new Armour("Socks", "Looks shiny", 1),
            new Armour("Nekkid!", "Looks shiny", 0)
    };

    final public static Armour[] DangerArmour = {
            new Armour("Bone Mail", "Looks shiny", 8),
            new Armour("Beer Barrel", "Looks shiny", 10),
            new Armour("Colander Helm", "Looks shiny", 8),
            new Armour("Chain Vest", "Looks shiny", 8),
            new Armour("Leather Jacket", "Looks shiny", 6),
            new Armour("Leather Apron", "Looks shiny", 6),
            new Armour("Kite Shield", "Looks shiny", 12)
    };

    private int hp;

    public Armour(String name, String description, int hp) {
        this.name = name;
        this.description = description;
        this.hp = hp;
    }

    public static Armour getRandomSafeArmour(boolean haveSportSkill) {
        int roll = Utils.roll(6);
        if (haveSportSkill) roll--;
        return SafeArmour[roll];
    }

    public static Armour getRandomDangerArmour(boolean haveLiftSkill) {
        int roll = Utils.roll(6);
        if (haveLiftSkill) roll--;
        return DangerArmour[roll];
    }

    public int getHP() { return hp; }
}
