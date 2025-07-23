package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Tools 
{
    public static void saveAlbumsToFile(ArrayList<Album> albums)
    {
        try
        {
            BufferedWriter file = new BufferedWriter(new FileWriter("albums.csv")); //Clears albums.csv every time
            for (Album album : albums)
            {
                file.append(album.getTitle() + ",");
                file.append(album.getArtist() + ",");
                file.append(album.getGenre() + ",");
                file.append(String.valueOf(album.getYear()));

                for (Song song : album.getSongs())
                {
                    file.append("\n" + song.songWithCommas()); //Appends the song under the album
                } //Gets album from arraylist, then gets the songs arraylist from the album, the song itself, then appends the song to the file
                file.append(System.lineSeparator()); //Song signature differentiates from album
            }
            file.close();
        }
        catch(IOException ioe)
        {
            System.out.println("I/O exception: " + ioe.getMessage());
        }

    }

    public static void saveSinglesToFile(ArrayList<Single> singles)
    {
        try
        {
            BufferedWriter file = new BufferedWriter(new FileWriter("singles.csv")); //Clears single.csv every time
            for (Single single : singles)
            {
                file.append(single.singleWithCommas());
                file.append(System.lineSeparator());
            }
            file.close();
        }
        catch(IOException ioe)
        {
            System.out.println("I/O exception: " + ioe.getMessage());
        }
    }

    public static void readAlbumsFromFile(ArrayList<Album> albums)
    {
        try
        {
            int currentAlbum = 0;
            BufferedReader file = new BufferedReader(new FileReader("albums.csv"));
            String line = file.readLine();
            while (line != null)
            {
                String[] parts = line.split(",");
                if (!parts[0].equals("Song")) //If album, add
                {
                    //title, artist, genre, year
                    Album album = new Album(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
                    albums.add(album);
                    currentAlbum++;
                }
                else
                {
                    //title, length, artist
                    Song song = new Song(parts[1], Integer.parseInt(parts[2]), parts[3]);
                    albums.get(currentAlbum - 1).getSongs().add(song); //currentAlbum - 1 is the index of the album, currentAlbum gets shifted before songs are added
                }
                line = file.readLine();
            }
            file.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found: " + e.getMessage()); //If file not found, program will skip over
        }
        catch (IOException e)
        {
            System.out.println("Error reading albums: " + e.getMessage()); //If file not found, program will skip over
        }



    }


    public static void readSinglesFromFile(ArrayList<Single> singles)
    {
        try
        {
            BufferedReader file = new BufferedReader(new FileReader("singles.csv"));
            String line = file.readLine();
            while (line != null)
            {
                //title, length, artist, year, genre
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String title = parts[0];
                    int length = Integer.parseInt(parts[1]);
                    String artist = parts[2];
                    int year = Integer.parseInt(parts[3]);
                    String genre = parts[4];
                    Single single = new Single(title, length, artist, year, genre);
                    singles.add(single);
                } 
                else 
                {
                    System.out.println("Skipping malformed line in singles.csv: " + line);
                }
                line = file.readLine();
            }
            file.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found: " + e.getMessage()); //If file not found, program will skip over
        }
        catch (IOException e)
        {
            System.out.println("Error reading singles: " + e.getMessage()); //If file not found, program will skip over
        }

    }
}
    
