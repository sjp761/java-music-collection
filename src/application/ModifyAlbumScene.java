package application;

import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ModifyAlbumScene 
{
    public static Scene createScene(Main mainInstance, int index)
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
        bottomButtons.getChildren().addAll(saveButton, exitButton);

        //-1 means new album, changes the save button to add a new album
        if (index >= 0 && index < mainInstance.albums.size())
        {
            Album album = mainInstance.albums.get(index);
            mainLabel.setText("Edit Album");
            albumYearField.setText(Integer.toString(album.getYear()));
            albumArtistField.setText(album.getArtist());
            albumGenreField.setText(album.getGenre());
            albumNameField.setText(album.getTitle());
        }

        exitButton.setOnAction(e -> {
            mainInstance.mainStage.setScene(mainInstance.mainScene);
            mainInstance.fullRefresh();
        });

        saveButton.setOnAction(e ->
        {   
            try
            {
                ArrayList<Song> songs = null;
                if (index >= 0 && index < mainInstance.albums.size()) {
                    songs = mainInstance.albums.get(index).getSongs();
                } else {
                    songs = new ArrayList<Song>();
                }
                Album newAlbum = new Album(albumNameField.getText(), 
                                           albumArtistField.getText(), 
                                           albumGenreField.getText(), 
                                           Integer.parseInt(albumYearField.getText()));
                if (index == -1)
                {
                    mainInstance.albums.add(newAlbum);
                }
                else if (index >= 0 && index < mainInstance.albums.size())
                {
                    mainInstance.albums.set(index, newAlbum);
                    mainInstance.albums.get(index).songs = songs;
                }
                mainInstance.mainStage.setScene(mainInstance.mainScene);
                mainInstance.fullRefresh();
            }
            catch (NumberFormatException ex)
            {
                System.out.println("Invalid year format");
            }
        });

        layout.getChildren().addAll(mainLabel, 
                                    nameLabel, albumNameField,
                                    artistLabel, albumArtistField, 
                                    genreLabel, albumGenreField, 
                                    yearLabel, albumYearField,  
                                    bottomButtons);
        
        return new Scene(layout, 400, 350);
    }
}
