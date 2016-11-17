/*
* Justin Sybrandt
*
* Description:
* The settings controller is in charge of populating the settingPane's comboboxes as well as
* retrieving information from that pane when the user wants to add a new maze.
*
* The SettingsController needs a reference to the ApplicationController to alert it when the user wants
* to add a new maze.
*
* Important Values:
* ALG_OPTS - A mapping between AlgorithmOption and human readable text used for display.
* MAZE_OPTS - A mapping between MazeOption and human readable text used for display.
*
* */

package controller;

import controller.option.GeneratorOption;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;

public class SettingsController extends Controller {

    HBox settingsPane;
    HelpWindowController helpController;

    public SettingsController(HBox pane, ApplicationController appController) {
        super(pane);
        settingsPane = pane;
        Button refresh = new Button("REFRESH");
        Spinner spinner = new Spinner(2,7,4);
        ComboBox cbox = new ComboBox();
        cbox.getItems().add(0,"Factoradic Generator");
        cbox.getItems().add(1,"Inserton Generator");
        cbox.getItems().add(2,"Swap Generator");
        cbox.getSelectionModel().select(0);

        refresh.setOnAction(event -> {
            appController.setPermutationLength((int)spinner.getValue());
            switch (cbox.getSelectionModel().getSelectedIndex()){
                case 0:
                    appController.setGeneratorOption(GeneratorOption.FACTORADIC);
                    break;
                case 1:
                    appController.setGeneratorOption(GeneratorOption.INSERT);
                    break;
                case 2:
                    appController.setGeneratorOption(GeneratorOption.SWAP);
                    break;
            }
            appController.run();
        });
        settingsPane.getChildren().add(refresh);
        settingsPane.getChildren().add(spinner);
        settingsPane.getChildren().add(cbox);
        Button helpButton = new Button();
        helpController = new HelpWindowController(helpButton);
        settingsPane.getChildren().add(helpButton);

    }

    @Override
    public void run() {
    }

    public void lauchHelp(){
        helpController.run();
    }
}
