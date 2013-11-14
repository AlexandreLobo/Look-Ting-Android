package com.ool.lookting.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

/**
 * Created by arthurthompson on 9/26/13.
 */
public class Event implements Parcelable{

    private String title;
    private Calendar startDate;
    private String type;
    private String artwork;
    private String description;
    private String price;
    private String contact;
    private String location;

    public Event(){

    }

    public Event(Parcel in){
        readFromParcel(in);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public void setStartDate(long millis){
        startDate = Calendar.getInstance();
        startDate.setTimeInMillis(millis);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArtwork() {
        return artwork;
    }

    public void setArtwork(String artwork) {
        this.artwork = artwork;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getContact() {
        return contact;
    }

    public String getLocation() {
        return location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(title);
        parcel.writeLong(startDate.getTimeInMillis());
        parcel.writeString(type);
        parcel.writeString(artwork);
        parcel.writeString(description);
        parcel.writeString(price);
        parcel.writeString(contact);
        parcel.writeString(location);
    }

    private void readFromParcel(Parcel in){
        title = in.readString();
        if(startDate==null) startDate = Calendar.getInstance();
        startDate.setTimeInMillis(in.readLong());
        type = in.readString();
        artwork = in.readString();
        description = in.readString();
        price = in.readString();
        contact = in.readString();
        location = in.readString();
    }

    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {
                public Event createFromParcel(Parcel in) {
                    return new Event(in);
                }
                public Event[] newArray(int size) {
                    return new Event[size];
                }
            };
}
