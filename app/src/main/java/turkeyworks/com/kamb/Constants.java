package turkeyworks.com.kamb;

import turkeyworks.com.kamb.Objects.BaseItem;
import turkeyworks.com.kamb.Objects.Bogey;
import turkeyworks.com.kamb.Objects.Edge;

/*
 * Created by Karl Wiens on 1/3/2016.
 */
public class Constants {

    public final static BaseItem[] defaults = {
            new Edge("Bark Like a Kobold", "Player can make their kobold bark, " +
                    "and remove one die from a difficulty roll!\n\npg 28"),
            new Edge("Kobold Senses", "You can see in the dark, and you can taste smells\n\npg 29"),
            new Bogey("Fearless", "You have no concept of fear, and running away in fear will invoke a Death Cheque\n\npg 29"),
            new Bogey("Tastes Like Kobold", "If you are injured while around animals, they will try to eat you\n\npg 29")
    };

    public final static Edge[] Edges = {
            new Edge("Animal Chum", "Animals won't attack you, unless you attack them\n\npg 31"),
            new Edge("Bouncy", "You can't fall to your death (but you will take damage!)\n\npg 31"),
            new Edge("Extra Padding", "You are round and pudgy, and get more hit points (already added)\n\nSee page 31"),
            new Edge("Red Thumb", "Damage resistance to fire (2 DAM / injury)\n\npg 32"),
            new Edge("Troll Blood", "If you spend one turn licking your wounds, you gain 2 HP\n\npg 32"),
            new Edge("Winning Smile", "Humans won't attack you, so long as you and the kobold you control smile together.\n\npg 32")
    };

    public final static Bogey[] Bogeys = {
            new Bogey("Animal Foe", "Animals will flee from you, or attack if you look at them funny.\n\npg 31"),
            new Bogey("Flammable ", "You start on fire if you get too near fire.\n\npg 31"),
            new Bogey("Foul Smelling", "You stink really bad, and everyone notices.\n\npg 31"),
            new Bogey("Hungry", "You are so hungry, you may eat whatever you are holding.\n\npg 31"),
            new Bogey("In Heat", "You love humping legs of larger creatures as you find them.\n\npg 31"),
            new Bogey("Tastes Like Baby", "If you get injured, kobolds and critters alike will try to eat you.\n\npg 32"),
    };
}
