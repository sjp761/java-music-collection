package application;

import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage; 

public class ModifyAlbumScene 
{
    public static Scene createScene(Stage mainStage, Scene mainScene, int index, ArrayList<Album> albums)
    {
        VBox layout = new VBox(10);
        Label mainLabel = new Label("Add Album");
        Label yearLabel = new Label("Enter album year");
        Label artistLabel = new Label("Enter album artist");
        Label genreLabel = new Label("Enter album genre");
        Label nameLabel = new Label("Enter album name");

        TextField albumYearField = new TextField();
        albumYearField.setPromptText("Enter album year");

        TextField albumGenreField = new TextField();
        albumGenreField.setPromptText("Enter album genre");

        TextField albumArtistField = new TextField();
        albumArtistField.setPromptText("Enter album artist");
        
        TextField albumNameField = new TextField();
        albumNameField.setPromptText("Enter album name");
        Button saveButton = new Button("Save");
        Button exitButton = new Button("Exit");
        HBox bottomButtons = new HBox(10);
        bottomButtons.getChildren().addAll(saveButton, exitButton); //Adds the buttons to the bottom of the scene

        //-1 means new album, changes the save button to add a new album
        if (index >= 0 && index < albums.size())
        {
            Album album = albums.get(index); //Sets the scene to be a modification of an existing album
            mainLabel.setText("Edit Album"); //Reuses code from the add album scene
            albumYearField.setText(Integer.toString(album.getYear()));
            albumArtistField.setText(album.getArtist());
            albumGenreField.setText(album.getGenre());
            albumNameField.setText(album.getTitle());
        }

        exitButton.setOnAction(e -> mainStage.setScene(mainScene)); //Exits the scene and goes back to the main scene

        
        saveButton.setOnAction(e ->
        {   
            try
            {
                ArrayList<Song> songs = null;
                if (index >= 0 && index < albums.size()) {
                    songs = albums.get(index).getSongs();
                } else {
                    songs = new ArrayList<Song>();
                }
                Album newAlbum = new Album(albumNameField.getText(), 
                                           albumArtistField.getText(), 
                                           albumGenreField.getText(), 
                                           Integer.parseInt(albumYearField.getText()));
                if (index == -1)
                {
                    albums.add(newAlbum);
                }
                else if (index >= 0 && index < albums.size())
                {
                    albums.set(index, newAlbum); //Creates new album object and replaces the old one (why not just modify the old one?)
                    albums.get(index).songs = songs; //Keeps the same song list as the old album object gets disposed of
                }
                Main.fullRefresh();
                mainStage.setScene(mainScene);

            }
            catch (NumberFormatException ex)
            {
                System.out.println("Invalid year format"); //In case the user enters a non-integer value for the year
            }

        });


        layout.getChildren().addAll(mainLabel, 
                                    nameLabel, albumNameField, //Adds all the labels and text fields to the layout
                                    artistLabel, albumArtistField, 
                                    genreLabel, albumGenreField, 
                                    yearLabel, albumYearField,  
                                    bottomButtons);
        
        return new Scene(layout, 400, 350);
    }
}
