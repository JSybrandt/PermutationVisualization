package view;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/*
Justin Sybrandt

Purpose:
The detail pane makes it easy to add information in a horizontal manner.
If data overflows, the pane automatically scrolls.

Main details are panes, and may be whatever size. These appear first
Minor details are text and appear later

 */
public class DetailPane extends ScrollPane {

    private VBox tabPane;
    private VBox detailPane;
    private VBox meta;
    public DetailPane(){
        tabPane = new VBox();
        detailPane = new VBox();
        meta = new VBox();
        meta.getChildren().add(tabPane);
        meta.getChildren().add(new Separator());
        meta.getChildren().add(detailPane);
        setContent(meta);
        setFitToWidth(true);
    }

    public Pane addMainDetail(String title){
        if(tabPane.getChildren().size()>0)
            tabPane.getChildren().add(new Separator());
        tabPane.getChildren().add(new Label(title));
        Pane ret = new Pane();
        tabPane.getChildren().add(ret);
        return ret;
    }

    public Label addMinorDetail(String title){
        if(detailPane.getChildren().size()>0)
            detailPane.getChildren().add(new Separator());
        detailPane.getChildren().add(new Label(title));
        Label ret = new Label();
        detailPane.getChildren().add(ret);
        return ret;
    }

}
