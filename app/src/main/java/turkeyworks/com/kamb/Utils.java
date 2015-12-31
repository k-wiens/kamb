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

    public static int getAbility(int num) {
        if (num >= 9 && num < 13) return 3;
        if (num >= 5 && num < 9)  return 2;
        return 1;
    }
}
