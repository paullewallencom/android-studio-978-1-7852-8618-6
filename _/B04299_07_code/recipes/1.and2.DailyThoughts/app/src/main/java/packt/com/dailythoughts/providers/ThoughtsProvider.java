package packt.com.dailythoughts.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import java.util.HashMap;

import packt.com.dailythoughts.db.DatabaseHelper;

/**
 * Created by mike on 27-07-15.
 */
public class ThoughtsProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.packt.dailythoughts";
    static final String URL = "content://" + PROVIDER_NAME + "/thoughts";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    public static final String THOUGHTS_ID = "_id";
    public static final String THOUGHTS_NAME = "name";
    public static final String THOUGHTS_HAPPINESS = "happiness";

    static final int THOUGHTS = 1;
    static final int THOUGHT_ID = 2;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "thoughts", THOUGHTS);
        uriMatcher.addURI(PROVIDER_NAME, "thoughts/#", THOUGHT_ID);
    }

    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    private static HashMap<String, String> THOUGHTS_PROJECTION;

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(DatabaseHelper.THOUGHTS_TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case THOUGHTS:
                builder.setProjectionMap(THOUGHTS_PROJECTION);
                break;
            case THOUGHT_ID:
                builder.appendWhere( THOUGHTS_ID + "=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if (sortOrder == null || sortOrder == ""){
            sortOrder = THOUGHTS_NAME;
        }

        Cursor c = builder.query(db, projection,	selection, selectionArgs,null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case THOUGHTS:
                return "vnd.android.cursor.dir/vnd.df.thoughts";
            case THOUGHT_ID:
                return "vnd.android.cursor.item/vnd.df.thoughts";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = db.insert(	DatabaseHelper.THOUGHTS_TABLE_NAME , "", values);
        if (rowID > 0)
        {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add record: " + uri);
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
