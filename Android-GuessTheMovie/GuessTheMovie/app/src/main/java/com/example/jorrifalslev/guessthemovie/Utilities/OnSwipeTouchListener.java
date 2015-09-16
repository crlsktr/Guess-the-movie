package com.example.jorrifalslev.guessthemovie.Utilities;

/**
 * Created by Jorri Falslev on 9/15/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class OnSwipeTouchListener extends GestureDetector implements GestureDetector.OnGestureListener
{
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    private GestureDetector mDetector;

    public OnSwipeTouchListener ( Context context, OnGestureListener listener )
    {
        super ( context, listener );
        mDetector = new GestureDetector ( context,listener );
    }


    public void onSwipeTop ()
    {

    }

    public void onSwipeBottom ()
    {
    }

    public void onSwipeLeft ()
    {
        Log.println ( Log.INFO, "Swipes", "Left swipe" );

    }

    public void onSwipeRight ()
    {
        Log.println ( Log.INFO, "Swipes", "Left swipe" );
    }

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
                    onSwipeBottom();
                } else {
                    onSwipeTop();
                }
            }
            result = true;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }
}