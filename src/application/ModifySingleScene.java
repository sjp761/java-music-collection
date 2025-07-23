package application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ModifySingleScene 
{
    public static Scene createScene(Main mainInstance, int index)
    {
        VBox layout = new VBox(10);
        Label mainLabel = new Label("Add Single");
        Label yearLabel = new Label("Enter single year");
        Label genreLabel = new Label("Enter single genre");
        Label artistLabel = new Label("Enter single artist");
        Label nameLabel = new Label("Enter single name");
        Label durationLabel = new Label("Enter single duration (in seconds)");

        TextField singleYearField = new TextField();
        singleYearField.setPromptText("Enter single year");
        TextField singleGenreField = new TextField();
        singleGenreField.setPromptText("Enter single genre");
        TextField singleArtistField = new TextField();
        singleArtistField.setPromptText("Enter single artist");
        TextField singleNameField = new TextField();
        singleNameField.setPromptText("Enter single name");
        TextField singleDurationField = new TextField();
        singleDurationField.setPromptText("Enter single duration");
        Button saveButton = new Button("Save");
        Button exitButton = new Button("Exit");
        HBox bottomButtons = new HBox(10);
        bottomButtons.getChildren().addAll(saveButton, exitButton);

        layout.getChildren().addAll(mainLabel, 
                                    nameLabel, singleNameField,
                                    artistLabel, singleArtistField,
                                    genreLabel, singleGenreField,
                                    yearLabel, singleYearField, 
                                    durationLabel, singleDurationField, 
                                    bottomButtons);

        if (index != -1) 
        {
            Single single = mainInstance.singles.get(index);
            mainLabel.setText("Edit Single");
            singleYearField.setText(Integer.toString(single.getYear()));
            singleGenreField.setText(single.getGenre());
            singleArtistField.setText(single.getArtist());
            singleNameField.setText(single.getTitle());
            singleDurationField.setText(Integer.toString(single.getLength()));
        }

        exitButton.setOnAction(e -> {
            mainInstance.mainStage.setScene(mainInstance.mainScene);
            mainInstance.fullRefresh();
        });

        saveButton.setOnAction(e -> 
        {
            try
            {
                Single single = new Single(singleNameField.getText(), 
                                           Integer.parseInt(singleDurationField.getText()), 
                                           singleArtistField.getText(), 
                                           Integer.parseInt(singleYearField.getText()), 
                                           singleGenreField.getText());
                if (index != -1) 
                {
                    mainInstance.singles.set(index, single);
                } 
                else 
                {
                    mainInstance.singles.add(single);
                }

                mainInstance.mainStage.setScene(mainInstance.mainScene);
                mainInstance.fullRefresh();
            }
            catch (NumberFormatException ex)
            {
                System.out.println("Duration and year must be numbers");
            }
        });
        return new Scene(layout, 400, 500);
    }

}
