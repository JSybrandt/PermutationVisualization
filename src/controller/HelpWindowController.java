package controller;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/*
Justin Sybrandt

Purpose:
    This controller responds to the help button being pressed. Creates a modal dialog which shows help information.
    This controller is used on startup as well to show the same help information.

Important Values:
    helpImages - holds paths (relative to ./src) for images in the order they should appear.
    getModalPane - creates the graphical portion of the help images
 */
public class HelpWindowController extends Controller {

    List<String> helpImages;
    Button helpButton;
    int currImage =0;
    ImageView imgView = new ImageView();
    Label imgTitle = new Label();

    public HelpWindowController(Button helpButton) {
        super(helpButton);
        this.helpButton = helpButton;
        helpButton.setText("Get Help");
        helpImages = generateHelpImages();
        helpButton.setOnAction(event -> {
            updateImage();
            Stage modal = new Stage();
            Pane modalPane = getModalPane();
            modal.setScene(new Scene(modalPane));
            modal.setTitle("Help Information");
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(((Node)event.getSource()).getScene().getWindow());
            modal.show();
        });

    }

    @Override
    public void run() {
        helpButton.fire();
    }

    private List<String> generateHelpImages(){
        ArrayList<String> res = new ArrayList<>();

        res.add("helpgifs/select_click&drag.gif");//*
        res.add("helpgifs/advancedSelection.gif");
        res.add("helpgifs/pan&zoom.gif");
        res.add("helpgifs/generatorDescription.gif");//*
        res.add("helpgifs/changePermSize.gif");
        return res;
    }

    //changes image displayed on imageview
    private void updateImage(){
        if(currImage < 0){
            currImage = 0;
        }
        else if (currImage > helpImages.size()-1){
            currImage = helpImages.size() - 1;
        }
        else {
            imgView.setImage(new Image(helpImages.get(currImage)));
            imgView.setFitHeight(716);
            imgView.setFitWidth(1131);
            imgTitle.setText((currImage+1) + "/" + (helpImages.size()));
        }
    }

    //makes modal dialog. Border pane with next/prev buttons and a large image
    private Pane getModalPane(){
        BorderPane pane = new BorderPane();
        pane.setCenter(imgView);
        BorderPane npPane = new BorderPane();
        Button nextButton = new Button("Next");
        Button prevButton = new Button("Prev");
        nextButton.setFont(new Font(24));
        prevButton.setFont(new Font(24));
        imgTitle.setFont(new Font(24));
        npPane.setLeft(prevButton);
        npPane.setRight(nextButton);
        npPane.setCenter(imgTitle);
        npPane.setStyle("-fx-border-color: black");
        pane.setTop(npPane);

        //buttons should update images
        nextButton.setOnAction(event->{
            currImage++;
            updateImage();
        });
        prevButton.setOnAction(event->{
            currImage--;
            updateImage();
        });
        return pane;
    }
}
