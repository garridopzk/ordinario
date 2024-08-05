package com.example.ordinario.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// DatabaseHelper.java
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "OrdinarioBD";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_PRODUCTOS = "productos";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_PRECIO = "precio";
    public static final String COLUMN_CANTIDAD = "cantidad";
    public static final String COLUMN_IMAGEN = "imagen";
    public static final String COLUMN_FECHA = "fecha";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_PRODUCTOS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOMBRE + " TEXT, " +
                    COLUMN_PRECIO + " REAL, " +
                    COLUMN_CANTIDAD + " INTEGER, " +
                    COLUMN_IMAGEN + " TEXT, " +
                    COLUMN_FECHA + " TEXT " + ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTOS);
        onCreate(db);
    }
}

