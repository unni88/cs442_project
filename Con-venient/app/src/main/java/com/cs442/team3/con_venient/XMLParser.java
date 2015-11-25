package com.cs442.team3.con_venient;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Unni on 11/15/15.
 */
public class XMLParser {


    private static final String ns = null;

    public ArrayList<MyEvent> parse(InputStream in) throws XmlPullParserException, IOException {
        ArrayList<MyEvent> events   =  new ArrayList<MyEvent>();
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            events = readFeed(parser);
        }catch(final Exception e){
            e.printStackTrace();
        } finally {
            in.close();
        }
     //   System.out.println("success");
        return events;
    }


    private ArrayList<MyEvent> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<MyEvent> events = new ArrayList();
        parser.require(XmlPullParser.START_TAG, ns, "events");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();


            // Starts by looking for the event tag
            if (name.equals("event")) {
                events.add(readEvent(parser));
            } else {
             //   skip(parser);
            }
        }
        return events;
    }

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
    private MyEvent readEvent(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "event");
        String eventName = "";
        String eventAddress = "";
        String eventDate = ""; //Format is 10-Sep-2015
        String time = ""; //Format is 14:00
        List<Booth> boothList = new ArrayList<Booth>();
        eventName = parser.getAttributeValue(null,"name");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            //TODO get the Attribute name from the Event Tag

            String name = parser.getName();

            if (name.equals("address")) {
                eventAddress = readAddress(parser);
                System.out.println("**************************ADDRESS:"+eventAddress);
            } else if (name.equals("date")) {
                eventDate = readDate(parser);
                System.out.println("**************************DATE:"+eventDate);
            } else if (name.equals("time")) {
                time = readTime(parser);
                System.out.println("**************************TIME:"+time);
            } else if (name.equals("booths")) {
                boothList = readBooths(parser);
            }
            else {
               //TODO implement SKIP Later... skip(parser);
            }
        }
        System.out.println("************************************EVENT NAME "+eventName);
        final MyEvent myEventObject = new MyEvent(eventName,false,eventAddress, eventDate+" "+time); // date Time "11/21/2015  11:14:12"
        if(null != boothList && !boothList.isEmpty()){
            for(Booth booth :boothList){
                myEventObject.addBooth(booth);
            }
        }
        return  myEventObject;
    }



    // Processes title tags in the feed.
    private String readAddress(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "address");
        String address = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "address");
        return address;
    }



    // Processes title tags in the feed.
    private String readDate(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "date");
        String date = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "date");
        return date;
    }


    // Processes title tags in the feed.
    private String readTime(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "time");
        String time = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "time");
        return time;
    }


    // Processes title tags in the feed.
    private List<Booth> readBooths(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "booths");
        List<Booth> boothList = new ArrayList<>();
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the booth tag
            if (name.equals("booth")) {
                boothList.add(readBooth(parser));
            }
        }
//        parser.require(XmlPullParser.END_TAG, ns, "booths");
        return boothList;
    }


    // Processes title tags in the feed.
    private Booth readBooth(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "booth");

        String boothName="";
        int boothid=0;
        String website="";
        String phone="";
        String description="";

        String name = parser.getName();
        boothName = parser.getAttributeValue(null, "name");
        boothid = Integer.parseInt(parser.getAttributeValue(null, "id"));

    //    System.out.println("**************************BOOTHNAME:"+boothName+",BOOTHID"+boothid);

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            name = parser.getName();
            if (name.equals("website")) {
                website = readWebsite(parser);
            } else if (name.equals("phone")) {
                phone = readPhone(parser);
            } else if (name.equals("description")) {
                description = readDescription(parser);
            }
        }
     //   System.out.println("WEBSITE:" + website);

//        parser.require(XmlPullParser.END_TAG, ns, "booth");
        return new Booth(boothName,boothid,website,phone,description);
    }




    // Processes title tags in the feed.
    private String readWebsite(XmlPullParser parser) throws IOException, XmlPullParserException {
     //   parser.require(XmlPullParser.START_TAG, ns, "website");
        String website = readText(parser);
    //    System.out.println("..........................."+website);
    //    parser.require(XmlPullParser.END_TAG, ns, "website");
        return website;
    }


    // Processes title tags in the feed.
    private String readPhone(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "phone");
        String phone = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "phone");
        return phone;
    }


    // Processes title tags in the feed.
    private String readDescription(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "description");
        String desc = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "description");
        return desc;
    }





    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

}
