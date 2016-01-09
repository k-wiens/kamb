package turkeyworks.com.kamb.Objects;

import turkeyworks.com.kamb.Constants;
import turkeyworks.com.kamb.Utils;

/*
 * Created by Karl Wiens on 1/2/2016.
 */
public class Edge extends BaseItem {


    public Edge(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static Edge getRandomEdge() {
        int roll = Utils.roll(Constants.Edges.length) - 1;
        return Constants.Edges[roll];
    }
}
