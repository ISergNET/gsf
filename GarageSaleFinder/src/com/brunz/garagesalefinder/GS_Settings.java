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

import android.content.Context;
import android.content.SharedPreferences;


public class GS_Settings {

    private SharedPreferences settings = null;
    private SharedPreferences.Editor editor = null;
    private String xmlFile = "api.xml";
    private String serverUrl = "http://garage-sales.co.nz/";
    private String distanceToYouLocation = "0.0";

    public GS_Settings(Context context) {
        this.settings = context.getSharedPreferences("PREFS_PRIVATE", Context.MODE_PRIVATE);
        this.editor = this.settings.edit();
    }

    public String getXmlFile() {
        if (this.settings == null) {
            return "api.xml";
        }

        this.xmlFile = this.settings.getString("xmlFile", "api.xml");
        return this.xmlFile;
    }

    public void setXmlFile(String newFile) {
        if (this.editor == null) {
            return;
        }

        this.editor.putString("xmlFile", newFile);
    }

    public String getServer() {
        if (this.settings == null) {
            return "http://garage-sales.co.nz/";
        }

        this.serverUrl = this.settings.getString("serverUrl", "http://garage-sales.co.nz/");
        return this.serverUrl;
    }

    public void setServer(String serverurl) {
        if (this.editor == null) {
            return;
        }
        this.editor.putString("serverUrl", serverurl);
    }

    public String getDistanceToYouLocation() {
        if (this.settings == null) {
            return "0.0";
        }

        this.distanceToYouLocation = this.settings.getString("distanceToYouLocation", "0.0");
        return this.distanceToYouLocation;
    }

    public void setDistanceToYouLocation(String newFile) {
        if (this.editor == null) {
            return;
        }

        this.editor.putString("distanceToYouLocation", newFile);
    }

    public void save() {
        if (this.editor == null) {
            return;
        }
        this.editor.commit();
    }
}

