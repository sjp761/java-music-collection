package application;

public class Song
{
    private String title;
    private int length;
    private String artist;

    public Song(String title, int length, String artist)
    {
        this.title = title;
        this.length = length;
        this.artist = artist;
    }

    public String getTitle()
    {
        return title;
    }

    public String getTime()
    {
        return String.format("%02d:%02d", length / 60, length % 60); //MM:SS for time (album info)
    }

    public int getLength()
    {
        return length;
    }

    public String getArtist()
    {
        return artist;
    }

    public String songWithCommas() //For saving during the album save run
    {
        return "Song," + title + "," + length + "," + artist;
    }


    

}
