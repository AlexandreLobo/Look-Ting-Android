package com.ool.lookting.data;

import android.util.Log;

import com.ool.lookting.model.Event;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by arthurthompson on 9/26/13.
 */
public class EventData {

    public static final String BASE_URL = "http://looktingtt.com";
    private static List<Event> events;

    /**
     * This is a supplier of events to the UI
     * @return a list of all events currently stored.
     */
    public static List<Event> getEvents(Boolean refresh){
        if(!refresh && events != null) return events;

        if(!refresh && false){ //events are on file use those
            return null;
        } else {
            events = getRemoteEvents();
            return events;
        }
    }

    private static List<Event> getRemoteEvents(){
        List<Event> tEvents = new ArrayList<Event>();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(BASE_URL+"/Events");
        try{
            HttpResponse httpResponse = httpClient.execute(httpGet);
            String eventsStr = EntityUtils.toString(httpResponse.getEntity());
            JSONArray jEvents = new JSONArray(eventsStr);
            for(int i=0; i<jEvents.length(); i++){
                JSONObject jEvent = jEvents.getJSONObject(i);
                Event event = new Event();
                event.setTitle(jEvent.getString("name"));
                event.setStartDate(jEvent.getLong("startDateMillis"));
                event.setType(jEvent.getString("category"));
                event.setDescription(jEvent.getString("description"));
                event.setPrice(jEvent.getString("price"));
                event.setLocation(jEvent.getString("address"));
                event.setContact(jEvent.getString("contact"));
                String imageUrl = "";
                if(!jEvent.isNull("artwork")){
                    imageUrl = jEvent.getString("artwork");
                }
                event.setArtwork(imageUrl);
                tEvents.add(event);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tEvents;
    }
}
