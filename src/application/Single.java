package application;

public class Single extends Song
{
    private int year;
    private String genre;


    public Single(String title, int length, String artist, int year, String genre)
    {
        super(title, length, artist);
        this.year = year;
        this.genre = genre;
    }
    public int getYear()
    {
        return year;
    }

    public String getGenre()
    {
        return genre;
    }

    public String getInfo() //Used for displaying the song in the GUI
    {
        return "Single: " + super.getTitle() + "\n" +
               "Artist: " + super.getArtist() + "\n" +
               "Length: " + super.getTime() + "\n" +
               "Year: " + year + "\n" +
               "Genre: " + genre;
    }

    public String singleWithCommas() //Used for CSV file
    {
        return super.getTitle() + "," + 
               super.getLength() + "," +
               super.getArtist() + "," +  
               year + "," + 
               genre;
    }
}
