package uk.co.edgeorgedev.yonderandbeyonddatacapture;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.viewpagerindicator.CirclePageIndicator;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.edgeorgedev.yonderandbeyonddatacapture.classes.Company;
import uk.co.edgeorgedev.yonderandbeyonddatacapture.utilities.FileUtils;
import uk.co.edgeorgedev.yonderandbeyonddatacapture.views.TagGridView;
import uk.co.edgeorgedev.yonderandbeyonddatacapture.views.adapters.AddViewAdapter;
import uk.co.edgeorgedev.yonderandbeyonddatacapture.views.adapters.TagAdapter;

/**
 * Created by edgeorge on 09/02/15.
 */
public class AboutActivity extends ActionBarActivity{

    private static final String COMPANY_INFO = "company_info.json";
    @InjectView(R.id.indicator)
    protected CirclePageIndicator indicator;
    @InjectView(R.id.pager)
    protected ViewPager pager;

    private AddViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.about);

        ButterKnife.inject(this);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.yonder_logo);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        adapter = new AddViewAdapter();
        addViews();
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);

    }

    private void addViews() {

        String json = FileUtils.getAssetContent(this, COMPANY_INFO);
        Gson gson = new Gson();

        Type type =  new TypeToken<ArrayList<Company>>(){}.getType();
        List<Company> companies = gson.fromJson(json, type);

        for(Company comp : companies){
            View view = getLayoutInflater().inflate(R.layout.company_profile, null);
            ImageView company_image = (ImageView) view.findViewById(R.id.company_image);
            TextView company_text = (TextView) view.findViewById(R.id.company_profile);
            TextView company_headline = (TextView) view.findViewById(R.id.company_header_text);
            TextView company_tag = (TextView) view.findViewById(R.id.company_tag);
            TagGridView tag_grid = (TagGridView) view.findViewById(R.id.tag_grid);
            tag_grid.setAdapter(new TagAdapter(this, comp.getTags()));
            company_headline.setText("\""+comp.getHeadline()+"\"");
            company_text.setText(comp.getDescription());
            company_tag.setText(comp.getMainTag().toUpperCase());
            company_image.setImageResource(getResources().getIdentifier(comp.getImage(), "drawable", getPackageName()));
            adapter.addView(view);
        }


    }
}