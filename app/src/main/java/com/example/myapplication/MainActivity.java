package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        writeLogs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.lbl_browse) {
            Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse("http://androidium.org"));
            startActivity(intent);

            return true;
        }

        if (id == R.id.action_settings) {
            Intent myIntent = new Intent(MainActivity.this, PreferencesActivity.class);
            MainActivity.this.startActivity(myIntent);

            return true;
        }

        if (id == R.id.action_sensors) {
            Intent myIntent = new Intent(MainActivity.this, Sensors.class);
            MainActivity.this.startActivity(myIntent);

            return true;
        }

        if (id == R.id.action_photo) {
            Intent myIntent = new Intent(MainActivity.this, TakePhotoActivity.class);
            MainActivity.this.startActivity(myIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void writeLogs() {

        File file = new File(this.getBaseContext().getFilesDir(), "app");
        if(!file.exists()){
            file.mkdir();
        }

        try{
            File gpxfile = new File(file, "logs.txt");
            FileWriter writer = new FileWriter(gpxfile);

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();

            writer.append("Application opened on: " + formatter.format(date));
            writer.flush();
            writer.close();

        }catch (Exception e){
            e.printStackTrace();

        }
    }
}
