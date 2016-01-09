package turkeyworks.com.kamb;

import java.util.Random;

/*
 * Created by Karl Wiens on 12/31/2015.
 */
public class Utils {

    // return a random integer between 1 and the given 'num'
    public static int roll(int num) {
        Random rand = new Random();
        return rand.nextInt(num) + 1;
    }

    // easiest way to scale this up012
    public static int getAbility(int num) {
        return (num + 3) / 4;
    }
}
