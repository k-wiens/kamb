package turkeyworks.com.kamb;

/*
 * Created by Karl Wiens on 12/31/2015.
 */
public class Lookup {

    public static String getRandomEdge() {
        int roll = Utils.roll(Constants.Edges.length) - 1;
        return Constants.Edges[roll][0];
    }

    public static String getRandomBogey() {
        int roll = Utils.roll(Constants.Bogeys.length) - 1;
        return Constants.Bogeys[roll][0];
    }

    // TODO: the info for these should be done similar to skills
    public static String getInfo(String s) {
        if (s.equals("Night Vision"))
            return "You can see in the dark as well as the day!";

        // check the edges
        for (String[] edge : Constants.Edges) {
            if (edge[0].equals(s)) return edge[1];
        }

        // check the bogeys
        for (String[] bogey : Constants.Bogeys) {
            if (bogey[0].equals(s)) return bogey[1];
        }

        for (String[] item : Constants.Armour_Safe) {
            if (item[0].equals(s)) return item[1];
        }
        for (String[] item : Constants.Armour_Danger) {
            if (item[0].equals(s)) return item[1];
        }
        for (String[] item : Constants.Weapon_Safe) {
            if (item[0].equals(s)) return item[1];
        }
        for (String[] item : Constants.Weapon_Danger) {
            if (item[0].equals(s)) return item[1];
        }
        for (String[] item : Constants.Gear_Safe) {
            if (item[0].equals(s)) return item[1];
        }
        for (String[] item : Constants.Gear_Danger) {
            if (item[0].equals(s)) return item[1];
        }

        return "No information available.";
    }

    public static String getRandomArmour(boolean pickSafe) {
        if (pickSafe) {
            int roll = Utils.roll(Constants.Armour_Safe.length) - 1;
            return Constants.Armour_Safe[roll][0];
        }
        else {
            int roll = Utils.roll(Constants.Armour_Danger.length) - 1;
            return Constants.Armour_Danger[roll][0];
        }
    }

    public static String getRandomWeapon(boolean pickSafe) {
        if (pickSafe) {
            int roll = Utils.roll(Constants.Weapon_Safe.length) - 1;
            return Constants.Weapon_Safe[roll][0];
        }
        else {
            int roll = Utils.roll(Constants.Weapon_Danger.length) - 1;
            return Constants.Weapon_Danger[roll][0];
        }
    }

    public static String getRandomGear(boolean pickSafe) {
        if (pickSafe) {
            int roll = Utils.roll(Constants.Gear_Safe.length) - 1;
            return Constants.Gear_Safe[roll][0];
        }
        else {
            int roll = Utils.roll(Constants.Gear_Danger.length) - 1;
            return Constants.Gear_Danger[roll][0];
        }
    }
}
