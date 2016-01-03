package turkeyworks.com.kamb.Objects;

/*
 * Created by Karl Wiens on 1/2/2016.
 */
public class DeathStrike {
    private String reason;
    private String modifier;

    public DeathStrike(String reason, String modifier) {
        this.reason = reason;
        this.modifier = modifier;
    }

    public String getReason() { return reason; }
    public String getModifier() { return modifier; }
}
