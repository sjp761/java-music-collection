package application;

import java.util.ArrayList;

public class Album 
{
    public ArrayList<Song> songs;
    private String title;
    private String artist;
    private String genre;
    private int year;

    public Album(String title, String artist, String genre, int year)
    {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.year = year;
        songs = new ArrayList<Song>(); //Album has a song (composition)
    }

    public String albumInfo()
    {
        String info = "Album: " + title + "\n" +
                     "Artist: " + artist + "\n" +
                     "Genre: " + genre + "\n" +
                     "Year: " + year + "\n" +
                     "Songs: ";
        for (Song song : songs)
        {
            info += "\n\t" + song.getTitle(); //Loops through the song arraylist, indented for readability
        }
        return info;
    }

    public String getTitle()
    {
        return title;
    }

    public String getArtist()
    {
        return artist;
    }

    public String getGenre()
    {
        return genre;
    }

    public int getYear()
    {
        return year;
    }

    public ArrayList<Song> getSongs() //Used for accessing the song list for modification, keeps things simpler
    {
        return songs;
    }

    public void setSongList(ArrayList<Song> songs) //Used for setting the song list when reading from a file
                                                   //Also for changing album attributes as a new album is created when changing album info (should probably change this)
    {
        this.songs = songs;
    }
}
