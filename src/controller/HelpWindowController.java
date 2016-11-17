package controller;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jsybran on 11/11/2016.
 */
public class HelpWindowController extends Controller {

    List<String> helpImages;
    Button helpButton;
    int currImage =0;
    ImageView imgView = new ImageView();

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
    }

    private List<String> generateHelpImages(){
        ArrayList<String> res = new ArrayList<>();
        //res.add("helpgifs/Untitled.png");
        res.add("helpgifs/advancedSelection.gif");
        //res.add("helpgifs/changePermSize.gif");
        res.add("helpgifs/generatorDescription.gif");
        res.add("helpgifs/pan&zoom.gif");
        res.add("helpgifs/select_click&drag.gif");
        return res;
    }

    private void updateImage(){
        if(currImage < 0){
            currImage = 0;
        }
        else if (currImage > helpImages.size()-1){
            currImage = helpImages.size() - 1;
        }
        else {
            System.out.println("Display:" + helpImages.get(currImage));
            imgView.setImage(new Image(helpImages.get(currImage)));
            imgView.setFitHeight(500);
            imgView.setFitWidth(600);
        }
    }

    private Pane getModalPane(){
        BorderPane pane = new BorderPane();
        pane.setCenter(imgView);
        BorderPane npPane = new BorderPane();
        Button nextButton = new Button("Next");
        Button prevButton = new Button("Prev");
        npPane.setLeft(prevButton);
        npPane.setRight(nextButton);
        pane.setTop(npPane);
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
