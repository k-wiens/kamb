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
        skills.add(new Skill("Bully", "I can bully", "Brawn"));
        skills.add(new Skill("Duel!", "I can bully", "Brawn"));
        skills.add(new Skill("Heft!", "I can bully", "Brawn"));
        skills.add(new Skill("Lift", "I can bully", "Brawn"));
        skills.add(new Skill("Swim", "I can bully", "Brawn"));
        skills.add(new Skill("Wrassle", "I can bully", "Brawn"));

        skills.add(new Skill("Fear!", "I can know things", "Ego"));
        skills.add(new Skill("Lackey!", "I can know things", "Ego"));
        skills.add(new Skill("Sage!", "I can know things", "Ego"));
        skills.add(new Skill("Shoot", "I can know things", "Ego"));
        skills.add(new Skill("Speak Human", "I can know things", "Ego"));
        skills.add(new Skill("Trap", "I can know things", "Ego"));
        skills.add(new Skill("Tinker!", "I can know things", "Ego"));

        skills.add(new Skill("Fast", "I can shoot", "Reflexes"));
        skills.add(new Skill("Hide", "I can shoot", "Reflexes"));
        skills.add(new Skill("Nurture", "I can shoot", "Reflexes"));
        skills.add(new Skill("Ride", "I can shoot", "Reflexes"));
        skills.add(new Skill("Sneak", "I can shoot", "Reflexes"));
        skills.add(new Skill("Steal", "I can shoot", "Reflexes"));
        skills.add(new Skill("Wiggle", "I can shoot", "Reflexes"));

        skills.add(new Skill("Bard", "Speak human wordss", "Extra"));
        skills.add(new Skill("Dungeon", "Speak animal words", "Extra"));
        skills.add(new Skill("Nature", "Speak animal words", "Extra"));
        skills.add(new Skill("Perform", "Speak animal words", "Extra"));
        skills.add(new Skill("Speak Critter", "Speak animal words", "Extra"));
        skills.add(new Skill("Track", "Speak animal words", "Extra"));
        skills.add(new Skill("Trade!", "Speak animal words", "Extra"));

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
