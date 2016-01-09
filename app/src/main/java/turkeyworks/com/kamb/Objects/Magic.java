package turkeyworks.com.kamb.Objects;

import turkeyworks.com.kamb.Constants;
import turkeyworks.com.kamb.Utils;

/*
 * Created by Karl Wiens on 1/6/2016.
 */
public class Magic extends BaseItem {

        final public static Magic[] Spells = {
                new Magic("Spell of Mostly Unspeakable Horrors", "Summon a tentacle monster\n\n" +
                        "Somatic Component - Player must howl and wail loudly\n\n" +
                        "pg 36"),
                new Magic("Lowerbrau's Wall of Beer", "Create an effervescent wall of beer\n\n" +
                        "Somatic Component - Shout the words \"Tappa tappa kegga, wall o' beer omega\" while doing a jig and pretending to drink beer\n\n" +
                        "pg 35"),
                new Magic("Count Reinhagen's Vampsfucation", "Become invisible, though you can still be smell and heard\n\n" +
                        "Somatic Component - Player must fold their arms\n\n" +
                        "pg 35\n"),
                new Magic("Big Bee's Slapping Hand", "Creates a giant hand\n\n" +
                        "Verbal Component - Who's your [daddy/mommy]?\n\n" +
                        "pg 34"),
                new Magic("Bail's Floating Frying Pan", "Summons a floating frying pan\n\n" +
                        "Verbal Component - Can you smell what The [name] is cooking?\n\n" +
                        "pg 34"),
                new Magic("Restor's Spell of Somnolence", "Causes nearby critters to fall asleep\n\n" +
                        "Somatic Component - Player must yawn mightily\n\n" +
                        "pg 35"),
                new Magic("Tabriz' Ball of Flaming Death", "Shoot a fireball\n\n" +
                        "Somatic Component - Player must point and laugh maniacally\n\n" +
                        "pg 36"),
                new Magic("Lord Elmer's Web of Glue", "Creates of a web of stickiness\n\n" +
                        "Verbal Component - Sing a song about a friendly local spider person\n\n" +
                        "pg 35"),
                new Magic("Sandor's Spell of Summoning Chicken", "Summon a chicken (Get a keen hat!)\n\n" +
                        "Somatic Component - Player must do a bad chicken impression\n\n" +
                        "pg 36"),
                new Magic("Poof!", "Disappear and appear somewhere else\n\n" +
                        "Verbal Component - Say \"Poof1\" (providing sulfur smell optional)\n\n" +
                        "pg 35"),
                new Magic("Summon Horrible Demon", "You have no spell! Pretend that you did cast one, and make that Perform check!")
        };

        public Magic(String name, String description) {
                this.name = name;
                this.description = description;
        }

        public static Magic getRandomMagic() {
                int roll = Utils.roll(Constants.Edges.length) - 1;
                return Spells[roll];
        }
}
