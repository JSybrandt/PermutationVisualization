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

import util.BiMap;
import view.SettingsPane;

public class SettingsController extends Controller {

    SettingsPane settingsPane;


    public SettingsController(SettingsPane pane, ApplicationController appController) {
        super(pane);
        settingsPane = pane;

    }

    @Override
    public void run() {

    }

}
