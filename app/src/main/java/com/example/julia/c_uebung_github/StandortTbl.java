package com.example.julia.c_uebung_github;

/**
 * Created by mschneiderbauer on 12.02.2018.
 */

public class StandortTbl {
    public static final String TABLE_NAME = "Standort";
    public static final String[] ALL_COLUMS = new String[]{"latitude" + "longitude" + "dateAndTime"};
    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME +
                    "(" +
                    "latitude REAL," +
                    "longitude REAL," +
                    "dateAndTime TEXT PRIMARY KEY);";
    public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME;
    public static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + "(latitude, longitude, dateAndTime) VALUES(?,?,?)";
    public static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET latitude = ?, longitude = ?, dateAndTime = ? WHERE Standort_id = ?";
}
