package com.ool.lookting;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ool.lookting.data.EventData;
import com.ool.lookting.model.Event;
import com.ool.lookting.util.BitmapManager;
import com.ool.lookting.util.LookTingUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class TingActivity extends ActionBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ting_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        int pos = intent.getIntExtra("pos",0);
        ViewPager tingPager = (ViewPager) findViewById(R.id.tingPager);
        TingPagerAdapter tingPagerAdapter = new TingPagerAdapter(getSupportFragmentManager(), EventData.getEvents(false));
        tingPager.setAdapter(tingPagerAdapter);
        tingPager.setCurrentItem(pos);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ting, menu);
        return true;
    }

    static class TingPagerAdapter extends FragmentStatePagerAdapter{

        List<Event> events;

        public TingPagerAdapter(FragmentManager fm, List<Event> events) {
            super(fm);
            this.events = events;
        }

        public void setEvents(List<Event> events){
            this.events = events;
            this.notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int i) {
            return new TingFragment(events.get(i));
        }

        @Override
        public int getCount() {
            return events.size();
        }
    }

    public static class TingFragment extends Fragment{

        Event event;

        public TingFragment(){}

        public TingFragment(Event event){
            this.event = event;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            if(savedInstanceState!=null && savedInstanceState.containsKey("event")) event = savedInstanceState.getParcelable("event");
            // Inflate the layout for this fragment
            View tingView = inflater.inflate(R.layout.ting_frag_layout, container, false);
            TextView title = (TextView) tingView.findViewById(R.id.title);
            title.setText(event.getTitle());
            TextView eventDate = (TextView) tingView.findViewById(R.id.date);
            eventDate.setText(LookTingUtil.getFormattedDate(event.getStartDate()));
            TextView description = (TextView) tingView.findViewById(R.id.description);
            description.setText(event.getDescription());
            TextView price = (TextView) tingView.findViewById(R.id.price);
            price.setText(event.getPrice());
            TextView location = (TextView) tingView.findViewById(R.id.location);
            location.setText(event.getLocation());
            TextView contact = (TextView) tingView.findViewById(R.id.contact);
            contact.setText(event.getContact());
            TextView type = (TextView) tingView.findViewById(R.id.type);
            type.setText(event.getType());
            if(event.getArtwork().compareTo("")!=0){
                try {
                    ImageView artwork = (ImageView) tingView.findViewById(R.id.artwork);
                    String imageUrl = EventData.BASE_URL+"/artwork/"+URLEncoder.encode(event.getArtwork(),"utf-8");
                    BitmapManager.INSTANCE.loadBitmap(imageUrl, artwork, -1, -1);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            return tingView;
        }

        public void onSaveInstanceState(Bundle bundle){
            bundle.putParcelable("event",event);
        }

    }

}
