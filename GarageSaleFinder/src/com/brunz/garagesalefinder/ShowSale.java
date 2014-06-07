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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ShowSale extends Activity {

    GS_Settings settings = null;
    GarageSale garageSale = null;
    final int CLOSESALETASK = 1;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.showsale);

        this.settings = new GS_Settings(getApplicationContext());

        StringBuilder sb = new StringBuilder();

        String details = null;

        Intent startingIntent = getIntent();

        if (startingIntent != null) {
            Log.i("GFS::ShowSale", "starting intent not null");
            Bundle b = startingIntent.getExtras();
            if (b == null) {
                Log.i("GFS::ShowSale", "bad bundle");
                details = "bad bundle?";
            } else {
                this.garageSale = GarageSale.fromBundle(b);
                sb.append("Date: " + this.garageSale.getDate() + " Time: " + this.garageSale.getTime() + "\n\n");
                sb.append("Address: " + this.garageSale.getAddress() + "\n");
                sb.append(this.garageSale.getSuburb() + ", " + this.garageSale.getRegion() + "\n\n");
                sb.append("Description:\n" + this.garageSale.getDescription() + "\n\n");
                details = sb.toString();
            }
        } else {
            details = "Sale Information Not Found.";
            TextView tv = (TextView) findViewById(R.id.details);
            tv.setText(details);
            return;
        }

        TextView tv = (TextView) findViewById(R.id.details);
        tv.setText(details);

        Button bmap = (Button) findViewById(R.id.mapsale);

        bmap.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                // clean up data for use in GEO query
                String address = ShowSale.this.garageSale.getAddress() + " " + ShowSale.this.garageSale.getSuburb() + " "
                        + ShowSale.this.garageSale.getRegion();
                String cleanAddress = address.replace(",", "");
                cleanAddress = cleanAddress.replace(' ', '+');
                String locationStr = "geo:" + ShowSale.this.garageSale.getGeocode();

                try {
/*
                    Intent geoIntent = new Intent("android.intent.action.VIEW", android.net.Uri.parse("geo:0,0?q="
                            + cleanAddress));
*/
                    Intent geoIntent = new Intent("android.intent.action.VIEW",
                            android.net.Uri.parse(locationStr + "?q=" + cleanAddress));

                    startActivity(geoIntent);
                } catch (Exception ee) {
                    Log.d("GFS", "error launching map? " + ee.getMessage());
                }

            }
        });

        Button btnEmailListing = (Button) findViewById(R.id.sendEmail);

        btnEmailListing.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String address = ShowSale.this.garageSale.getAddress() + " " + ShowSale.this.garageSale.getSuburb() + " "
                        + ShowSale.this.garageSale.getRegion();
                Intent emailListing = new Intent(Intent.ACTION_SEND);
                emailListing.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
                emailListing.putExtra(Intent.EXTRA_SUBJECT, "Hi my friend! Garage Sale at: ");
                emailListing.putExtra(Intent.EXTRA_TEXT, address);
                emailListing.setType("message/rfc822");
                startActivity(Intent.createChooser(emailListing, "Choose an Email Client :"));
                //In here, we put the code that allows people to email listings.
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CLOSESALETASK:
                if (resultCode == 1) {
                    Log.d("GFS", "Good Close!");
                    // propagate this up to the list activity
                    Intent resultIntent = new Intent();
                    resultIntent.putExtras(data.getExtras());
                    this.setResult(1, resultIntent);
                    // leave this activity
                    finish();
                }
                break;
        }

    }
}

