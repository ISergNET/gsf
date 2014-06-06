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
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class RefreshSales extends Activity {

    GS_Settings settings = null;
    Boolean bCancel = false;
    GarageSaleList mList = null;
    ProgressDialog myprogress;
    Handler progresshandler;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.refresh_sales);

        this.settings = new GS_Settings(getApplicationContext());

        this.myprogress = ProgressDialog.show(this, "Refreshing Sale List", "Please Wait", true, false);

        // install handler for processing gui update messages
        this.progresshandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                // process incoming messages here
                switch (msg.what) {
                    case 0:
                        // update progress bar
                        RefreshSales.this.myprogress.setMessage("" + (String) msg.obj);
                        break;
                    case 1:
                        RefreshSales.this.myprogress.cancel();
                        finish();
                        break;
                    case 2: // error occurred
                        RefreshSales.this.myprogress.cancel();
                        finish();
                        break;
                }
                // super.handleMessage(msg);
            }
        };

        Thread workthread = new Thread(new DoReadSales());

        workthread.start();

    }

    class DoReadSales implements Runnable {

        public void run() {
            InputSource is = null;

            // set up our message - used to convey progress information
            Message msg = new Message();
            msg.what = 0;

            try {
                // Looper.prepare();

                msg.obj = ("Connecting ...");
                RefreshSales.this.progresshandler.sendMessage(msg);
                URL url = new URL(RefreshSales.this.settings.getServer()
                        + RefreshSales.this.settings.getXmlFile());
                // get our data via the url class
                is = new InputSource(url.openStream());

                // create the factory
                SAXParserFactory factory = SAXParserFactory.newInstance();

                // create a parser
                SAXParser parser = factory.newSAXParser();

                // create the reader (scanner)
                XMLReader xmlreader = parser.getXMLReader();

                // instantiate our handler
                GarageSaleListHandler gslHandler = new GarageSaleListHandler(getApplication().getApplicationContext(),
                        RefreshSales.this.progresshandler);

                // assign our handler
                xmlreader.setContentHandler(gslHandler);

                msg = new Message();
                msg.what = 0;
                msg.obj = ("Parsing ...");
                RefreshSales.this.progresshandler.sendMessage(msg);

                // perform the synchronous parse
                xmlreader.parse(is);

                msg = new Message();
                msg.what = 0;
                msg.obj = ("Parsing Complete");
                RefreshSales.this.progresshandler.sendMessage(msg);

                msg = new Message();
                msg.what = 0;
                msg.obj = ("Saving Sale List");
                RefreshSales.this.progresshandler.sendMessage(msg);

                gslHandler.getList().persist();

                msg = new Message();
                msg.what = 0;
                msg.obj = ("Sale List Saved.");
                RefreshSales.this.progresshandler.sendMessage(msg);

                msg = new Message();
                msg.what = 1;
                RefreshSales.this.progresshandler.sendMessage(msg);

            } catch (Exception e) {
                Log.d("GFS", "Exception: " + e.getMessage());
                msg = new Message();
                msg.what = 2; // error occured
                msg.obj = ("Caught an error retrieving Sale data: " + e.getMessage());
                RefreshSales.this.progresshandler.sendMessage(msg);

            }
        }
    }

}

