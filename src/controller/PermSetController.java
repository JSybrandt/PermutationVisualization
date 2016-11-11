package controller;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
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
    Pane selectionPane;
    ApplicationController parentController;
    PermutationGenerator generator;
    final double PERM_ICON_RADIUS = 15;
    final double SELECTION_DOT_RADIUS = 6;
    Map<Permutation, Shape> perm2Shape;
    final Color INV_COLOR = Color.BLUE;
    public PermSetController(Pane visualizationPane, Pane selectionPane, ApplicationController parent) {
        super(visualizationPane);
        this.visualizationPane = visualizationPane;
        this.parentController = parent;
        this.selectionPane = selectionPane;
        visualizationPane.widthProperty().addListener(observable -> resize());
        visualizationPane.heightProperty().addListener(observable -> resize());
    }

    public void resize(){
        visualizationPane.getChildren().clear();
    }

    public void setGenerator(PermutationGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void run(){
        if(generator == null) return;
        prevSelection = null;
        visualizationPane.getChildren().clear();
        selectionPane.getChildren().clear();
        perm2Shape = new HashMap<>();
        List<Permutation> data = generator.generate();
        if(generator.getPermutationSize() <= 4){
            displaySmall(data);
        }else if (generator.getPermutationSize() <= 5){
            displayMed(data);
        }else{
            Platform.runLater(new Runnable() {
                                  @Override
                                  public void run() {
                                      displayLarge(data);
                                  }
                              });
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
            neighbors.put(perm,perm.getAdjacentPerms());
            locs.put(perm,vec.rotate(angle).plus(center));
            angle += angleDiff;

        }
        for(Permutation perm : data){
            perm2Shape.put(perm, placePermNode(locs.get(perm),perm));
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
            Permutation target = source.getInverse();
            Shape sLoc = perm2Shape.get(source);
            Shape tLoc = perm2Shape.get(target);
            Line line = new Line();
            line.startXProperty().bind(sLoc.layoutXProperty());
            line.startYProperty().bind(sLoc.layoutYProperty());
            line.endXProperty().bind(tLoc.layoutXProperty());
            line.endYProperty().bind(tLoc.layoutYProperty());
            line.setStroke(INV_COLOR);
            visualizationPane.getChildren().add(line);
            line.toBack();
        }


    }
    private void displayMed(List<Permutation> data){
        displaySmall(data);
    }
    //color
    private void displayLarge(List<Permutation> data){
        int sideLength = (int) Math.ceil(Math.sqrt(data.size()));

        double width = visualizationPane.getWidth();
        double height = visualizationPane.getHeight();

        if(width > 0 && height > 0) {

            Vec2 size = new Vec2(width / sideLength, height / sideLength);

            Vec2 index = new Vec2(0, 0);

            for (int i = 1; i <= data.size(); i++) {
                perm2Shape.put(data.get(i - 1), placePermCell(index, size, data.get(i - 1)));
                if (i % sideLength == 0) {
                    index = new Vec2(0, index.Y() + size.Y());
                } else
                    index = new Vec2(index.X() + size.X(), index.Y());
            }
        }

    }

    private void placePermLabel(Shape s, Permutation perm){
        int numFixed = perm.getNumFixed();
        if(numFixed==0)return;
        Label label = new Label(Integer.toString( perm.getNumFixed()));
        label.setCenterShape(true);
        label.layoutXProperty().bind(s.layoutXProperty());
        label.layoutYProperty().bind(s.layoutYProperty());
        visualizationPane.getChildren().add(label);
        label.toFront();
    }

    private Shape placePermCell(Vec2 loc, Vec2 size, Permutation perm){
        Shape rect = new Rectangle(0,0, size.X(), size.Y());
        rect.setLayoutX(loc.X());
        rect.setLayoutY(loc.Y());
        rect.setOnMousePressed(event -> parentController.setSelectedPerm(perm));
        rect.setFill(getFactoradicColor(perm));
        visualizationPane.getChildren().add(rect);
        Tooltip tooltip = new Tooltip(perm.toString());
        Tooltip.install(rect, tooltip);
        placePermLabel(rect, perm);
        return rect;
    }


    double lastPressX;
    double lastPressY;
    double initialLayoutX;
    double initialLayoutY;

    private Shape placePermNode(Vec2 loc, Permutation perm){
        Circle shape = new Circle(PERM_ICON_RADIUS,getFactoradicColor(perm));
        shape.setOnMousePressed(event -> parentController.setSelectedPerm(perm));
        visualizationPane.getChildren().add(shape);
        shape.setLayoutX(loc.X());
        shape.setLayoutY(loc.Y());
        Tooltip tooltip = new Tooltip(perm.toString());
        Tooltip.install(shape,tooltip);
        placePermLabel(shape, perm);

        EventHandler<MouseEvent> dragHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                shape.setLayoutX(event.getSceneX());
                shape.setLayoutY(event.getSceneY());
            }
        };

        shape.setOnMousePressed(event -> {
            initialLayoutX = shape.getLayoutX();
            initialLayoutY = shape.getLayoutY();
            lastPressX = event.getSceneX();
            lastPressY = event.getSceneY();
            parentController.setSelectedPerm(perm);
        });
        shape.setOnMouseDragged(event -> {
            double dispX = event.getSceneX() - lastPressX;
            double dispY = event.getSceneY() - lastPressY;
            shape.setLayoutX(initialLayoutX + dispX);
            shape.setLayoutY(initialLayoutY + dispY);
            //shape.setLayoutX(event.getSceneX());
            //shape.setLayoutX(event.getSceneY());
        });
        return shape;
    }




    private Color getFactoradicColor(Permutation perm){

        //plus 1's used so that no perm is white
        int factNum = perm.getFactoradic()+1;
        int maxFact = factorial(generator.getPermutationSize())+1;
        double ratio = (double) factNum / (double) maxFact;


        return Color.hsb(100,ratio,1);//note: 100 is for green
    }

    private Permutation prevSelection;
    //private List<Node> selectionObjects;
    public void selectPermutation(Permutation perm){
        selectionPane.getChildren().clear();
        /*
        if(selectionObjects!=null){
            for(Node n : selectionObjects)
                selectionPane.getChildren().remove(n);
        } else {
            selectionObjects = new ArrayList<>();
        }
        selectionObjects.clear();*/
        if(perm2Shape != null && perm2Shape.containsKey(perm)){
            if(prevSelection != null)
                perm2Shape.get(prevSelection).setStrokeWidth(0);
            perm2Shape.get(perm).setStroke(Color.RED);
            perm2Shape.get(perm).setStrokeWidth(2);
            //perm2Shape.get(perm).setFill(Color.RED);
            prevSelection = perm;
            if(perm.getLegnth() > 5){
                Shape thisShape = perm2Shape.get(perm);
                for(Permutation neighbor : perm.getAdjacentPerms()){
                    Shape thatShape = perm2Shape.get(neighbor);
                    connectShapes(thisShape,thatShape, Color.BLACK);
                }
                connectShapes(thisShape,perm2Shape.get(perm.getInverse()),Color.BLUE);
            }
        }
    }

    private void connectShapes(Shape source, Shape target, Color color){
        double sCenterX = source.getLayoutBounds().getWidth()/2 + source.getLayoutX();
        double sCenterY = source.getLayoutBounds().getHeight()/2 + source.getLayoutY();
        double tCenterX = target.getLayoutBounds().getWidth()/2 + target.getLayoutX();
        double tCenterY = target.getLayoutBounds().getHeight()/2 + target.getLayoutY();

        Circle targetSelector = new Circle(tCenterX,tCenterY,SELECTION_DOT_RADIUS,color);
        Line selectorLine = new Line(sCenterX, sCenterY, tCenterX, tCenterY);
        selectorLine.setStroke(color);
        selectionPane.getChildren().add(selectorLine);
        selectionPane.getChildren().add(targetSelector);
        //selectionObjects.add(targetSelector);
        //selectionObjects.add(selectorLine);

    }

}
