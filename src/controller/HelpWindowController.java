package controller;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by jsybran on 11/11/2016.
 */
public class HelpWindowController extends Controller {
    public HelpWindowController(Button helpButton) {
        super(helpButton);
        helpButton.setText("Get Help");
        helpButton.setOnAction(event -> {
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

    private Pane getModalPane(){
        BorderPane pane = new BorderPane();
        ImageView imageView = new ImageView();
        Image image = new Image("help.png");
        imageView.setImage(image);
        pane.setCenter(imageView);
        return pane;
    }
}
