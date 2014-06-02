package com.brunz.garagesalefinder;

import android.content.Context;
import android.util.Log;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by ISerg on 23.05.2014.
 */

public class GarageSaleList {

    private Context context = null;
    private List<GarageSale> list;

    GarageSaleList(Context c) {
        this.context = c;
        this.list = new Vector<GarageSale>(0);
    }

    int add(GarageSale job) {
        this.list.add(job);
        return this.list.size();
    }

    GarageSale get(int location) {
        return this.list.get(location);
    }

    List<GarageSale> getAll() {
        return this.list;
    }

    int getCount() {
        return this.list.size();
    }

/*
    void replace(GarageSale newjob) {
        try {
            GarageSaleList newlist = new GarageSaleList(this.context);
            for (int i = 0; i < getCount(); i++) {
                GarageSale je = get(i);
                if (je.get_jobid().equals(newjob.get_jobid())) {
                    Log.d("CH12", "Replacing Job");
                    newlist.add(newjob);
                } else {
                    newlist.add(je);
                }
            }
            this.list = newlist.list;
            persist();
        } catch (Exception e) {

        }
    }
*/

    void persist() {
        try {
            FileOutputStream fos = this.context.openFileOutput("chapter12.xml", Context.MODE_PRIVATE);
            fos.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n".getBytes());
            fos.write("<joblist>\n".getBytes());
            for (int i = 0; i < getCount(); i++) {
                GarageSale je = get(i);
                fos.write(je.toXMLString().getBytes());
            }
            fos.write("</joblist>\n".getBytes());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            Log.d("CH12", "Failed to write out file?" + e.getMessage());
        }
    }

    static GarageSaleList parse(Context context) {
        try {
            FileInputStream fis = context.openFileInput("chapter12.xml");

            if (fis == null) {
                return null;
            }
            // we need an input source for the sax parser
            InputSource is = new InputSource(fis);

            // create the factory
            SAXParserFactory factory = SAXParserFactory.newInstance();

            // create a parser
            SAXParser parser = factory.newSAXParser();

            // create the reader (scanner)
            XMLReader xmlreader = parser.getXMLReader();

            // instantiate our handler
            GarageSaleListHandler garageSaleListHandler = new GarageSaleListHandler(context, null /*
                                                                         * no progress updates when
                                                                         * reading file
                                                                         */);

            // assign our handler
            xmlreader.setContentHandler(garageSaleListHandler);

            // perform the synchronous parse
            xmlreader.parse(is);

            // clean up
            fis.close();

            // return our new joblist
            return garageSaleListHandler.getList();
        } catch (Exception e) {
            Log.d("CH12", "Error parsing job list xml file : " + e.getMessage());
            return null;
        }
    }

}


