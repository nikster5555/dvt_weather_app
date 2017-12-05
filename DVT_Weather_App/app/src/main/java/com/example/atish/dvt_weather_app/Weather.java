package com.example.atish.dvt_weather_app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class Weather extends AppCompatActivity
{
    @SuppressLint("StaticFieldLeak")
    static TextView place; // A Global Variable - which can be accessed from all Methods with in this class, this is a TextView Variable
    @SuppressLint("StaticFieldLeak")
    static TextView minTemp;
    @SuppressLint("StaticFieldLeak")
    static TextView maxTemp;
    @SuppressLint("StaticFieldLeak")
    static TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Toast.makeText(this, "Please Make Sure you have Internet Access", Toast.LENGTH_LONG).show(); // Shows this Toast Message, for a Long Duration

        place = (TextView) findViewById(R.id.lblPlace);
        minTemp = (TextView)  findViewById(R.id.lblMinTemp);
        maxTemp = (TextView)  findViewById(R.id.lblMaxTemp);
        date = (TextView) findViewById(R.id.lblDate);

        LocationManager locman = (LocationManager) getSystemService(LOCATION_SERVICE); // Manages the Location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) // Gets the Permission to use
        {
            return;
        }
        assert locman != null;
        if (locman.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) // Checks if the Network Provider is Enabled
        {
            locman.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() // Requests Updates by Network Provider which determines the Location by the Data or Wifi Access by the Network, Minimum Time to Update and the Minimum Distance to Update is set to Zero
            {
                @Override
                public void onLocationChanged(Location loc)
                {
                    Geocoder gC = new Geocoder(getApplicationContext()); // Instantiates the Geocoder Class
                    double longi = loc.getLongitude(); // Gets the Longitude
                    double lat = loc.getLatitude(); // Gets the Latitude
                    List<Address> addrList = null; // gets the Latitude and the Longitude Value, Returns only 1 Address Value
                    try // Tries the Block of Code
                    {
                        addrList = gC.getFromLocation(lat, longi, 1); // gets the Latitude and the Longitude Value, Returns only 1 Address Value
                    }
                    catch (IOException e) // Runs this Block of Code when an Exception Happens
                    {
                        e.printStackTrace(); // Prints the Stack trace, to see where the Error Occurred
                    }
                    assert addrList != null;
                    String local = addrList.get(0).getLocality(); // Gets Locality
                    String coun = addrList.get(0).getCountryName(); // Gets Country Name
                    String locCoun = "Location: " + local + ", " + coun;
                    place.setText(locCoun); // Sets the Locality as Text in to the EditText
                    GetWeather gw = new GetWeather(); // Instantiates the Class
                    gw.execute("http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + longi + "&appid=c5c99c84662c06bb6e3bf3b5a705679c"); // Executes the Class
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) // When the Network status changes
                {
                    Toast.makeText(getApplicationContext(), "The Provider Status has Changed: " + provider + "status: " + status, Toast.LENGTH_SHORT).show(); // Shows a Toast Message when the Provider Status has Changed, for a Short Duration
                }

                @Override
                public void onProviderEnabled(String provider) // When the Network is Enabled
                {
                    Toast.makeText(getApplicationContext(), "Location is Enabled", Toast.LENGTH_SHORT).show(); // Shows a Toast Message when Network is Enabled, for a Short Duration
                }

                @Override
                public void onProviderDisabled(String provider) // When the Network is Disabled
                {
                    Toast.makeText(getApplicationContext(), "Location is Disabled", Toast.LENGTH_SHORT).show();  // Shows a Toast Message when Network is Disabled, for a Short Duration
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Location is not Enabled", Toast.LENGTH_SHORT).show();  // Shows a Toast Message when Network is not Enabled, for a Short Duration
        }

        String currDateTime = "Date: " + DateFormat.getDateTimeInstance().format(new Date()); // Gets the Current Date and Time from the Device
        date.setText(currDateTime); // Sets the Current Date and Time into the Text View
    }
}
