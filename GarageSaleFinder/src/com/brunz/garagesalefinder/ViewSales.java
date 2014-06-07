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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ViewSales extends Activity implements AdapterView.OnItemClickListener {

    final int SHOWSALE = 1;

    final String tag = "GSF:ViewSales";
    GS_Settings settings = null;

    GarageSaleList garageSaleList = null;
    ArrayList<GarageSale> saleList = null;
    ListView saleListView;
    EditText searchText = null;
    private static String search = "";

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.managesales);

        // get our application prefs handle
        this.settings = new GS_Settings(getApplicationContext());

        this.garageSaleList = GarageSaleList.parse(getApplicationContext());
        if (this.garageSaleList == null) {
            Log.d("GFS", "garageSaleList is null");

            // we need to do this to allow the garageSaleList to have something to display!
            // even though it is empty!
            this.garageSaleList = new GarageSaleList(getApplicationContext());
        }

        // get a reference to the garageSaleList view
        this.saleListView = (ListView) findViewById(R.id.garagelist);

        searchText = (EditText) findViewById(R.id.searchText);
        searchText.setText(search);

        saleList = new ArrayList<GarageSale>(garageSaleList.getAll());
        if (Double.parseDouble(settings.getDistanceToYouLocation()) > 0) {
            for (int i = saleList.size() - 1; i > -1; i--) {
                GarageSale sale = saleList.get(i);
                if (sale.distance != null) {
                    if (sale.distance > Double.parseDouble(settings.getDistanceToYouLocation()))
                        saleList.remove(i);
                }
            }
        }
        if (search.length() != 0) {
            for (int i = saleList.size() - 1; i > -1; i--) {
                GarageSale sale = saleList.get(i);
                if (sale.description.toUpperCase().indexOf(search.toUpperCase()) < 0)
                    saleList.remove(i);
            }
        }
        // setup data adapter
        ArrayAdapter<GarageSale> adapter = new ArrayAdapter<GarageSale>(this, android.R.layout.simple_list_item_1,
                saleList);

        // assign adapter to garageSaleList view
        this.saleListView.setAdapter(adapter);

        // install handler
        this.saleListView.setOnItemClickListener(this);

        // hilight the first entry in the garageSaleList...
        this.saleListView.setSelection(0);
        saleListView.setActivated(true);

        TextView tv = (TextView) findViewById(R.id.statuslabel);

        if (adapter.getCount() == 0) {
            tv.setText("There are No Sales Available");
        } else {
            tv.setText("There are " + adapter.getCount() + " sales.");
        }
        ImageButton searchButton = (ImageButton) findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {
                searchText = (EditText) findViewById(R.id.searchText);
                search = searchText.getText().toString();
                // get a reference to the garageSaleList view
                saleListView = (ListView) findViewById(R.id.garagelist);
                //String bufSearch = settings.getSearchString();
                saleList = new ArrayList<GarageSale>(garageSaleList.getAll());
                if (Double.parseDouble(settings.getDistanceToYouLocation()) > 0) {
                    for (int i = saleList.size() - 1; i > -1; i--) {
                        GarageSale sale = saleList.get(i);
                        if (sale.distance != null) {
                            if (sale.distance > Double.parseDouble(settings.getDistanceToYouLocation()))
                                saleList.remove(i);
                        }
                    }
                }
                if (search.length() != 0) {
                    for (int i = saleList.size() - 1; i > -1; i--) {
                        GarageSale sale = saleList.get(i);
                        if (sale.description.toUpperCase().indexOf(search.toUpperCase()) < 0)
                            saleList.remove(i);
                    }
                }
                // setup data adapter
                ArrayAdapter<GarageSale> adapter = new ArrayAdapter<GarageSale>(ViewSales.this,
                        android.R.layout.simple_list_item_1,
                        saleList);

                // assign adapter to garageSaleList view
                saleListView.setAdapter(adapter);

                // hilight the first entry in the garageSaleList...
                saleListView.setSelection(0);
                saleListView.setActivated(true);
                TextView tv = (TextView) findViewById(R.id.statuslabel);

                if (adapter.getCount() == 0) {
                    tv.setText("There are No Sales Available");
                } else {
                    tv.setText("There are " + adapter.getCount() + " sales.");
                }
            }
        });
    }

    public void onItemClick(AdapterView parent, View v, int position, long id) {
        GarageSale garageSale = saleList.get(position);

        Log.i("GFS", "sale clicked! [" + garageSale.getId() + "]");

        // a sale has been selected, let's get it ready to display
        Intent saleIntent = new Intent(v.getContext(), ShowSale.class);

        // use the toBundle() helper method to assist in pushing
        // data across the "Activity" boundary
        Bundle b = garageSale.toBundle();
        // saleIntent.putExtra("android.intent.extra.INTENT", b);
        saleIntent.putExtras(b);
        // we start this as a "sub" activity, because it may get updated
        // and we need to track that (in the method below OnActivityResult)
        startActivityForResult(saleIntent, this.SHOWSALE);
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
                this.finishActivity(1);
                startActivityForResult(new Intent(this.getBaseContext(), RefreshSales.class),
                        MainActivity.ACTIVITY_REFRESHSALES);
                startActivityForResult(new Intent(this.getBaseContext(), ViewSales.class),
                        MainActivity.ACTIVITY_LISTSALES);
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
