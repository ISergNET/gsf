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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    final int ACTIVITY_REFRESHSALES = 1;
    final int ACTIVITY_LISTSALES = 2;
    final int ACTIVITY_SETTINGS = 3;

    final String tag = "GSF:Main";

    GS_Settings settings = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		}


    @Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
        if (id == R.id.action_refreshsales) {
            try {
                startActivityForResult(new Intent(this.getBaseContext(), RefreshSales.class),
                        this.ACTIVITY_REFRESHSALES);
            } catch (Exception e) {
                Log.i(this.tag, "Failed to refresh sales [" + e.getMessage() + "]");
            }
            return true;
        }
        if (id == R.id.action_viewsales) {
            try {
                // Perform action on click
                startActivityForResult(new Intent(this.getBaseContext(), ViewSales.class),
                        this.ACTIVITY_LISTSALES);
            } catch (Exception e) {
                Log.i(this.tag, "Failed to list sales [" + e.getMessage() + "]");
            }
            return true;
        }
        if (id == R.id.action_settings) {
            try {
                // Perform action on click
                startActivityForResult(new Intent(this.getBaseContext(), ShowSettings.class),
                        this.ACTIVITY_SETTINGS);
            } catch (Exception e) {
                Log.i(this.tag, "Failed to Launch Settings [" + e.getMessage() + "]");
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
	}


}
