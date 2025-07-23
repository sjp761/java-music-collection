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
    ComboBox<String> cbAlbumList = new ComboBox<>();
    ComboBox<String> cbSingleList = new ComboBox<>();
    TextArea taChosenInfo = new TextArea();
    ArrayList<Album> albums = new ArrayList<>();
    ArrayList<Single> singles = new ArrayList<>();
	Stage mainStage; // Main stage for the application
	Scene mainScene; // Main scene for the application
    int albumIndex = -1; //Index of the album Combobox (-1 is unselected)
    int singleIndex = -1; //Index of the single Combobox (-1 is unselected)
    //Static for the fullRefresh method

    @Override
    public void start(Stage primaryStage) {
        try {
			mainStage = primaryStage; // Initialize mainStage
            loadData();
            setupUI(primaryStage);
            setupEventHandlers(primaryStage);
            fullRefresh();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        Tools.readAlbumsFromFile(albums); //Loads the albums from the file
        Tools.readSinglesFromFile(singles); //Loads the singles from the file
    }

    private void setupUI(Stage primaryStage) {
        taChosenInfo.setEditable(false);
        taChosenInfo.setWrapText(true);
        

        VBox infoBox = createInfoPane();
        
        cbAlbumOrSingle.getItems().addAll("Album", "Single");
        cbAlbumOrSingle.setValue("Album");
        cbSingleList.setVisible(false);
        
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 700, 400);
        root.setRight(createAlbumButtonsPane());
        root.setLeft(infoBox);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        mainScene = scene; // Store the main scene for later use

        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> saveDataOnExit());
        primaryStage.show();
    }

    private VBox createAlbumButtonsPane() {
        return new VBox(btnAddAlbum, btnManageAlbum, btnDeleteAlbum, btnViewAlbumSongs, btnBST);
    }

    private VBox createSingleButtonsPane() {
        return new VBox(btnAddSingle, btnManageSingle, btnDeleteSingle, btnBST);
    }

    private VBox createInfoPane() {
        return new VBox(cbAlbumOrSingle, cbAlbumList, cbSingleList, taChosenInfo);
    }

    private void saveDataOnExit() {
        Tools.saveAlbumsToFile(albums);
        Tools.saveSinglesToFile(singles);
    }

    private void setupEventHandlers(Stage primaryStage) {
        setupComboBoxHandlers(primaryStage);
        setupAlbumButtonHandlers(primaryStage);
        setupSingleButtonHandlers(primaryStage);
        setupBSTButtonHandler();
    }

    private void setupComboBoxHandlers(Stage primaryStage) {
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

        cbAlbumOrSingle.setOnAction(e -> handleAlbumOrSingleSelection(primaryStage));
    }

    private void handleAlbumOrSingleSelection(Stage primaryStage) {
        Scene currentScene = primaryStage.getScene();
        BorderPane root = (BorderPane) currentScene.getRoot();
        
        if (cbAlbumOrSingle.getValue().equals("Album")) {
            root.setRight(createAlbumButtonsPane());
            cbSingleList.setVisible(false);
            cbAlbumList.setVisible(true);
        } else {
            root.setRight(createSingleButtonsPane());
            cbSingleList.setVisible(true);
            cbAlbumList.setVisible(false);
        }
    }

    private void setupAlbumButtonHandlers(Stage primaryStage) {
        btnAddAlbum.setOnAction(e -> {
            Scene addScene = ModifyAlbumScene.createScene(this, -1);
            primaryStage.setScene(addScene);
        });

        btnManageAlbum.setOnAction(e -> {
            if (albumIndex == -1) {
                taChosenInfo.setText("Please select an album to manage");
                return;
            }
            primaryStage.setScene(ModifyAlbumScene.createScene(this, albumIndex));
        });

        btnDeleteAlbum.setOnAction(e -> {
            if (albumIndex != -1) {
                albums.remove(albumIndex);
                fullRefresh();
                taChosenInfo.setText("");
            }
        });

        btnViewAlbumSongs.setOnAction(e -> {
            if (albumIndex != -1) {
                primaryStage.setScene(AddSongsScene.createScene(this, albums.get(albumIndex)));
            } else {
                taChosenInfo.setText("Please select an album to manage");
            }
        });
    }

    private void setupSingleButtonHandlers(Stage primaryStage) {
        btnAddSingle.setOnAction(e -> {
            primaryStage.setScene(ModifySingleScene.createScene(this, -1));
        });

        btnManageSingle.setOnAction(e -> {
            if (singleIndex == -1) {
                taChosenInfo.setText("Please select a single to manage");
                return;
            }
            primaryStage.setScene(ModifySingleScene.createScene(this, singleIndex));
        });

        btnDeleteSingle.setOnAction(e -> {
            if (singleIndex != -1) {
                singles.remove(singleIndex);
                fullRefresh();
                taChosenInfo.setText("");
            }
        });
    }

    private void setupBSTButtonHandler() {
        btnBST.setOnAction(e -> {
            BinaryTree tree = new BinaryTree();
            for (Single single : singles) {
                tree.insert(single.getTitle());
            }
            taChosenInfo.setText("In Order: " + "\n" + tree.inorder() + "\n" + 
                                 "Pre Order: " + "\n" + tree.preorder() + "\n" +
                                 "Post Order: " + "\n" + tree.postorder());
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void fullRefresh() 
	{
        taChosenInfo.clear();
        albumIndex = -1;         
        singleIndex = -1;

        cbAlbumList.getItems().clear();
        for (Album album : albums) {
            cbAlbumList.getItems().add(album.getTitle());
        }
        
        cbSingleList.getItems().clear();
        for (Single single : singles) {
            cbSingleList.getItems().add(single.getTitle());
        }
    }
}
