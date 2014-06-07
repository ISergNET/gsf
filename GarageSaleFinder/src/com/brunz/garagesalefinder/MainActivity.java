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
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends Activity {

    final int ACTIVITY_REFRESHSALES = 1;
    final int ACTIVITY_LISTSALES = 2;
    final int ACTIVITY_SETTINGS = 3;

    final String tag = "GSF:Main";

    GS_Settings settings = null;
    private Geocoder geocoder;
    public static Location location;
    private Boolean startRefresh;

    public MainActivity() {
        super();
        startRefresh = false;
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location loc) {
            location = loc;
            printCoordinates();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        if (settings.getLocationProvider() == "Address"){
            if (settings.getYourLocationAddress().toString().isEmpty()){

            }
        }
*/
        TextView text = (TextView) this.findViewById(R.id.text);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        text.setText("List of location providers:\n");

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_LOW);
        //criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(true);
        String bestProvider = locationManager.getBestProvider(criteria, true);

        List<String> providers = locationManager.getAllProviders();
        for (int i = 0; i < providers.size(); i++) {
            String provider = providers.get(i);
            text.append(provider.toUpperCase() + " is " + locationManager.isProviderEnabled(provider) + "\n");
        }
        TextView prvdView = (TextView) this.findViewById(R.id.prvdView);
        bestProvider = (bestProvider != LocationManager.NETWORK_PROVIDER) ?
                LocationManager.NETWORK_PROVIDER : bestProvider;
        prvdView.setText(bestProvider.toUpperCase());
        locationManager.requestLocationUpdates(bestProvider, 300000, 0, locationListener);
        location = locationManager.getLastKnownLocation(bestProvider);
        geocoder = new Geocoder(getApplicationContext());
        if (location != null) {
            printCoordinates();
        }
        if (!startRefresh) {
            startRefresh = true;
            try {
                // Perform action on click
                startActivityForResult(new Intent(this.getBaseContext(), ViewSales.class),
                        this.ACTIVITY_LISTSALES);
            } catch (Exception e) {
                Log.i(this.tag, "Failed to list sales [" + e.getMessage() + "]");
            }
            try {
                startActivityForResult(new Intent(this.getBaseContext(), RefreshSales.class),
                        this.ACTIVITY_REFRESHSALES);
            } catch (Exception e) {
                Log.i(this.tag, "Failed to refresh sales [" + e.getMessage() + "]");
            }
        }

    }

    private void printCoordinates() {
        TextView cView = (TextView) this.findViewById(R.id.cView);
        cView.setText("\nLatitude: " + location.getLatitude());
        cView.append("\nLongitude: " + location.getLongitude());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null && addresses.size() > 0) {
            EditText editText = (EditText) this.findViewById(R.id.editYourLocationAddress);
            editText.setText(addresses.get(0).toString());
            editText.invalidate();
        }
        cView.invalidate();
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
