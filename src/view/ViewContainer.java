/*
* Justin Sybrandt
*
* Description:
* Used to display a maze along with a title and a close button. This is created and managed by the Application
* Controller, and is displayed inside the ApplicationPane.
*
* This class handles the layout of its contained elements, and ensures that the mazeCanvas is set to an
* appropriate size.
*
* Important Values:
* DEFAULT_SIZE - determines the height and width of a container.
* closeButton - the X button at the top right corner used to delete this object.
*               the applicationController handles this callback.
* */

package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ViewContainer extends BorderPane{

    private static double DEFAULT_SIZE = 400;

    private Label title;
    private Pane enclosedPane;
    private Button closeButton;
    public ViewContainer(String title, Pane pane){
        this.title = new Label(title);
        this.enclosedPane = pane;
        this.closeButton = new Button("X");
        BorderPane header = new BorderPane();
        header.setLeft(this.title);
        header.setRight(this.closeButton);
        HBox.setHgrow(this.title, Priority.ALWAYS);
        HBox.setHgrow(this.closeButton, Priority.NEVER);
        HBox.setHgrow(header, Priority.ALWAYS);
        setTop(header);
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        //Pane needed to enlarge the maze pane
        setCenter(this.enclosedPane);
        setPrefHeight(DEFAULT_SIZE);
        setPrefWidth(DEFAULT_SIZE);
    }

    public Pane getPane(){return enclosedPane;}
    public Button getCloseButton(){return closeButton;}

}
