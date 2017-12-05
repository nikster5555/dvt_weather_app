package com.example.atish.dvt_weather_app;

import android.os.AsyncTask;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetWeather extends AsyncTask<String, Void, String> // Extends an Abstract Class that allows you to use and manipulate Threads
{
    @Override
    protected String doInBackground(String... weblinks) // Does this Method Processing in the Background
    {
        StringBuilder res = new StringBuilder();
        HttpURLConnection conn = null;
        URL link;
        try // Tries this code block and sees if it runs
        {
            link = new URL(weblinks[0]); // gets the link which is the url from the First Part of the list
            conn = (HttpURLConnection) link.openConnection(); // Variable to Open the Connection Initialized
            InputStream input = conn.getInputStream(); // Gets input Stream from the conn and assigns it to a Variable
            InputStreamReader isr = new InputStreamReader(input); // Variable to Read Input Stream
            int inData = isr.read(); // Saves the Data from the Input Stream in to an Integer Variable

            while(inData != -1) // When inData finishes getting read it is equal to negative one, so while the inData is not equal to Negative One
            {
                char curr = (char) inData;
                res.append(curr); // saves the data in the  Global res Variable
                inData = isr.read();
            }
            return res.toString(); // Returns the Res
        }
        catch (Exception e) // Runs this Code Block if an Error Occurs with an Exception
        {
            e.printStackTrace(); //Prints the Stack Trace to see where it has Occurred
        }
        return null; // Returns no Value
    }

    @Override
    protected void onPostExecute(String res) // When the doInBackground Method finishes Execute, then this will Run
    {
        super.onPostExecute(res);

        try  // Tries this code block and sees if it runs
        {
            JSONObject json = new JSONObject(res); // a JSON Object from the res
            JSONObject data = new JSONObject(json.getString("main")); // Gets the Weather Data from the JSON
            double tempMin = Double.parseDouble(data.getString("temp_min"))-273.15; // gets the Minimum Temperature from the Weather Data, saves it in to a Double variable, because it could be a decimal value
            double tempMax = Double.parseDouble(data.getString("temp_max"))-273.15; // gets the Maximum Temperature from the Weather Data, Converts it to Celsius from Kelvin
            int temMinRound = (int) Math.round(tempMin); // Rounds off the Celsius
            int temMaxRound = (int) Math.round(tempMax);
            String sMinTemp = "Min Temperature: " + Integer.toString(temMinRound) + " \u2103"; // Converts the Integer to a String
            String sMaxTemp = "Max Temperature: " + Integer.toString(temMaxRound) + " \u2103";
            Weather.minTemp.setText(String.valueOf(sMinTemp)); // Sets the Value in the Text View
            Weather.maxTemp.setText(String.valueOf(sMaxTemp));
        }
        catch (Exception e) // Runs this Code Block if an Error Occurs with an Exception
        {
            e.printStackTrace(); //Prints the Stack Trace to see where it has Occurred
        }
    }
}
