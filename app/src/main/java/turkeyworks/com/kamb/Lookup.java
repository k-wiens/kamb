package turkeyworks.com.kamb;

/*
 * Created by Karl Wiens on 12/31/2015.
 */
public class Lookup {
    private final static String[][] Edges = {
            {"Troll Blood", "Heal 2 when you lick yourself for a turn."},
            {"Flame Resistant", "Resist 2 fire damage per turn"},
            {"Animal Charm", "Animals don't attack unless you attack them."}
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
}
