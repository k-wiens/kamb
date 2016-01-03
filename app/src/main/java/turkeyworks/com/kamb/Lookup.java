package turkeyworks.com.kamb;

/*
 * Created by Karl Wiens on 12/31/2015.
 */
public class Lookup {

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

        return "No information available.";
    }
}
