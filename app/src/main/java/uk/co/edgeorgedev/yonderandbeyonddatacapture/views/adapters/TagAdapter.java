package uk.co.edgeorgedev.yonderandbeyonddatacapture.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import uk.co.edgeorgedev.yonderandbeyonddatacapture.R;

/**
 * Created by edgeorge on 09/02/15.
 */
public class TagAdapter extends BaseAdapter {

    private Context ctx;
    private List<String> tags;

    public TagAdapter(Context ctx, List<String> tags){
        this.ctx = ctx;
        this.tags = tags;
    }

    @Override
    public int getCount() {
        return tags.size();
    }

    @Override
    public Object getItem(int position) {
        return tags.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if(view == null){
            LayoutInflater vi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.tag_view, parent, false);

            TextView textView = (TextView) view.findViewById(R.id.tag_view_text);
            textView.setText(tags.get(position).toUpperCase());

        }

        return view;
    }
}
