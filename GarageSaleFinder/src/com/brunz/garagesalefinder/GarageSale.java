/*
 * ISCG7424 – Mobile Software Development
 * Assignment 3: Garage Sales Finder
 * Parts: All
 * Student: Paul Shalley		(ID:1402195)
 * Student: Renato De Mendonca	(ID:1422497)
 * Student: Sergey Seriakov 	(ID:1405156)
 * Teacher: Dr. John Casey
 * 2014.
 */

package com.brunz.garagesalefinder;

import android.os.Bundle;


public class GarageSale {
    String id;
    String address;
    String suburb;
    String geocode;
    String description;
    String date;
    String time;
    String region;

    GarageSale() {
    }

    public String toString() {
        return address + "\n" + suburb + ", " + region;
    }

    public String toXMLString() {
        StringBuilder xs = new StringBuilder("");
        xs.append("<garagesale>");
        xs.append("<id>" + id + "</id>");
        xs.append("<address>" + address + "</address>");
        xs.append("<suburb>" + suburb + "</suburb>");
        xs.append("<geocode>" + geocode + "</geocode>");
        xs.append("<description>" + description + "</description>");
        xs.append("<date>" + date + "</date>");
        xs.append("<time>" + time + "</time>");
        xs.append("<region>" + region + "</region>");
        xs.append("</garagesale>");
        return xs.toString() + "/n";
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSuburb() {
        return this.suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getGeocode() {
        return this.geocode;
    }

    public void setGeocode(String geocode) {
        this.geocode = geocode;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Bundle toBundle() {
        Bundle b = new Bundle();
        b.putString("id", this.id);
        b.putString("address", this.address);
        b.putString("suburb", this.suburb);
        b.putString("geocode", this.geocode);
        b.putString("description", this.description);
        b.putString("date", this.date);
        b.putString("time", this.time);
        b.putString("region", this.region);

        return b;
    }

    public static GarageSale fromBundle(Bundle b) {
        GarageSale garageSale = new GarageSale();
        garageSale.setId(b.getString("id"));
        garageSale.setAddress(b.getString("address"));
        garageSale.setSuburb(b.getString("suburb"));
        garageSale.setGeocode(b.getString("geocode"));
        garageSale.setDescription(b.getString("description"));
        garageSale.setDate(b.getString("date"));
        garageSale.setTime(b.getString("time"));
        garageSale.setRegion(b.getString("region"));
        return garageSale;
    }
}
