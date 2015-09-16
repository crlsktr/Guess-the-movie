package com.example.jorrifalslev.guessthemovie;

import android.graphics.Color;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jorrifalslev.guessthemovie.Utilities.OnSwipeTouchListener;
import com.example.jorrifalslev.guessthemovie.moviedb.Movie;
import com.example.jorrifalslev.guessthemovie.moviedb.MovieDBHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Phrase extends AppCompatActivity  implements GestureDetector.OnGestureListener
{
    private final String dbFileName = "movieDB.db";
    private Movie selectedMovie;
    private Animation fadeOut;
    private Animation fadein;
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_phrase );

        //Gesture detection
        mDetector = new GestureDetectorCompat ( this,this );

        //Load animation
        fadeOut = AnimationUtils.loadAnimation ( getApplicationContext (), R.anim.fade_out );

        fadeOut.setAnimationListener ( new Animation.AnimationListener ()
        {
            @Override
            public void onAnimationStart ( Animation animation )
            {

            }

            @Override
            public void onAnimationEnd ( Animation animation )
            {
//                Toast.makeText ( base,"showing next hint",Toast.LENGTH_LONG );
            }

            @Override
            public void onAnimationRepeat ( Animation animation )
            {

            }
        } );

        fadein = AnimationUtils.loadAnimation ( getApplicationContext (), R.anim.fade_in );
        //Create DB file
        createDBfile ();
        FetchMovie ();

    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    public void nextHintBtn ( View view )
    {
        Toast.makeText ( getBaseContext (), "showing next hint", Toast.LENGTH_LONG );
        fadeOutCover ();
        fadeInPhrase ();
    }

    private void fadeInPhrase ()
    {
        TextView phrase = (TextView) findViewById ( R.id.PhraseLabel );

        if ( phrase.getVisibility () != View.VISIBLE )
        {
            phrase.setVisibility ( View.VISIBLE );
            phrase.startAnimation ( fadein );
        }
    }

    private void fadeOutCover ()
    {
        ImageView image = ( ImageView ) findViewById ( R.id.imageView );
        if ( image.getVisibility () != View.GONE )
        {
            image.setVisibility ( View.GONE );
            image.startAnimation ( fadeOut );
        }
    }

    private void createDBfile ()
    {
        try
        {
            //Add sample movie

            //Needs deprecation
            List< Movie > seedmovies = new ArrayList< Movie > ()
            {{
                    add ( new Movie (
                            "Lion King",
                            "Hakuna Matata",
                            "http://t1.gstatic.com/images?q=tbn:ANd9GcQ2vZQTR7HyXqWbjYYr0HNfAyDLRq7EXogJGAgG0bbM8odQlDLV",
                            "Capture link",
                            "Audio Link" ) );
                    add ( new Movie (
                            "My Fair Lady",
                            "Where the devil are my slippers?",
                            "http://www.teachwithmovies.org/guides/my-fair-lady-files/DVD-cover.jpg",
                            "Capture link",
                            "Audio Link" ) );
                    add ( new Movie (
                            "La vita e bella",
                            "bon giorno principezza",
                            "https://upload.wikimedia.org/wikipedia/en/8/88/Life_Is_Beautiful_cd.jpg",
                            "Capture link",
                            "Audio Link" ) );
                }};

            MovieDBHandler db = new MovieDBHandler ( this, dbFileName, null, 1 );
            for ( Movie newMovie : seedmovies )
            {
                //only add if the movie doesn't exist yet
                if ( ! db.movieExist ( newMovie ) )
                    db.addMovie ( newMovie );
            }

        }
        catch ( Exception ex )
        {
            Log.e ( "Error", "An exception occurred when creating the new database file" );
        }

    }

    public void FetchMovie ()
    {
        //get the movie in sample
        MovieDBHandler handler = new MovieDBHandler ( this, dbFileName, null, 1 );

        List< Movie > listMovies = handler.getAllMovies ();
        TextView text = ( TextView ) findViewById ( R.id.PhraseLabel );

        //get a random movie from the list of movies
        Random randomGenerator = new Random ();
        selectedMovie = listMovies.get ( randomGenerator.nextInt ( listMovies.size () ) );

        if ( selectedMovie != null )
        {
            text.setText ( selectedMovie.get_phrase () );
            loadImage ( selectedMovie.get_cover () );
        }
        else
            text.setText ( "could not find a movie" );
    }

    public void compareAnswer ( View context )
    {

        TextView text = ( TextView ) findViewById ( R.id.PhraseLabel );
        EditText inputText = ( EditText ) findViewById ( R.id.Answer );
        String answer = inputText.getText ().toString ();
        if ( compareStrings ( selectedMovie.get_name (), answer ) )
        {
            text.setText ( "Correct" );
            text.setTextColor ( Color.GREEN );
        }
        else
        {
            text.setText ( "Incorrect" );
            text.setTextColor ( Color.RED );
        }
    }

    private boolean compareStrings ( String name, String answer )
    {
        //Remove the word "the" so that The Lion King = lion king
        String correctedName = name.replaceAll ( "the|The", "" );
        String correctedAnswer = answer.replaceAll ( "the|The", "" );
        correctedAnswer = correctedAnswer.trim ();
        correctedName = correctedName.trim ();

        return correctedAnswer.equalsIgnoreCase ( correctedName );
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


    //Gestures
    @Override
    public boolean onDown ( MotionEvent e )
    {
        return true;
    }

    @Override
    public void onShowPress ( MotionEvent e )
    {

    }

    @Override
    public boolean onSingleTapUp ( MotionEvent e )
    {
        return false;
    }

    @Override
    public boolean onScroll ( MotionEvent e1, MotionEvent e2, float distanceX, float distanceY )
    {
        return false;
    }

    @Override
    public void onLongPress ( MotionEvent e )
    {

    }

    @Override
    public boolean onFling ( MotionEvent e1, MotionEvent e2, float velocityX, float velocityY )
    {
        boolean result = false;
        try {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                }
                result = true;
            }
            else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    //onSwipeBottom();
                } else {
                    //onSwipeTop();
                }
            }
            result = true;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    private void onSwipeRight ()
    {
        Log.d ( "Debug" ,"Swipe right");
    }

    private void onSwipeLeft ()
    {
        Log.d ( "Debug", "Swipe left" );
    }
}

