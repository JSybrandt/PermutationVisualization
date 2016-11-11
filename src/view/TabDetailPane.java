package view;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TabDetailPane extends ScrollPane {

    private VBox tabPane;
    private VBox detailPane;
    private VBox meta;
    public TabDetailPane(){
        tabPane = new VBox();
        detailPane = new VBox();
        meta = new VBox();
        meta.getChildren().add(tabPane);
        meta.getChildren().add(new Separator());
        meta.getChildren().add(detailPane);
        setContent(meta);
        setFitToWidth(true);
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
