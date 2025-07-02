package application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;

public class AddSongsScene 
{
 public static Scene createScene(Stage mainStage, Scene mainScene, Album album)
 {
      BorderPane layout = new BorderPane();
      ComboBox<String> songList = new ComboBox<>();
      Button btnExit = new Button("Exit");
      Button btnAdd = new Button("Add/Save");
      Button btnRemove = new Button("Remove");
      TextField txtSongName = new TextField();
      TextField txtArtistName = new TextField();
      TextField txtLength = new TextField();
      VBox songInfo = new VBox();
      VBox addRemoveBox = new VBox();
      addRemoveBox.getChildren().addAll(btnAdd, btnRemove);
      songInfo.getChildren().addAll(songList, 
                                     new Label("Song Name"), txtSongName, 
                                     new Label("Artist Name"), txtArtistName, 
                                     new Label("Length (in seconds)"), txtLength);
      layout.setLeft(songInfo);
      layout.setBottom(btnExit);
      layout.setRight(addRemoveBox);
      refreshSongList(songList, album);

      songList.setOnAction(e -> {
         if (songList.getValue().equals("Add new ..."))
         {
            txtSongName.setText("");
            txtArtistName.setText("");
            txtLength.setText("");
         }
         
         else
         {
            int index = songList.getSelectionModel().getSelectedIndex();
            Song song = album.getSongs().get(index);
            txtSongName.setText(song.getTitle());
            txtArtistName.setText(song.getArtist());
            txtLength.setText(String.valueOf(song.getLength()));
         }
      });

      btnRemove.setOnAction(e -> {
         if (songList.getValue().equals("Add new ..."))
         {
            return;
         }
         int index = songList.getSelectionModel().getSelectedIndex();
         album.getSongs().remove(index);
         refreshSongList(songList, album);
      });

      btnExit.setOnAction(e -> {
         Main.fullRefresh();
         mainStage.setScene(mainScene);
      });

      btnAdd.setOnAction(e -> {
         try
         {
         if (songList.getValue().equals("Add new ...")) //Basically a check for new vs Modify
         {
            album.getSongs().add(new Song(txtSongName.getText(), //title
                                          Integer.parseInt(txtLength.getText()), //length
                                          txtArtistName.getText())); //artist
         }
         else
         {                                                     //Index of the song to modify
            album.getSongs().set(songList.getSelectionModel().getSelectedIndex(), new Song(txtSongName.getText(), 
                                                                                  Integer.parseInt(txtLength.getText()), 
                                                                                  txtArtistName.getText()));
         }       
         refreshSongList(songList, album);
         } 
         catch (NumberFormatException ex) 
         {
            System.out.println("Invalid number for length");
         }
      });
      
      

    return new Scene(layout, 300, 450);
   }

 public static void refreshSongList(ComboBox<String> songList, Album album)
 {
    songList.getItems().clear(); //Clears the list and repopulates it after the song is added or modified
    for (Song song : album.getSongs())
    {
        songList.getItems().add(song.getTitle());
    }
    songList.getItems().add("Add new ...");
    songList.setValue("Add new ..."); //Makes things slightly easier to code 
 }
    
}

