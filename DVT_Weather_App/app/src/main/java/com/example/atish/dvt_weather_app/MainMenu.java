package com.example.atish.dvt_weather_app;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;
import com.karan.churi.PermissionManager.PermissionManager;

public class MainMenu extends AppCompatActivity
{
    PermissionManager permissionManager; // Global Permission Manager Object, can be used by all Methods in this Class

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        permissionManager = new PermissionManager() {}; // Instantiates the PermissionManager Object
        permissionManager.checkAndRequestPermissions(this); // Checks for the Permissions from the Android Manifest to see if it is Granted or not, and Requests for them if they aren't already Granted
        Toast.makeText(this, "Please Turn On Device Location", Toast.LENGTH_LONG).show(); // Shows this Toast Message, for a Long Duration


        Switch music = (Switch) findViewById(R.id.swtMusic); // Gets the Switch
        final MediaPlayer sound = MediaPlayer.create(this, R.raw.nm_audio); // Instantiates the Audio Track from the raw Folder

        music.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(sound.isPlaying()) // If the Audio is Playing, it will Run this Block of Code
                {
                    try // Tries this Piece of code within the block
                    {
                        sound.pause(); // Pauses the Audio Track
                    }
                    catch(Exception e) // Catches this Error and runs the Block
                    {
                        Toast.makeText(getApplicationContext(), "An Error has Occurred: " + e, Toast.LENGTH_SHORT).show(); // Shows a Toast Message when an Error Occurs
                    }
                }
                else // Else if the Switch is off, it will run this Block of Code
                {
                    try // Tries this Piece of code within the block
                    {
                        sound.start(); // Starts the Audio Track
                    }
                    catch(Exception e) // Catches this Error and runs the Block
                    {
                        Toast.makeText(getApplicationContext(), "An Error has Occurred: " + e, Toast.LENGTH_SHORT).show(); // Shows a Toast Message when an Error Occurs
                    }
                }
            }
        });
    }

    public void btnWeather(View V)
    {
        try // Tries this Piece of code within the block
        {
            Intent weather = new Intent(this, Weather.class); // Puts the Activity in to a Intent
            startActivity(weather); // Starts the Activity
        }
        catch(Exception e) // Catches this Error and runs the Block
        {
            Toast.makeText(this, "An Error Occurred: " + e, Toast.LENGTH_SHORT).show(); // Shows this Toast Message
        }
    }

    public void btnExit(View v)
    {
        try // Tries this Piece of code within the block
        {
            finish(); // Finishes the Activity
            System.exit(0); // Exits the Activity
        }
        catch (Exception e) // Catches this Error and runs the Block
        {
            Toast.makeText(this, "An Error Occurred, can not Exit Application: " + e, Toast.LENGTH_SHORT).show(); // Shows this Toast Message
        }
    }

    public void btnHelp(View V)
    {
        try // Tries this Piece of code within the block
        {
            Intent help = new Intent(this, Help.class); // Puts the Activity in to a Intent
            startActivity(help); // Starts the Activity
        }
        catch(Exception e) // Catches this Error and runs the Block
        {
            Toast.makeText(this, "An Error Occurred: " + e, Toast.LENGTH_SHORT).show(); // Shows this Toast Message
        }
    }
}
