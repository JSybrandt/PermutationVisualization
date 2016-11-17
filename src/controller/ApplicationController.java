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

import controller.option.GeneratorOption;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.*;
import view.ApplicationPane;
import view.TabDetailPane;
import view.ZoomPane;

import java.util.List;

public class ApplicationController extends Controller{

    private ApplicationPane appPane;

    private HBox settingsPane;
    private SettingsController settingsController;

    private TabDetailPane tabDetailPane;
    private PermDetailController permDetailController;

    private ZoomPane mainVis;
    private Pane selectionPane;
    private PermSetController visualizationController;

    private Pane braidVis;
    private BraidController braidController;

    private int permutationLength;
    private GeneratorOption generatorOption;

    private Permutation selectedPermutation;

    private List<Permutation> data;

    public ApplicationController(ApplicationPane root){
        super(root);
        this.appPane = root;
        createViews();
        attachControllers();
    }

    @Override
    public void run(){
        if(generatorOption != null && permutationLength > 0) {
            mainVis.reset();
            PermutationGenerator gen;
            switch (generatorOption) {
                case FACTORADIC:
                    gen = new FactoradicGenerator(permutationLength);
                    break;
                case HEAP:
                    gen = new SwapGenerator(permutationLength);
                    break;
                case INSERT:
                    gen = new InsertionGenerator(permutationLength);
                    break;
                default:
                    gen = new FactoradicGenerator(permutationLength);
            }


            if(permutationLength < 6) {
                appPane.showBraid();
                braidController.setPermLength(permutationLength);
                braidController.run();
            } else{
                appPane.hideBraid();
            }

            visualizationController.setGenerator(gen);
            visualizationController.run();
        }
    }

    private void createViews(){
        settingsPane = appPane.getSettingPane();
        tabDetailPane = appPane.getTabDetailPane();
        mainVis = appPane.getMainVisualization();
        braidVis = appPane.getBraidVisualization();
        selectionPane = appPane.getSelectionPane();

    }

    private void attachControllers(){
        settingsController = new SettingsController(settingsPane,this);
        settingsController.run();
        permDetailController = new PermDetailController(tabDetailPane);
        permDetailController.run();
        visualizationController = new PermSetController(mainVis,selectionPane,this);
        braidController = new BraidController(braidVis,this);
        appPane.setOnKeyReleased(event -> {
            if(braidVis.isVisible())
                braidController.keyTypedHandler(event);
            visualizationController.keyTypedHandler(event);
        });
    }

    public PermDetailController getPermDetailController(){
        return permDetailController;
    }

    public void setPermutationLength(int length){
        permutationLength = length;
        braidController.setPermLength(length);
    }

    public void setGeneratorOption(GeneratorOption opt){
        generatorOption = opt;
    }

    public Permutation getSelectedPerm(){
        return selectedPermutation;
    }

    public void setSelectedPerm(Permutation perm){
        selectedPermutation = perm;
        permDetailController.setPermutation(perm);
        braidController.setSelectedPerm(perm);
        visualizationController.selectPermutation(perm);
    }

}
