package view;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TabDetailPane extends HBox {

    private TabPane tabPane;
    private VBox detailPane;
    public TabDetailPane(){
        tabPane = new TabPane();
        tabPane.resize(200,200);
        detailPane = new VBox();
        getChildren().add(tabPane);
        getChildren().add(detailPane);
    }

    public void addTab(String title, Node value){
        Tab newTab = new Tab();
        newTab.setText(title);
        newTab.setContent(value);
    }

    public void addDetail(String title, String value){
        if(detailPane.getChildren().size()>0)
            detailPane.getChildren().add(new Separator());
        detailPane.getChildren().add(new Label(title));
        detailPane.getChildren().add(new Label(value));
    }

}
