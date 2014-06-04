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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by ISerg on 02.06.2014.
 */
public class ViewSales extends Activity implements AdapterView.OnItemClickListener {

    final int SHOWSALE = 1;
    final int ACTIVITY_REFRESHSALES = 1;

    final String tag = "GSF:ViewSales";
    GS_Settings settings = null;

    GarageSaleList garageSaleList = null;
    ListView saleListView;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.managesales);

        // get our application prefs handle
        this.settings = new GS_Settings(getApplicationContext());

        TextView tv = (TextView) findViewById(R.id.statuslabel);

        this.garageSaleList = GarageSaleList.parse(getApplicationContext());
        if (this.garageSaleList == null) {
            Log.d("GFS", "garageSaleList is null");

            // we need to do this to allow the garageSaleList to have something to display!
            // even though it is empty!
            this.garageSaleList = new GarageSaleList(getApplicationContext());
        }

        if (this.garageSaleList.getCount() == 0) {
            tv.setText("There are No Sales Available");
        } else {
            tv.setText("There are " + this.garageSaleList.getCount() + " sales.");
        }

        // get a reference to the garageSaleList view
        this.saleListView = (ListView) findViewById(R.id.garagelist);

        // setup data adapter
        ArrayAdapter<GarageSale> adapter = new ArrayAdapter<GarageSale>(this, android.R.layout.simple_list_item_1,
                this.garageSaleList.getAll());

        // assign adapter to garageSaleList view
        this.saleListView.setAdapter(adapter);

        // install handler
        this.saleListView.setOnItemClickListener(this);

        // hilight the first entry in the garageSaleList...
        this.saleListView.setSelection(0);

    }

    public void onItemClick(AdapterView parent, View v, int position, long id) {
        GarageSale garageSale = this.garageSaleList.get(position);

        Log.i("GFS", "sale clicked! [" + garageSale.getId() + "]");

        // a sale has been selected, let's get it ready to display
        Intent saleintent = new Intent(v.getContext(), ShowSale.class);

        // use the toBundle() helper method to assist in pushing
        // data across the "Activity" boundary
        Bundle b = garageSale.toBundle();
        // saleintent.putExtra("android.intent.extra.INTENT", b);
        saleintent.putExtras(b);
        // we start this as a "sub" activity, because it may get updated
        // and we need to track that (in the method below OnActivityResult)
        startActivityForResult(saleintent, this.SHOWSALE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SHOWSALE:
                if (resultCode == 1) {
                    Log.d("GFS", "Good Close, let's update our garageSaleList");
                    // pull the SaleEntry out of the bundle
                    Bundle bundle = data.getExtras();
                    GarageSale garageSale = GarageSale.fromBundle(bundle);
                    // update our garageSaleList of sales
                    this.garageSaleList.replace(garageSale);
                }
                break;
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.viewsales, menu);
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
/*
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
*/
        return super.onOptionsItemSelected(item);
    }
}
