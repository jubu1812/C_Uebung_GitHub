package com.example.julia.c_uebung_github;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class MainActivity extends AppCompatActivity implements LocationListener {

    private static LocationManager locMan = null;
    SQLiteDatabase db;
    Button btn_anzeigen, btn_maps;
    Location currLoc;

    String DB_NAME = "gpsDB";
    int DB_VERSION = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Standort");

        btn_anzeigen = (Button) findViewById(R.id.btn_anzeigen);
        btn_anzeigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShowActivity.class);
                intent.putExtra("dbName", DB_NAME);
                intent.putExtra("dbVersion", DB_VERSION);
                startActivity(intent);
            }
        });

        btn_maps = (Button) findViewById(R.id.btn_maps);
        btn_maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "geo:" + currLoc.getLatitude() + "," + currLoc.getLongitude() + "?z=" + 13;
                Uri uri = Uri.parse(s);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                startActivity(intent);
            }
        });

        locMan = (LocationManager) getSystemService(LOCATION_SERVICE);

        MySQLiteHelper dbHelper = new MySQLiteHelper(this, DB_NAME, DB_VERSION);
        db = dbHelper.getReadableDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
    }

    @Override
    protected void onPause(){
        super.onPause();
        locMan.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {

        if(location == null) {
            return;
        }

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());

        currLoc = location;

        displayLocation(location, date);
        saveLocation(location, date);
    }

    private void displayLocation(Location location, String date) {

        TextView tv_long = (TextView)findViewById(R.id.longitude);
        TextView tv_lat = (TextView)findViewById(R.id.latitude);
        TextView tv_date = (TextView)findViewById(R.id.date);

        tv_long.setText(""+location.getLongitude());
        tv_lat.setText(""+location.getLatitude());

        tv_date.setText(date);
    }

    private void saveLocation(Location location, String date) {
        db.execSQL(StandortTbl.SQL_INSERT, new String[]{"" + location.getLatitude(), "" + location.getLongitude(), date});
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }



}
