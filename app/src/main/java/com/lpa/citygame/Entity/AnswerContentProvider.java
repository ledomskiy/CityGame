package com.lpa.citygame.Entity;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.lpa.citygame.database.DatabaseInstance;

/**
 * Created by lpa on 11.05.15.
 */
public class AnswerContentProvider extends ContentProvider {
    private DatabaseInstance dbInstance;

    public static final Uri CONTENT_URI = Uri.parse("content://com.lpa.answersdatabaseprovider/elements");
    private final static String ANSWER_TABLE_NAME = "PlayerAnswer";
    public final static String KEY_ID = "Id_Answer";
    public final static String PLAYER_COLUMN = "Player";
    public final static String CITY_NAME_COLUMN = "CityName";
    public final static String ANSWER_TIME_COLUMN = "AnswerTime";

    private final static int ALLROWS = 1;
    private final static int SINGLE_ROW = 2;

    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.lpa.answersdatabaseprovider","elements", ALLROWS);
        uriMatcher.addURI("com.lpa.answersdatabaseprovider","elements/#", SINGLE_ROW);
    }

    @Override
    public boolean onCreate() {
        DatabaseInstance.createInstance(getContext());
        dbInstance = DatabaseInstance.getInstance();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbInstance.getWritableDatabase();

        String groupBy = null;
        String having = null;

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables (ANSWER_TABLE_NAME);
        queryBuilder.setDistinct(true);

        switch (uriMatcher.match(uri)){
            case SINGLE_ROW:
                String rowID = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(KEY_ID + "=" + rowID);
                break;
            default:
                break;
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
        SQLiteDatabase db = dbInstance.getWritableDatabase();
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
