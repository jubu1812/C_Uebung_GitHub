package com.example.julia.c_uebung_github;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShowActivity extends AppCompatActivity {

    ArrayAdapter<Standort> adapter;

    String DB_NAME;
    int DB_VERSION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        setTitle("Standorte anzeigen");

        Bundle params = getIntent().getExtras();
        if (params.containsKey("dbName")) {
            DB_NAME = params.getString("dbName");
        }
        if (params.containsKey("dbVersion")) {
            DB_VERSION = params.getInt("dbVersion");
        }

        ListView lv = (ListView) findViewById(R.id.list_standorte);
        adapter = new ArrayAdapter<Standort>(this, android.R.layout.simple_list_item_single_choice);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = "geo:" + adapter.getItem(position).getLatitude() + "," + adapter.getItem(position).getLongitude() + "?z=" + 13;
                Uri uri = Uri.parse(s);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                startActivity(intent);
            }
        });

        getData();
    }

    private void getData() {
        MySQLiteHelper dbHelper = new MySQLiteHelper(this, DB_NAME, DB_VERSION);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor rows = db.rawQuery("Select * from " + StandortTbl.TABLE_NAME, new String[]{});

        while (rows.moveToNext()) {
            double longitude = rows.getDouble(0);
            double latitude = rows.getDouble(1);
            String dateTime = rows.getString(2);
            Standort s = new Standort(longitude, latitude, dateTime);
            adapter.add(s);
        }

        rows.close();
        db.close();
    }
}
