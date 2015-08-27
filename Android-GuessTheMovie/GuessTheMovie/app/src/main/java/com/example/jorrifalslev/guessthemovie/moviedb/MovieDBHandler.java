package com.example.jorrifalslev.guessthemovie.moviedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jorri Falslev on 8/18/2015.
 */
public class MovieDBHandler extends SQLiteOpenHelper
{
    //Name for Movies table
    private static final String TABLE_MOVIES = "Movies";
    //Columns for Movies table
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHRASE = "phrase";
    public static final String COLUMN_CAPTURE = "capture";
    public static final String COLUMN_COVER = "cover";
    public static final String COLUMN_AUDIOCLIP = "audioclip";

    public MovieDBHandler ( Context context, String name, SQLiteDatabase.CursorFactory factory, int version )
    {
        super ( context, name, factory, version );
    }

    @Override
    public void onCreate ( SQLiteDatabase db )
    {
        //Structure of the Movies table
        String CREATE_TABLE_MOVIES = "CREATE TABLE IF NOT EXISTS "+ TABLE_MOVIES +
                "( " + COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, "+
                COLUMN_PHRASE + " TEXT, "+
                COLUMN_CAPTURE + " TEXT, "+
                COLUMN_COVER + " TEXT, "+
                COLUMN_AUDIOCLIP + " TEXT)";

        db.execSQL ( CREATE_TABLE_MOVIES );
    }

    @Override
    public void onUpgrade ( SQLiteDatabase db, int oldVersion, int newVersion )
    {
        db.execSQL ( "DROP TABLE IF EXISTS " + TABLE_MOVIES );
        onCreate ( db );
    }

    public void addMovie(Movie nMovie)
    {
        ContentValues values = new ContentValues (  );
        values.put ( COLUMN_NAME, nMovie.get_name () );
        values.put ( COLUMN_CAPTURE,nMovie.get_capture () );
        values.put ( COLUMN_COVER,nMovie.get_cover () );
        values.put ( COLUMN_PHRASE,nMovie.get_capture () );
        values.put ( COLUMN_AUDIOCLIP, nMovie.get_audioclip () );
        SQLiteDatabase db = this.getWritableDatabase ();

        db.insert ( TABLE_MOVIES, null, values );
    }

    public Movie findMovieByName(String movieName) {
        String query = "Select * FROM " + TABLE_MOVIES + " WHERE " + COLUMN_NAME + " =  \"" + movieName + "\"";

        SQLiteDatabase db = this.getReadableDatabase ();

        Cursor cursor = db.rawQuery(query, null);

        Movie movie = new Movie();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            movie.set_id (Integer.parseInt(cursor.getString(0)));
            movie.set_name (cursor.getString(1));
            movie.set_phrase (cursor.getString(2));
            movie.set_capture ( cursor.getString ( 3 ) );
            movie.set_cover ( cursor.getString ( 4 ) );
            movie.set_audioclip ( cursor.getString ( 5 ) );
            cursor.close();
        } else {
            movie = null;
        }
        db.close ();
        return movie;
    }
}
