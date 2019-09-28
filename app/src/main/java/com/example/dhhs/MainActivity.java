package com.example.dhhs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

/*
 * Main activity that is loaded in with the app.  Holds the UI for the activty as well as methods
 * relating to getting the JSON data through the Google API, as well as initializing the
 * RecyclerViews
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";  //Tag used for debugging
    private ArrayList<String> mNames = new ArrayList<>();   //ArrayList of strings containing titles of Recycler Views
    private ArrayList<DataStructure> output;
    private ArrayList<Club> clubs;
    private Context context;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
         * As the app loads up, initializes the UI of the main activity and starts the JSON data
         * request in another thread
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycleV = findViewById(R.id.recyclerv_view);
        String sheets_address = "https://sheets.googleapis.com/v4/spreadsheets/1gsrdfheTKsqRw6hHrgnbBXP0EmYtFT0nwvfqaG0jDH0/values/Sheet1?key=AIzaSyCbZPiojjVUMac1ldoA6gYyYJ2MRMUQkyk";
        new JsonTask().execute(sheets_address);
        context = getApplicationContext();

        //Opens "About App" activity
        TextView aboutUs = (TextView) findViewById(R.id.txtAbout);
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent empty = new Intent(context, AboutUs.class);
                startActivity(empty);
            }
        });
    }

    ProgressDialog pd;
    RecyclerView recycleV;

    private class JsonTask extends AsyncTask<String, String, String> {
        /*
         * Adds functionality to be able to request data over the internet.
         * Three main parts: Pre-Execute, Do-in-Background, and Post-Execute
         *
         * Pre-Execute: First thing that is called when JsonTask.execute() is called.  Responsible
         * in this case for showing the "Please wait" dialogue on screen while waiting for the
         * request to go through
         */

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {
            /*
             * The code to make a HTTP request.  In short, the app tries to connect to Google's
             * API and reads the data it finds, plus a bunch of error checking on top of that.
             */

            HttpURLConnection connection = null;
            BufferedReader reader = null;
            Looper.prepare();
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                //Tries to establish a connection to the internet

                InputStream stream = connection.getInputStream();
                //Converts what whatever website we accessed sent into standard in

                reader = new BufferedReader(new InputStreamReader(stream));
                //Uses buffered reader to more easily manage the input

                StringBuffer buffer = new StringBuffer();
                String line = "";

                //On successful connection, reads data received and adds to to a string
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                    Log.d("Response: ", "> " + line); //Prints out input

                }
                return buffer.toString();

            } catch (MalformedURLException e) { //Catches bad URLs
                e.printStackTrace();
            } catch (IOException e) { //Catches bad inputs
                e.printStackTrace();
            } finally {  //If it can't connect...
                if (connection != null) {
                    connection.disconnect();
                }
                try {  //Close the reader
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {  //If there is some bogus error don't crash
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            /*
             * Last function in the AsyncTask.  Responsible for dismissing the progress dialogue and
             * more importantly formatting the data from the Google API into a JSON object, and from
             * there putting it into a DataStructure object that the app can more easily interact
             * with.
             */
            super.onPostExecute(result);

            if (pd.isShowing()) {
                pd.dismiss();
            }
            try {
                JSONObject parser;

                output = new ArrayList<>();

                try {
                    parser = new JSONObject(result);

                    JSONArray array = (JSONArray) parser.get("values");

                    /*Gets the indexes of all of the actual values.  Array looks like:

                    Values:
                          0:
                              Timestamp
                              Quote
                              Joke
                              Other Announcements
                          1:
                              1/2/2019
                              "Quote here"
                              "Joke here"
                              "Other announcements here"
                          2:
                              1/3/2019
                              ...
                          3:
                              ...
                    */

                    JSONArray headings = (JSONArray) array.get(0);
                    JSONArray row = (JSONArray) array.get(array.length() - 1);

                    DataStructure ds;

                    if (counter == 0){
                        for (int col = 1; col < headings.length(); col++) {
                            Log.e(TAG, "Reached initImageBitmaps.  array.length() = " + array.length());

                            //Output is an ArrayList of DataStructures that contains the sheets data
                            //after it's parsed
                            output.add(new DataStructure(headings.get(col).toString(), row.get(col).toString()));
                        }
                        counter++;
                        Log.e(TAG, "Reached initImageBitmaps");
                        initImageBitmaps();

                    }else{
                        //For later development with Clubs functionality, can ignore
                        for (int col = 1; col < array.length(); col++){
                            headings = (JSONArray) array.get(col);
                            clubs.add(new Club(headings.get(col).toString(), headings.get(col + 1).toString(), headings.get(col + 2).toString(), headings.get(col + 3).toString(), headings.get(col + 4).toString(), headings.get(col + 5).toString()));
                        }
                        Log.e(TAG, "713\n" + clubs.toString());
                        counter = 0;
                    }


                } catch (Exception e) { //Typically means that the input wasn't a String in JSON format
                    Log.e(TAG, "JSON parse error");
                }
                Log.e(TAG, counter + "QRS\n" + output.toString());

            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    private void initImageBitmaps() {
        ArrayList<DataStructure> q = output;
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        //Creates a RecyclerView on the main activity

        System.out.println(" QXR\n" + output);

        //Populates the RecyclerViews
        for (int i = 0; i < q.size(); i++) {
            try {
                mNames.add(q.get(i).getHeading());
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

        initRecyclerView();
    }

    private void initRecyclerView() {
        //Defines the RecyclerViewAdapters
        Log.d(TAG, "initRecyclerView: init recyclerView.");
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, output);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}