package turkeyworks.com.kamb.Objects;

import android.content.Context;
import android.widget.TextView;

/*
 * Created by Karl Wiens on 1/2/2016.
 */
public class MyTextView extends TextView {

    private BaseItem item;

    public MyTextView(Context context) {
        super(context);
        this.item = null;
    }

    public MyTextView(Context context, BaseItem item) {
        super(context);
        this.item = item;
    }

    public BaseItem getItem() {return item;}
}
