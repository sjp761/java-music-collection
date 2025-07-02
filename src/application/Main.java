package application;
	
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;




public class Main extends Application {
	//btn = Button
	//cb = ComboBox
	//ta = TextArea
	Button btnAddAlbum = new Button("Add Album");
	Button btnAddSingle = new Button("Add Single");
	Button btnManageAlbum = new Button("Manage Album");
	Button btnManageSingle = new Button("Manage Single");
	Button btnDeleteAlbum = new Button("Delete Album");
	Button btnDeleteSingle = new Button("Delete Single");
	Button btnViewAlbumSongs = new Button("Manage Album Songs");
	Button btnBST = new Button("BST of Singles");
	ComboBox<String> cbAlbumOrSingle = new ComboBox<>();
	static ComboBox<String> cbAlbumList = new ComboBox<>();
	static ComboBox<String> cbSingleList = new ComboBox<>();
	static TextArea taChosenInfo = new TextArea();
	static ArrayList<Album> albums = new ArrayList<>();
	static ArrayList<Single> singles = new ArrayList<>();
	static int albumIndex = -1; //Index of the album Combobox (-1 is unselected)
	static int singleIndex = -1; //Index of the single Combobox (-1 is unselected)
	//Static for the fullRefresh method
	

	@Override
	public void start(Stage primaryStage) {
		try {
			Tools.readAlbumsFromFile(albums); //Loads the albums from the file
			Tools.readSinglesFromFile(singles); //Loads the singles from the file
			taChosenInfo.setEditable(false);
			taChosenInfo.setWrapText(true);
			VBox albumButtons = new VBox(btnAddAlbum, btnManageAlbum, btnDeleteAlbum, btnViewAlbumSongs, btnBST); //VBox for albums (right pane)
			VBox singleButtons = new VBox(btnAddSingle, btnManageSingle, btnDeleteSingle, btnBST); //VBox for singles (right pane)
			VBox infoBox = new VBox(cbAlbumOrSingle, cbAlbumList, cbSingleList, taChosenInfo); //VBox for song list and info (left pane)
			cbAlbumOrSingle.getItems().addAll("Album", "Single");
			cbAlbumOrSingle.setValue("Album");
			cbSingleList.setVisible(false);			
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,700,400);
			root.setRight(albumButtons);
			root.setLeft(infoBox);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setOnCloseRequest(e -> {Tools.saveAlbumsToFile(albums);
												 Tools.saveSinglesToFile(singles);});

			Main.fullRefresh(); //Refreshes the album and single list

			cbAlbumList.setOnAction(e -> {
				albumIndex = cbAlbumList.getSelectionModel().getSelectedIndex();
				if (albumIndex != -1) {
					taChosenInfo.setText(albums.get(albumIndex).albumInfo());
				}
			});

			cbSingleList.setOnAction(e -> {
				singleIndex = cbSingleList.getSelectionModel().getSelectedIndex();
				if (singleIndex != -1) {
					taChosenInfo.setText(singles.get(singleIndex).getInfo());
				}
			});

			cbAlbumOrSingle.setOnAction(e -> {
				if (cbAlbumOrSingle.getValue().equals("Album")) 
				{
					root.setRight(albumButtons);
					cbSingleList.setVisible(false);
					cbAlbumList.setVisible(true);
				} 
				else 
				{
					root.setRight(singleButtons);
					cbSingleList.setVisible(true);
					cbAlbumList.setVisible(false);
				}
			});


			btnAddAlbum.setOnAction(e -> {
				Scene addScene = ModifyAlbumScene.createScene(primaryStage, scene, -1 , albums);
				primaryStage.setScene(addScene);
			});

			btnManageAlbum.setOnAction(e -> {
				if (albumIndex == -1) //Makes sure an album is selected
				{
					taChosenInfo.setText("Please select an album to manage");
					return;
				}
				primaryStage.setScene(ModifyAlbumScene.createScene(primaryStage, scene, albumIndex, albums));
				
			});

			
			
			btnDeleteAlbum.setOnAction(e -> {
				if (albumIndex != -1) //Makes sure an album is selected
				{
					albums.remove(albumIndex);
					fullRefresh();
					taChosenInfo.setText("");
				}});

			btnViewAlbumSongs.setOnAction(e -> {
				if (albumIndex != -1)
				{
					primaryStage.setScene(AddSongsScene.createScene(primaryStage, scene, albums.get(albumIndex))); //Opens the add songs scene, possible idea is to make it so it is not recreated every time
				}
				else
				{
					System.out.println("Please select an album to manage");
				}
			});

			btnAddSingle.setOnAction(e -> {
				primaryStage.setScene(ModifySingleScene.createScene(primaryStage, scene, -1 , singles));
			});

			btnManageSingle.setOnAction(e -> {
				primaryStage.setScene(ModifySingleScene.createScene(primaryStage, scene, singleIndex, singles));
			});

			btnBST.setOnAction(e -> 
			{
				BinaryTree tree = new BinaryTree();
				for (int i = 0; i < singles.size(); i++)
				{
					tree.insert(singles.get(i).getTitle());
				}
				taChosenInfo.setText("In Order: " + tree.inorder() + "\n" + 
									 "Pre Order: "+ tree.preorder() + "\n" +
									 "Post Order: "+ tree.postorder());
			});

			btnDeleteSingle.setOnAction(e -> {
				if (singleIndex != -1) //Makes sure a single is selected
				{
					singles.remove(singleIndex);
					fullRefresh();
					taChosenInfo.setText("");
				}});

			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public static void fullRefresh() //Clears the list of album and singles then, repopulates them
                                     //Maybe could change this so that when a new album is created, the combobox gets selected right to it  
    {
        Main.taChosenInfo.clear();
        Main.albumIndex = -1;         
        Main.singleIndex = -1;
        if (Main.albums.size() == 0)
        {
            Main.cbAlbumList.getItems().clear();
        }
        else
        {
            Main.cbAlbumList.getItems().clear();
            for (int i = 0; i < Main.albums.size(); i++)
            {
                Main.cbAlbumList.getItems().add(Main.albums.get(i).getTitle());
            }
        }

        if (Main.singles.size() == 0)
        {
            Main.cbSingleList.getItems().clear();
        }
        else
        {
            Main.cbSingleList.getItems().clear();
            for (int i = 0; i < Main.singles.size(); i++)
            {
                Main.cbSingleList.getItems().add(Main.singles.get(i).getTitle());
            }
        }

    }
}
