package com.example.julia.c_uebung_github;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class MainActivity extends AppCompatActivity implements LocationListener {

    private static LocationManager locMan = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locMan = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

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

        displayLocation(location);
        //saveLocation(location);
    }

    private void displayLocation(Location location) {

        TextView tv_long = (TextView)findViewById(R.id.longitude);
        TextView tv_lat = (TextView)findViewById(R.id.latitude);
        TextView tv_date = (TextView)findViewById(R.id.date);

        tv_long.setText(""+location.getLongitude());
        tv_lat.setText(""+location.getLatitude());

        System.out.println("location display");

        String datum = GregorianCalendar.DAY_OF_MONTH + "." + GregorianCalendar.MONTH + "." + GregorianCalendar.YEAR;

        DateFormat df = new SimpleDateFormat("DD.MM.yyyy, HH:mm");

        String date = df.format(Calendar.getInstance().getTime());

        tv_date.setText(date);
    }

    private void saveLocation(Location location) {
        MySQLiteHelper dbHelper = new MySQLiteHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
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

    String DB_NAME = "gpsDB";
    int DB_VERSION = 1;

    private class MySQLiteHelper extends SQLiteOpenHelper{

        public MySQLiteHelper(Context context){
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
