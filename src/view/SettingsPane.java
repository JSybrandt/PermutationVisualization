/*
* Justin Sybrandt
*
* Description:
* This pane holds an add button, two number inputs, and a variable number of comboboxes.
* The SettingsController populates the comboboxes and reads from the number inputs.
* Additionally, the SettingsController handles the add button callback.
*
* This pane is displayed across the top of the ApplicationPane.
*
* Important Values:
* addSelector - adds a combobox with a given title and a given set of values and human readable strings
* getComboBoxValue - returns the value corresponding to the selected text of a given combobox.
* */

package view;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.BiMap;

import java.util.HashMap;
import java.util.Map;

public class SettingsPane extends HBox {

    Map<String,ComboBox<String>> options;
    Map<String,BiMap<Integer,String>> comboBoxValues;
    Button addButton;
    Spinner<Integer> rowSpinner;
    Spinner<Integer> colSpinner;

    public SettingsPane(){
        addButton = new Button("Add");
        addButton.setCenterShape(true);
        addButton.prefHeightProperty().bind(this.heightProperty());
        addButton.setMinSize(Button.USE_PREF_SIZE,Button.USE_PREF_SIZE);
        rowSpinner = new Spinner<>(2,50,10);
        colSpinner = new Spinner<>(2,50,10);
        VBox vb1 = new VBox();
        vb1.getChildren().add(new Label("# Rows"));
        vb1.getChildren().add(rowSpinner);
        VBox vb2 = new VBox();
        vb2.getChildren().add(new Label("# Cols"));
        vb2.getChildren().add(colSpinner);
        getChildren().add(addButton);
        getChildren().add(vb1);
        getChildren().add(vb2);
        options = new HashMap<>();
        comboBoxValues = new HashMap<>();
        setSpacing(10);

    }

    public ComboBox<String> addSelector(String title, BiMap<Integer,String> values){
        ComboBox<String> comboBox = new ComboBox<>();
        for(String s : values.values())
            comboBox.getItems().add(s);
        options.put(title,comboBox);
        comboBoxValues.put(title,values);
        VBox vb = new VBox();
        vb.getChildren().add(new Label(title));
        vb.getChildren().add(comboBox);
        getChildren().add(vb);
        comboBox.getSelectionModel().select(0);
        return comboBox;
    }

    public Button getAddButton(){return addButton;}

    public int getComboBoxValue(String name){
        return comboBoxValues.get(name).getValue(options.get(name).getSelectionModel().getSelectedItem());
    }

    public int getRowValue(){
        return rowSpinner.getValue();
    }

    public int getColValue(){
        return colSpinner.getValue();
    }

}
