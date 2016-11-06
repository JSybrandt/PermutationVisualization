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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

public class ApplicationPane extends BorderPane {

    private SettingsPane settingPane;
    private TabDetailPane tabDetailPane;
    private Pane mainVisualization;
    private Pane braidVisualization;
    public ApplicationPane(){
        tabDetailPane = new TabDetailPane();
        settingPane = new SettingsPane();
        mainVisualization = new Pane();
        braidVisualization = new Pane();
        braidVisualization.setMinWidth(100);
        setTop(settingPane);
        setRight(tabDetailPane);
        setCenter(mainVisualization);
        setLeft(braidVisualization);
    }

    public SettingsPane getSettingPane(){return settingPane;}

    public TabDetailPane getTabDetailPane() {
        return tabDetailPane;
    }

    public Pane getMainVisualization() {
        return mainVisualization;
    }

    public Pane getBraidVisualization(){
        return braidVisualization;
    }
}
