package view;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

/*
Justin Sybrandt

Purpose:
This class contains the main visualization, the braid visualization, the settings pane, and the detail pane
Allows braid visualization to be shown / hid
 */
public class ApplicationPane extends BorderPane {

    private HBox settingPane;
    private DetailPane detailPane;
    private ZoomPane mainVisualization;
    private Pane selectionPane;
    private Pane braidVisualization;
    public ApplicationPane(){
        //set values
        detailPane = new DetailPane();
        settingPane = new HBox();
        mainVisualization = new ZoomPane();
        selectionPane = new Pane();
        selectionPane.mouseTransparentProperty().set(true);
        braidVisualization = new Pane();

        //size
        braidVisualization.setMinWidth(100);

        //used to place the selection pane over the main vis
        StackPane stack = new StackPane();
        stack.getChildren().add(mainVisualization);
        stack.getChildren().add(selectionPane);

        //borders made this look better
        braidVisualization.setStyle("-fx-border-color: black");
        detailPane.setStyle("-fx-border-color: black");
        selectionPane.setStyle("-fx-border-color: black");

        //set some padding on left-right of detail pane
        detailPane.setPadding(new Insets(0,5,0,5));


        //make a scrollpane to house the braid visualization
        ScrollPane sp = new ScrollPane(braidVisualization);
        sp.setFitToWidth(true);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setPrefWidth(150);
        sp.setMinWidth(150);
        sp.setMaxWidth(150);
        sp.setFocusTraversable(false);

        //place all panes in their respective spots
        setTop(settingPane);
        setRight(detailPane);
        setCenter(stack);
        setLeft(sp);
    }

    public HBox getSettingPane(){return settingPane;}

    public DetailPane getDetailPane() {
        return detailPane;
    }

    public ZoomPane getMainVisualization() {
        return mainVisualization;
    }

    public Pane getSelectionPane(){return selectionPane; }

    public Pane getBraidVisualization(){
        return braidVisualization;
    }

    public void showBraid(){
        if(getLeft() == null) {
            setLeft(braidVisualization);
            braidVisualization.setVisible(true);
        }
    }
    public void hideBraid(){
        if(getLeft() != null) {
            setLeft(null);
            braidVisualization.setVisible(false);
        }
    }
}
