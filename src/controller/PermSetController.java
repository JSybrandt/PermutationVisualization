package controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import model.Permutation;
import model.PermutationGenerator;
import util.Vec2;

import java.util.*;

import static util.FactorialMath.factorial;

/**
 * Created by jsybran on 11/6/2016.
 */
public class PermSetController extends Controller {
    Pane visualizationPane;
    ApplicationController parentController;
    PermutationGenerator generator;
    final double PERM_ICON_RADIUS = 15;
    Map<Permutation, Shape> perm2Shape;
    public PermSetController(Pane visualizationPane, ApplicationController parent) {
        super(visualizationPane);
        this.visualizationPane = visualizationPane;
        this.parentController = parent;
        visualizationPane.widthProperty().addListener(observable -> run());
        visualizationPane.heightProperty().addListener(observable -> run());
    }

    public void setGenerator(PermutationGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void run(){
        if(generator == null) return;
        visualizationPane.getChildren().clear();
        perm2Shape = new HashMap<>();
        List<Permutation> data = generator.generate();
        if(generator.getPermutationSize() <= 4){
            displaySmall(data);
        }else if (generator.getPermutationSize() <= 5){
            displayMed(data);
        }else{
            displayLarge(data);
        }
    }

    //graph
    private void displaySmall(List<Permutation> data){

        Map<Permutation,Vec2> locs = new HashMap<>();
        Map<Permutation, List<Permutation>> neighbors = new HashMap<>();
        double angle = 0;
        double angleDiff = (Math.PI * 2) / data.size();
        double radius = Math.min(visualizationPane.getHeight(),visualizationPane.getWidth())/2.5;
        Vec2 vec = new Vec2(0,radius);
        Vec2 center = new Vec2(visualizationPane.getWidth()/2, visualizationPane.getHeight()/2);
        for(Permutation perm : data){
            neighbors.put(perm,getAdjacentPerms(perm));
            locs.put(perm,vec.rotate(angle).plus(center));
            angle += angleDiff;

        }
        for(Permutation perm : data){
            perm2Shape.put(perm, placePermIcon(locs.get(perm),perm));
        }
        for(Permutation source : data){
            for(Permutation target : neighbors.get(source)){
                Shape sLoc = perm2Shape.get(source);
                Shape tLoc = perm2Shape.get(target);
                Line line = new Line();
                line.startXProperty().bind(sLoc.layoutXProperty());
                line.startYProperty().bind(sLoc.layoutYProperty());
                line.endXProperty().bind(tLoc.layoutXProperty());
                line.endYProperty().bind(tLoc.layoutYProperty());
                visualizationPane.getChildren().add(line);
                line.toBack();
            }
        }


    }
    private void displayMed(List<Permutation> data){

    }
    //color
    private void displayLarge(List<Permutation> data){

    }

    private Shape placePermIcon(Vec2 loc, Permutation perm){
        Circle shape = new Circle(PERM_ICON_RADIUS,getFactoradicColor(perm));
        shape.setOnMousePressed(event -> parentController.setSelectedPerm(perm));
        visualizationPane.getChildren().add(shape);
        shape.setLayoutX(loc.X());
        shape.setLayoutY(loc.Y());
        Tooltip tooltip = new Tooltip(perm.toString());
        Tooltip.install(shape,tooltip);

        EventHandler<MouseEvent> dragHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                shape.setLayoutX(event.getSceneX());
                shape.setLayoutY(event.getSceneY());
            }
        };

        shape.setOnMouseDragged(dragHandler);
        return shape;
    }


    private List<Permutation> getAdjacentPerms(Permutation source){
        int[] data = source.getData();
        List<Permutation> adjPerms = new ArrayList<>();
        for(int i  = 1 ; i < data.length; i++){
            int[] cp = Arrays.copyOf(data,data.length);
            int tmp = cp[i-1];
            cp[i-1] = cp[i];
            cp[i] = tmp;
            adjPerms.add(new Permutation(cp));
        }
        return adjPerms;
    }

    private Color getFactoradicColor(Permutation perm){

        //plus 1's used so that no perm is white
        int factNum = perm.getFactoradic()+1;
        int maxFact = factorial(generator.getPermutationSize())+1;
        double ratio = (double) factNum / (double) maxFact;


        return Color.hsb(100,ratio,1);//note: 100 is for green
    }

    private Permutation prevSelection;
    public void selectPermutation(Permutation perm){
        if(perm2Shape != null && perm2Shape.containsKey(perm)){
            if(prevSelection != null)
                perm2Shape.get(prevSelection).setStrokeWidth(0);
            perm2Shape.get(perm).setStroke(Color.RED);
            perm2Shape.get(perm).setStrokeWidth(2);
            //perm2Shape.get(perm).setFill(Color.RED);
            prevSelection = perm;
        }
    }

}
