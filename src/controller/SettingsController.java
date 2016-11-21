

package controller;

import controller.option.GeneratorOption;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;

/*
Justin Sybrandt

Purpose:
This controller is in charge of the top settings bar.
This controller sets up the help controller

Important Values:
Constructor sets up the top bar and action callbacks
 */
public class SettingsController extends Controller {

    private HelpWindowController helpController;

    public SettingsController(HBox settingsPane, ApplicationController appController) {
        super(settingsPane);
        Button refresh = new Button("REFRESH");

        //min - 2, max - 4, default - 4
        Spinner spinner = new Spinner(2,7,4);
        ComboBox cbox = new ComboBox();
        cbox.getItems().add(0,"Factoradic Generator");
        cbox.getItems().add(1,"Insertion Generator");
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
        //do nothing, everything setup in constructor
    }

    public void lauchHelp(){
        helpController.run();
    }
}
