package com.ool.lookting;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ool.lookting.data.EventData;
import com.ool.lookting.model.Event;
import com.ool.lookting.util.BitmapManager;
import com.ool.lookting.util.LookTingUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class TingsActivity extends ActionBarActivity {

    TingAdapter tingAdapter;
    MenuItem refreshItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.tings_layout);
        //setSupportProgressBarIndeterminate(true);
        //setSupportProgressBarVisibility(true);

        ListView tings = (ListView)findViewById(R.id.tings);
        tings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(),TingActivity.class);
                intent.putExtra("pos",position);
                startActivity(intent);
            }
        });
        tingAdapter = new TingAdapter(this,R.layout.ting_item_layout,new ArrayList<Event>());
        tings.setAdapter(tingAdapter);

        new EventsTask().execute(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tings, menu);
        refreshItem = (MenuItem) menu.findItem(R.id.action_refresh);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_refresh:
                new EventsTask().execute(true);
                setSupportProgressBarIndeterminateVisibility(true);
                refreshItem.setVisible(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class TingAdapter extends ArrayAdapter<Event>{

        public TingAdapter(Context context, int resource, List<Event> events) {
            super(context, resource, events);
        }

        public View getView(int position, View convertView, ViewGroup viewGroup){
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.ting_item_layout, null);
            Event event = getItem(position);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            title.setText(getItem(position).getTitle());
            TextView startDate = (TextView) convertView.findViewById(R.id.startDate);
            startDate.setText(LookTingUtil.getFormattedDate(event.getStartDate()));
            TextView eventType = (TextView) convertView.findViewById(R.id.eventType);
            eventType.setText(event.getType());
            if(event.getArtwork().compareTo("")!=0){
                try {
                    ImageView artwork = (ImageView) convertView.findViewById(R.id.artwork);
                    String imageUrl = EventData.BASE_URL+"/artwork/"+ URLEncoder.encode(event.getArtwork(), "utf-8");
                    BitmapManager.INSTANCE.loadBitmap(imageUrl, artwork, -1, -1);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            return convertView;
        }
    }

    class EventsTask extends AsyncTask<Boolean, Void, List<Event>>{

        @Override
        protected List<Event> doInBackground(Boolean... params) {
            return EventData.getEvents(params[0]);
        }

        @Override
        protected void onPostExecute(List<Event> events){
            tingAdapter.clear();
            for(Event event: events){
                tingAdapter.add(event);
            }
            tingAdapter.notifyDataSetChanged();
            setSupportProgressBarIndeterminateVisibility(false);
            if(refreshItem!=null) refreshItem.setVisible(true);
            //check to see if the loaded data is old, if so refresh it
        }
    }
    
}
