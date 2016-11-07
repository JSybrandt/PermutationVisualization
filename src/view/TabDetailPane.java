package view;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TabDetailPane extends VBox {

    private VBox tabPane;
    private VBox detailPane;
    public TabDetailPane(){
        tabPane = new VBox();
        tabPane.resize(200,200);
        detailPane = new VBox();
        getChildren().add(tabPane);
        getChildren().add(detailPane);
    }

    public Pane addTab(String title){
        if(tabPane.getChildren().size()>0)
            tabPane.getChildren().add(new Separator());
        tabPane.getChildren().add(new Label(title));
        Pane ret = new Pane();
        tabPane.getChildren().add(ret);
        return ret;
    }

    public Label addDetail(String title){
        if(detailPane.getChildren().size()>0)
            detailPane.getChildren().add(new Separator());
        detailPane.getChildren().add(new Label(title));
        Label ret = new Label();
        detailPane.getChildren().add(ret);
        return ret;
    }

}
