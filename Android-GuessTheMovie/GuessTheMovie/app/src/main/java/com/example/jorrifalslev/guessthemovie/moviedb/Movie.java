package com.example.jorrifalslev.guessthemovie.moviedb;

/**
 * Created by Jorri Falslev on 8/18/2015.
 * Model for Database
 */
public class Movie
{
    private int _id;
    private String _name;
    private String _capture;
    private String _phrase;
    private String _cover;
    private String _audioclip;

    public Movie (String name, String phrase, String cover, String capture, String audioclip)
    {
        _name = name;
        _capture = capture;
        _phrase = phrase;
        _cover = cover;
        _audioclip = audioclip;
    }
    public Movie()
    {

    }

    public int get_id ()
    {
        return _id;
    }

    public void set_id ( int _id )
    {
        this._id = _id;
    }

    public String get_audioclip ()
    {
        return _audioclip;
    }

    public void set_audioclip ( String _audioclip )
    {
        this._audioclip = _audioclip;
    }

    public String get_cover ()
    {
        return _cover;
    }

    public void set_cover ( String _cover )
    {
        this._cover = _cover;
    }

    public String get_phrase ()
    {
        return _phrase;
    }

    public void set_phrase ( String _phrase )
    {
        this._phrase = _phrase;
    }

    public String get_capture ()
    {
        return _capture;
    }

    public void set_capture ( String _capture )
    {
        this._capture = _capture;
    }

    public String get_name ()
    {
        return _name;
    }

    public void set_name ( String _name )
    {
        this._name = _name;
    }
}
