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
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by ISerg on 23.05.2014.
 */
public class GarageSaleListHandler extends DefaultHandler {

    Handler handler = null;
    GarageSaleList list;
    GarageSale sale;
    String _lastElementName = "";
    StringBuilder sb = null;
    Context context;

    GarageSaleListHandler(Context c, Handler progresshandler) {
        this.context = c;
        if (progresshandler != null) {
            this.handler = progresshandler;
            Message msg = new Message();
            msg.what = 0;
            msg.obj = ("Processing List");
            this.handler.sendMessage(msg);
        }
    }

    public GarageSaleList getList() {
        Message msg = new Message();
        msg.what = 0;
        msg.obj = ("Fetching List");
        if (this.handler != null) {
            this.handler.sendMessage(msg);
        }
        return this.list;
    }

    @Override
    public void startDocument() throws SAXException {
        Message msg = new Message();
        msg.what = 0;
        msg.obj = ("Starting Document");
        if (this.handler != null) {
            this.handler.sendMessage(msg);
        }

        // initialize our GarageSaleList object - this will hold our parsed contents
        this.list = new GarageSaleList(this.context);

        // initialize the GarageSale object
        this.sale = new GarageSale();

    }

    @Override
    public void endDocument() throws SAXException {
        Message msg = new Message();
        msg.what = 0;
        msg.obj = ("End of Document");
        if (this.handler != null) {
            this.handler.sendMessage(msg);
        }

    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        try {
            this.sb = new StringBuilder("");

            if (localName.equals("garagesale")) {
                // create a new item

                Message msg = new Message();
                msg.what = 0;
                msg.obj = (localName);
                if (this.handler != null) {
                    this.handler.sendMessage(msg);
                }

                this.sale = new GarageSale();

            }
        } catch (Exception ee) {
            Log.d("GSF:startElement", ee.getStackTrace().toString());
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {

        if (localName.equals("garagesale")) {
            // add our garageSale to the garageSaleList!
            this.list.add(this.sale);
            Message msg = new Message();
            msg.what = 0;
            msg.obj = ("Storing sale # " + this.sale.getId());
            if (this.handler != null) {
                this.handler.sendMessage(msg);
            }

            return;
        }

        if (localName.equals("id")) {
            if (this.sb.toString().indexOf("&") >= 0)
                this.sale.setId(this.sb.toString().replace("&", ""));
            else
                this.sale.setId(this.sb.toString());
            return;
        }
        if (localName.equals("address")) {
            if (this.sb.toString().indexOf("&") >= 0)
                this.sale.setAddress(this.sb.toString().replace("&", ""));
            else
            this.sale.setAddress(this.sb.toString());
            return;
        }
        if (localName.equals("suburb")) {
            if (this.sb.toString().indexOf("&") >= 0)
                this.sale.setSuburb(this.sb.toString().replace("&", ""));
            else
            this.sale.setSuburb(this.sb.toString());
            return;
        }
        if (localName.equals("geocode")) {
            if (this.sb.toString().indexOf("&") >= 0)
                this.sale.setGeocode(this.sb.toString().replace("&", ""));
            else
            this.sale.setGeocode(this.sb.toString());
            return;
        }
        if (localName.equals("description")) {
            if (this.sb.toString().indexOf("&") >= 0)
                this.sale.setDescription(this.sb.toString().replace("&", ""));
            else
                this.sale.setDescription(this.sb.toString());
            return;
        }
        if (localName.equals("date")) {
            if (this.sb.toString().indexOf("&") >= 0)
                this.sale.setDate(this.sb.toString().replace("&", ""));
            else
            this.sale.setDate(this.sb.toString());
            return;
        }
        if (localName.equals("time")) {
            if (this.sb.toString().indexOf("&") >= 0)
                this.sale.setTime(this.sb.toString().replace("&", ""));
            else
            this.sale.setTime(this.sb.toString());
            return;
        }
        if (localName.equals("region")) {
            if (this.sb.toString().indexOf("&") >= 0)
                this.sale.setRegion(this.sb.toString().replace("&", ""));
            else
            this.sale.setRegion(this.sb.toString());
            return;
        }

    }

    @Override
    public void characters(char ch[], int start, int length) {
        String theString = new String(ch, start, length);
        Log.d("GFS", "characters[" + theString + "]");
        this.sb.append(theString);
    }
}

