/*
* Justin Sybrandt
*
* Description:
* This is the main pane. At the top is the SettingsPane, in the center is a collection of MazeContainers.
* Controlled by the Application Controller.
*
* Important Values:
* mazeContainerTilePane - stores a collection of MazeContainers and displays them based on space available.
*
* */

package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

public class ApplicationPane extends BorderPane {

    private HBox settingPane;
    private TabDetailPane tabDetailPane;
    private Pane mainVisualization;
    private Pane selectionPane;
    private Pane braidVisualization;
    public ApplicationPane(){
        tabDetailPane = new TabDetailPane();
        settingPane = new HBox();
        mainVisualization = new Pane();
        selectionPane = new Pane();
        selectionPane.mouseTransparentProperty().set(true);
        braidVisualization = new Pane();
        braidVisualization.setMinWidth(100);
        setTop(settingPane);
        setRight(tabDetailPane);
        StackPane stack = new StackPane();
        stack.getChildren().add(mainVisualization);
        stack.getChildren().add(selectionPane);
        setCenter(stack);
        setLeft(braidVisualization);
    }

    public HBox getSettingPane(){return settingPane;}

    public TabDetailPane getTabDetailPane() {
        return tabDetailPane;
    }

    public Pane getMainVisualization() {
        return mainVisualization;
    }

    public Pane getSelectionPane(){return selectionPane; }

    public Pane getBraidVisualization(){
        return braidVisualization;
    }
}
