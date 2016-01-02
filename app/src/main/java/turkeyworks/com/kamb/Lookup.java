package turkeyworks.com.kamb;

/*
 * Created by Karl Wiens on 12/31/2015.
 */
public class Lookup {
    private final static String[][] Edges = {
            {"Troll Blood", "Heal 2 when you lick yourself for a turn."},
            {"Flame Resistant", "Resist 2 fire damage per turn"},
            {"Animal Charm", "Animals don't attack unless you attack them.\n\nSee page 47"}
    };

    private final static String[][] Bogeys = {
            {"Taste Like Baby", "Kobolds want to eat you if you are injured."},
            {"Hungry", "Roll Ego checks if your are carrying food."},
            {"Flammable", "You will catch on fire if you are near fire."}
    };

    public static String getRandomEdge() {
        int roll = Utils.roll(Edges.length) - 1;
        return Edges[roll][0];
    }

    public static String getRandomBogey() {
        int roll = Utils.roll(Bogeys.length) - 1;
        return Bogeys[roll][0];
    }

    public static String getInfo(String s) {
        if (s.equals("Night Vision"))
            return "You can see in the dark as well as the day!";

        // check the edges
        for (String[] edge : Edges) {
            if (edge[0].equals(s)) return edge[1];
        }

        // check the bogeys
        for (String[] bogey : Bogeys) {
            if (bogey[0].equals(s)) return bogey[1];
        }

        return "No information available.";
    }

    public static String getRandomArmour(boolean pickSafe) {
        return "";
    }

    public static String getRandomWeapon(boolean pickSafe) {
        return "";
    }

    public static String getRandomGear(boolean pickSafe) {
        return "";
    }
}
