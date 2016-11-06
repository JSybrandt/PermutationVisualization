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

    private TabPane tabPane;
    private VBox detailPane;
    public TabDetailPane(){
        tabPane = new TabPane();
        tabPane.resize(200,200);
        detailPane = new VBox();
        getChildren().add(tabPane);
        getChildren().add(detailPane);
    }

    public Tab addTab(String title){
        Tab newTab = new Tab();
        newTab.setText(title);
        newTab.closableProperty().set(false);
        tabPane.getTabs().add(newTab);
        return newTab;
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
