package com.example.sqliteimage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBServer {
    private static final String DATABASE_NAME = "data.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase database;

    public DBServer(Context context) {
        OpenHelper openHelper = new OpenHelper(context);
        this.database = openHelper.getWritableDatabase();
    }

    public class Products{
        private static final String TABLE_NAME = "images";

        private static final String COLUMN_ID= "id";
        private static final String COLUMN_NAME= "name";
        private static final String COLUMN_IMAGE = "image";

        private static final int NUM_COLUMN_ID = 0;
        private static final int NUM_COLUMN_NAME = 1;
        private static final int NUM_COLUMN_IMAGE = 2;

        public long insert(byte[] blob, String name){
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_IMAGE, blob);
            cv.put(COLUMN_NAME, name);
            return database.insert(TABLE_NAME, null, cv);
        }

        public void delete(){
            database.delete(TABLE_NAME, null, null);
        }

        public ArrayList<Preview> selectAll(){
            Cursor cursor = database.query(TABLE_NAME, null, null,
                    null, null, null, null);

            ArrayList<Preview> arr = new ArrayList<>();
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                do {
                    int id = cursor.getInt(NUM_COLUMN_ID);
                    String description = cursor.getString(NUM_COLUMN_NAME);
                    byte[] image = cursor.getBlob(NUM_COLUMN_IMAGE);
                    arr.add(new Preview(id, image, description));
                } while (cursor.moveToNext());
            }
            return arr;

        }

        public byte[] select(int id){
            Cursor cursor = database.query(TABLE_NAME, null, COLUMN_ID + " =?",
                    new String[]{String.valueOf(id)}, null, null, null);

            byte[] out = null;
            if (cursor.getCount() > 0){
                cursor.moveToFirst();
                out = cursor.getBlob(NUM_COLUMN_IMAGE);
            }
            cursor.close();
            return out;
        }
    }

    private class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + Products.TABLE_NAME + " (" +
                    Products.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    Products.COLUMN_NAME + " TEXT UNIQUE NOT NULL, " +
                    Products.COLUMN_IMAGE + " BLOB NOT NULL);";

            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + Products.TABLE_NAME);
            onCreate(db);
        }
    }

}
