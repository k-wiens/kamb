package turkeyworks.com.kamb.Objects;

import android.util.Log;

import java.util.ArrayList;

import turkeyworks.com.kamb.Utils;

/*
 * Created by Karl Wiens on 1/2/2016.
 */
public class Skill extends BaseItem {

    private String ability;

    public Skill(String name, String description, String ability) {
        this.name = name;
        this.description = description;
        this.ability = ability;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getAbility() { return ability; }

    private static Skill cookSkill = new Skill("Cook", "I can cook", "Extra");
    private static ArrayList<Skill> skills;

    public static void init(boolean takeCook) {
        skills = new ArrayList<>();

        skills.add(new Skill("Sport", "Some Kobolds are just naturally more athletic than others. \n\n" +
                "Kobolds with the SPORT skill can leap, climb, vault, and perform any \n" +
                "number of extreme physical activities.\n\nThe DIFFICULTY of SPORT rolls \n" +
                "is determined by the Mayor.", "Brawn"));

        skills.add(new Skill("Shoot", "I can shoot", "Reflexes"));
        skills.add(new Skill("Bully", "I can bully", "Brawn"));
        skills.add(new Skill("Sage", "I can know things", "Ego"));

        skills.add(new Skill("Shoot", "I can shoot", "Reflexes"));
        skills.add(new Skill("Bully", "I can bully", "Brawn"));
        skills.add(new Skill("Sage", "I can know things", "Ego"));

        skills.add(new Skill("Speak Human", "Speak human wordss", "Extra"));
        skills.add(new Skill("Speak Critter", "Speak animal words", "Extra"));

        if (!takeCook)
            skills.add(cookSkill);
    }

    public static Skill getRandomSkill (String category) {

        int count = 0;
        for (Skill s : skills) {
            if (s.getAbility().equals(category))
                count++;
        }
        int roll = Utils.roll(count);

        Log.e("Skill", "Count: " + count);
        Log.e("Skill", "Roll: " + roll);
        count = 0;

        for (Skill s : skills) {
            if (s.getAbility().equals(category))
                count++;
            if (count == roll) {
                skills.remove(s);
                return s;
            }
        }

        Log.e("Skill", "shouldn't get here: " + category);
        return null;
    }

    public static Skill getRandomSkill () {
        int roll = Utils.roll(skills.size()) - 1;
        Skill result = skills.get(roll);
        skills.remove(result);
        return result;
    }

    public static Skill getCookSkill() {
        return cookSkill;
    }
}
