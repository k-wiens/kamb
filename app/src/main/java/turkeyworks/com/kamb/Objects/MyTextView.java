package turkeyworks.com.kamb.Objects;

import android.content.Context;
import android.util.AttributeSet;
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

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.item = null;
    }

    public MyTextView(Context context, BaseItem item) {
        super(context);
        this.item = item;
    }

    public void setItem(BaseItem item) {
        this.item = item;
        String text = (item != null) ? item.getName() : "";
        this.setText(text);
    }
    public BaseItem getItem() {return item;}
}
