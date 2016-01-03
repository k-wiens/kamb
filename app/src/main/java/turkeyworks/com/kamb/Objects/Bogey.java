package turkeyworks.com.kamb.Objects;

import turkeyworks.com.kamb.Constants;
import turkeyworks.com.kamb.Utils;

/*
 * Created by Karl Wiens on 1/2/2016.
 */
public class Bogey extends BaseItem {

    public Bogey(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static String getRandomBogey() {
        int roll = Utils.roll(Constants.Bogeys.length) - 1;
        return Constants.Bogeys[roll][0];
    }
}
