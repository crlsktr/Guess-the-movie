package com.example.jorrifalslev.guessthemovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jorrifalslev.guessthemovie.moviedb.Movie;
import com.example.jorrifalslev.guessthemovie.moviedb.MovieDBHandler;

import java.io.File;


public class Phrase extends AppCompatActivity
{
    private final String dbFileName = "movieDB.db";


    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_phrase );
        Button button = ( Button ) findViewById ( R.id.button );

        //Create DB file
        createDBfile ();
        //Button Listener
        button.setOnClickListener ( new View.OnClickListener ()
        {
            @Override
            public void onClick ( View v )
            {
                FetchMovie ();
            }
        } );
    }

    private void createDBfile ()
    {
        try
        {
            File file = new File ( getFilesDir (), dbFileName );
            if ( ! file.exists () )
            {
                file.createNewFile ();
            }

            //Add sample movie
            MovieDBHandler db = new MovieDBHandler ( this, dbFileName, null, 1 );
            db.addMovie ( new Movie (
                    "Lion King",
                    "capture link",
                    "http://t1.gstatic.com/images?q=tbn:ANd9GcQ2vZQTR7HyXqWbjYYr0HNfAyDLRq7EXogJGAgG0bbM8odQlDLV",
                    "hakuna matata",
                    "Audio Link" ) );

        }
        catch ( Exception ex )
        {
            Log.e ( "Error", "An exception occurred when creating the new database file" );
        }

    }

    private void FetchMovie ()
    {
        //get the movie in sample
        MovieDBHandler handler = new MovieDBHandler ( this, dbFileName, null, 1 );
        Movie lk = handler.findMovieByName ( "Lion King" );
        TextView text = ( TextView ) findViewById ( R.id.PhraseLabel );
        if ( lk != null )
        {
            text.setText ( lk.get_name () );
            loadImage ( lk.get_cover () );
        }
        else
            text.setText ( "could not find Lion king" );
    }

    private void loadImage ( String url )
    {
        try
        {
            new DownloadImageTask ( ( ImageView ) findViewById ( R.id.imageView ) ).execute ( url );
        }
        catch ( Exception ex )
        {
            System.err.println ( "something bad happened " + ex.getStackTrace () );
        }
    }

    @Override
    public boolean onCreateOptionsMenu ( Menu menu )
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater ().inflate ( R.menu.menu_phrase, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected ( MenuItem item )
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId ();

        //noinspection SimplifiableIfStatement
        if ( id == R.id.action_settings )
        {
            return true;
        }

        return super.onOptionsItemSelected ( item );
    }
}
