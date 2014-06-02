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
    GarageSaleList _list;
    GarageSale _job;
    String _lastElementName = "";
    StringBuilder sb = null;
    Context _context;

    GarageSaleListHandler(Context c, Handler progresshandler) {
        this._context = c;
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
        return this._list;
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
        this._list = new GarageSaleList(this._context);

        // initialize the GarageSale object
        this._job = new GarageSale();

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

            if (localName.equals("job")) {
                // create a new item

                Message msg = new Message();
                msg.what = 0;
                msg.obj = (localName);
                if (this.handler != null) {
                    this.handler.sendMessage(msg);
                }

                this._job = new GarageSale();

            }
        } catch (Exception ee) {
            Log.d("CH12:startElement", ee.getStackTrace().toString());
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {

/*
            if (localName.equals("job")) {
                // add our job to the list!
                this._list.add(this._job);
                Message msg = new Message();
                msg.what = 0;
                msg.obj = ("Storing Job # " + this._job.get_jobid());
                if (this.handler != null) {
                    this.handler.sendMessage(msg);
                }

                return;
            }

            if (localName.equals("id")) {
                this._job.set_jobid(this.sb.toString());
                return;
            }
            if (localName.equals("status")) {
                this._job.set_status(this.sb.toString());
                return;
            }
            if (localName.equals("customer")) {
                this._job.set_customer(this.sb.toString());
                return;
            }
            if (localName.equals("address")) {
                this._job.set_address(this.sb.toString());
                return;
            }
            if (localName.equals("city")) {
                this._job.set_city(this.sb.toString());
                return;
            }
            if (localName.equals("state")) {
                this._job.set_state(this.sb.toString());
                return;
            }
            if (localName.equals("zip")) {
                this._job.set_zip(this.sb.toString());
                return;
            }
            if (localName.equals("product")) {
                this._job.set_product(this.sb.toString());
                return;
            }
            if (localName.equals("producturl")) {
                this._job.set_producturl(this.sb.toString());
                return;
            }
            if (localName.equals("comments")) {
                this._job.set_comments(this.sb.toString());
                return;
            }
*/

    }

    @Override
    public void characters(char ch[], int start, int length) {
        String theString = new String(ch, start, length);
        Log.d("CH12", "characters[" + theString + "]");
        this.sb.append(theString);
    }
}

