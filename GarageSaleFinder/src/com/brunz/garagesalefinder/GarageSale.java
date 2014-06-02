package com.brunz.garagesalefinder;

/**
 * Created by ISerg on 23.05.2014.
 */
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

    ;

    public String toString() {
        return id + " " + address + " " + suburb + " " + geocode + " " + description + " " + date + " " + time + " " + region;
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
}
