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

/*
 * showsettings.java Unlocking Android http://manning.com/ableson Author: W. F. Ableson
 * fableson@msiservices.com
 */

package com.brunz.garagesalefinder;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ShowSettings extends Activity {

    GS_Settings settings = null;

    final String tag = "GFS:ShowSettings";

    AlertDialog.Builder adb;// = new AlertDialog.Builder(this);

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.showsettings);

        this.settings = new GS_Settings(getApplicationContext());

        // load screen
        PopulateScreen();

        this.adb = new AlertDialog.Builder(this);

        final Button savebutton = (Button) findViewById(R.id.settingssave);

        // create anonymous click listener to handle the "save"
        savebutton.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                try {

                    // get the string and do something with it.

                    final EditText editDistance = (EditText) findViewById(R.id.editDistance);
                    if (editDistance.getText().length() == 0) {

                        AlertDialog ad = ShowSettings.this.adb.create();
                        ad.setMessage("Please Enter amount of kilometres to your location (Distance)");
                        ad.show();
                        return;
                    }


                    // save off values
                    ShowSettings.this.settings.setDistanceToYouLocation(editDistance.getText().toString());
                    ShowSettings.this.settings.save();

                    // we're done!
                    finish();
                } catch (Exception e) {
                    Log.i(ShowSettings.this.tag, "Failed to Save Settings [" + e.getMessage() + "]");
                }
            }
        });
    }

    private void PopulateScreen() {
        try {
            final EditText editDistance = (EditText) findViewById(R.id.editDistance);

            editDistance.setText(this.settings.getDistanceToYouLocation());
        } catch (Exception e) {

        }
    }
}
