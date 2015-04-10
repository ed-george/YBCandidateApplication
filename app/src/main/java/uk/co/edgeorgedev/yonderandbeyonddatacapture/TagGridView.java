package uk.co.edgeorgedev.yonderandbeyonddatacapture;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by edgeorge on 09/02/15.
 */
public class TagGridView extends GridView{

    public TagGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagGridView(Context context) {
        super(context);
    }

    public TagGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
