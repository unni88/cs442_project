package com.cs442.team3.con_venient;

import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Unni on 11/15/15.
 */
public class DataFromWebService {

    public static String hostedURL = "http://10.0.2.2:8080/conventionserviceapp/rest/hello";

    public static String TAG = "WebServiceClass";

    public static boolean isHosted = false;

    public ArrayList<MyEvent> getDataFromWebService(){
        // Get the XML
        URL url;
        ArrayList<MyEvent> events = new ArrayList<MyEvent>();
        try {
            url = new URL(hostedURL);
            URLConnection connection;


            connection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection)connection;
          //  int responseCode = httpConnection.getResponseCode();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Accept","text/xml");

            if (true) {

                String fullString = "";
               /* BufferedReader reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                String line;
               while ((line = reader.readLine()) != null) {
                    fullString += line;
                }*/
                try {
                    XMLParser xmlParser = new XMLParser();
                    events = xmlParser.parse(httpConnection.getInputStream());
                }catch(final Exception e){
                    e.printStackTrace();
                }

               // reader.close();


//                System.out.println("------------- This is the Output that needs to be printed.."+ fullString);

                /*
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();

                // Parse the earthquake feed.
                Document dom = db.parse(in);
                Element docEle = dom.getDocumentElement();

                // Get a list of each earthquake entry.
                NodeList nl = docEle.getElementsByTagName("entry");
                if (nl != null && nl.getLength() > 0) {
                    for (int i = 0 ; i < nl.getLength(); i++) {
                        Element entry = (Element)nl.item(i);
                        Element title = (Element)entry.getElementsByTagName("title").item(0);
                        Element g = (Element)entry.getElementsByTagName("georss:point").item(0);
                        Element when = (Element)entry.getElementsByTagName("updated").item(0);
                        Element link = (Element)entry.getElementsByTagName("link").item(0);

                        String details = title.getFirstChild().getNodeValue();
                        String hostname = "http://earthquake.usgs.gov";
                        String linkString = hostname + link.getAttribute("href");

                        String point = g.getFirstChild().getNodeValue();
                        String dt = when.getFirstChild().getNodeValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
                        Date qdate = new GregorianCalendar(0,0,0).getTime();
                        try {
                            qdate = sdf.parse(dt);
                        } catch (ParseException e) {
                            Log.d(TAG, "Date parsing exception.", e);
                        }

                        String[] location = point.split(" ");
                        Location l = new Location("dummyGPS");
                        l.setLatitude(Double.parseDouble(location[0]));
                        l.setLongitude(Double.parseDouble(location[1]));

                        String magnitudeString = details.split(" ")[1];
                        int end =  magnitudeString.length()-1;
                        double magnitude = Double.parseDouble(magnitudeString.substring(0, end));

                        details = details.split(",")[1].trim();

                        Quake quake = new Quake(qdate, details, l, magnitude, linkString);

                        // Process a newly found earthquake
                        addNewQuake(quake);
                    }
                }*/
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "Malformed URL Exception", e);
        } catch (IOException e) {
            Log.e(TAG, "IO Exception", e);
        }/* catch (ParserConfigurationException e) {
            Log.e(TAG, "Parser Configuration Exception", e);
        } catch (SAXException e) {
            Log.e(TAG, "SAX Exception", e);
        }*/
        finally {
        }
        return events;
    }

}
