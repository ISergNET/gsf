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

/**
 * Created by ISerg on 02.06.2014.
 */
public class GS_Settings {

    private SharedPreferences settings = null;
    private SharedPreferences.Editor editor = null;
    private String xmlFile = "api.xml";
    private String serverUrl = "http://garage-sales.co.nz/";

    public GS_Settings(Context context) {
        this.settings = context.getSharedPreferences("PREFS_PRIVATE", Context.MODE_PRIVATE);
        this.editor = this.settings.edit();
    }

    public String getValue(String key, String defaultvalue) {
        if (this.settings == null) {
            return "Unknown";
        }

        return this.settings.getString(key, defaultvalue);
    }

    public void setValue(String key, String value) {
        if (this.editor == null) {
            return;
        }

        this.editor.putString(key, value);

    }

    public String getXmlFile() {
        if (this.settings == null) {
            return "api.xml";
        }

        this.xmlFile = this.settings.getString("xmlFile", "api.xml");
        return this.xmlFile;
    }

    public String getServer() {
        if (this.settings == null) {
            return "http://garage-sales.co.nz/";
        }

        this.serverUrl = this.settings.getString("serverUrl", "http://garage-sales.co.nz/");
        return this.serverUrl;
    }

    public void setXmlFile(String newFile) {
        if (this.editor == null) {
            return;
        }

        this.editor.putString("xmlFile", newFile);
    }

    public void setServer(String serverurl) {
        if (this.editor == null) {
            return;
        }
        this.editor.putString("serverUrl", serverurl);
    }

    public void save() {
        if (this.editor == null) {
            return;
        }
        this.editor.commit();
    }
}

