package com.lpa.citygame.Entity;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import com.lpa.citygame.database.DatabaseInstance;

/**
 * Created by lpa on 09.05.15.
 */
public class CitiesContentProvider extends ContentProvider {
    public static final Uri CONTENT_URI = Uri.parse ("content://com.lpa.citiesdatabaseprovider/elements");
    private static final String CITY_TABLE_NAME = "CITY";
    public static final String KEY_ID = "Id_City";
    public static final String CITY_NAME = "CityName";
    public static final String FIRST_CHAR = "FirstChar";
    public static final String LAST_CHAR = "LastChar";

    private static final int ALLROWS = 1;
    private static final int SINGLE_ROW = 2;

    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.lpa.citiesdatabaseprovider", "elements", ALLROWS);
        uriMatcher.addURI("com.lpa.citiesdatabaseprovider", "elements/#", SINGLE_ROW);
    }

    private DatabaseInstance dbInstance;
    @Override
    public boolean onCreate() {
        Log.v("CitiesContentProvider", "OnCreate");
        DatabaseInstance.createInstance(getContext());
        dbInstance = DatabaseInstance.getInstance();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.v("CitiesContentProvider", "QueryMethod...");
        SQLiteDatabase db = dbInstance.getWritableDatabase();

        String groupBy = null;
        String having = null;

        SQLiteQueryBuilder queryBuilder= new SQLiteQueryBuilder();
        queryBuilder.setTables(CITY_TABLE_NAME);
        queryBuilder.setDistinct(true);

        switch (uriMatcher.match(uri)){
            case SINGLE_ROW:
                String rowID = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(KEY_ID + "=" + rowID);
                break;
            default:break;
        }
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, having, sortOrder);
        return cursor;
    }


    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
