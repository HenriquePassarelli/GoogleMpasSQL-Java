package com.example.googlemaps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "exampleMaps";
    private static final String TABLE_NAME = "locals";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_LOCAL = "local";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";


    public DatabaseHandler(Context context) {
        super(context,
                DATABASE_NAME,null,
                DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
// Category table create query
        String CREATE_ITEM_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_LOCAL + " TEXT,"
                + COLUMN_LATITUDE + " TEXT,"
                + COLUMN_LONGITUDE + " TEXT)";
        database.execSQL(CREATE_ITEM_TABLE);
    }

    public void insertLabel(LocalMaps maps) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LOCAL, maps.getLocal());//column name, column value
        values.put(COLUMN_LATITUDE, maps.getLatitude().toString());//column name, column value
        values.put(COLUMN_LONGITUDE, maps.getLongitude().toString());//column name, column value
        // Inserting Row
        database.insert(TABLE_NAME, null, values);//tableName,nullColumnHack, CotentValues

        database.close(); // Closing database connection
    }

    public List<LocalMaps> getAllLabels() {
        List<LocalMaps> list = new ArrayList<LocalMaps>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery,
                null);//selectQuery,selectedArguments
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LocalMaps aluno = new LocalMaps(
                        cursor.getString(1),//RG
                        Double.parseDouble(cursor.getString(2)),
                        Double.parseDouble(cursor.getString(3)));
                list.add(aluno);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        database.close();
        // returning lables
        return list;
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
// Drop older table if existed
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(database);
    }
}
