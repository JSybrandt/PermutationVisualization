/*
* Justin Sybrandt
*
* Description:
* The application controller is responsible for initializing the views and controllers.
* This meta controller also adds mazes to the application pane when given a maze description.
*
* Important Values:
* appPane - the main view.
* mazeController2View - stores a collection of maze controllers and views.
*                       The map structure allows for easy storage of pairs.
*
*
* */


package controller;

import view.ApplicationPane;
import view.SettingsPane;
import view.ViewContainer;
import view.visualization.PermVis;

import java.util.HashMap;
import java.util.Map;

public class ApplicationController extends Controller{

    ApplicationPane appPane;
    Map<Controller, ViewContainer> controller2Container;

    SettingsPane settingsPane;
    SettingsController settingsController;

    public ApplicationController(ApplicationPane root){
        super(root);
        this.appPane = root;
        controller2Container = new HashMap<>();
    }

    @Override
    public void run(){
        createViews();
        attachControllers();

    }

    private void createViews(){

        settingsPane = appPane.getSettingPane();
    }

    private void attachControllers(){
        settingsController = new SettingsController(settingsPane,this);
        settingsController.run();
    }

    //called by other controllers
    public void addVisualization(String title, PermVis visualization){

    }
}
