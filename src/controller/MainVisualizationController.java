package controller;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.Permutation;
import model.PermutationGenerator;
import util.Vec2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static util.FactorialMath.factorial;

/*
Justin Sybrandt

Purpose:
This class displays permutation set visualizations shown on the center of the main application pane
This class responds differently to permutations of different sizes
This class handles many user events to update the selected permutation and allows for an interactive view
All visualizations are assembled on a simple pane.
To speedup selection, dynamic information is presented on the selection pane (intended to be stacked on the visPane).

In this file, nodes refers to the circles that the user can move, cells refers to the squares the user cannot

Important values
void displaySmall
void displayMed
void displayLarge


 */
public class MainVisualizationController extends Controller {
    private Pane visualizationPane;
    private Pane selectionPane;

    private ApplicationController parentController;
    private PermutationGenerator generator;

    private final double PERM_ICON_RADIUS = 15;
    private final double SELECTION_DOT_RADIUS = 6;
    private Map<Permutation, Shape> perm2Shape;
    private final Color INV_COLOR = Color.BLUE;
    private List<Permutation> data;
    private List<Circle> markers = new ArrayList<>();

    //needs pane to put vis on, pane to put selection info on (optimization), and parent controller
    public MainVisualizationController(Pane visualizationPane, Pane selectionPane, ApplicationController parent) {
        super(visualizationPane);
        this.visualizationPane = visualizationPane;
        this.parentController = parent;
        this.selectionPane = selectionPane;
        visualizationPane.widthProperty().addListener(observable -> resize());
        visualizationPane.heightProperty().addListener(observable -> resize());
    }

    //resize sets visiblity based on size
    public void resize(){
        Bounds bounds = visualizationPane.getLayoutBounds();
        for(Node node : visualizationPane.getChildren()){
            if(!node.visibleProperty().isBound())
                node.setVisible(bounds.contains(node.getBoundsInParent()));
        }
    }

    public void setGenerator(PermutationGenerator generator) {
        this.generator = generator;
    }

    //running places the visualization. Dynamic based on permutation size
    @Override
    public void run(){
        if(generator == null) return;
        prevSelection = null;
        visualizationPane.getChildren().clear();
        selectionPane.getChildren().clear();
        perm2Shape = new HashMap<>();
        data = generator.generate();
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
        parentController.setSelectedPerm(data.get(0));
    }

    //displayes a circular graph
    private void displaySmall(List<Permutation> data){

        //angle and anglediff used to place perms in circle
        double angle = 0;
        double angleDiff = (Math.PI * 2) / data.size();

        //radius intended to keep perms onscreen
        double radius = Math.min(visualizationPane.getHeight(),visualizationPane.getWidth())/2.5;
        Vec2 vec = new Vec2(0,radius);
        Vec2 center = new Vec2(visualizationPane.getWidth()/2, visualizationPane.getHeight()/2);

        //place all shapes
        for(Permutation perm : data){
            perm2Shape.put(perm, placePermNode(vec.rotate(angle).plus(center),perm));
            angle += angleDiff;
        }

        //make all links
        for(Permutation source : data){
            for(Permutation target : source.getAdjacentPerms()){
                connectPermNodes(perm2Shape.get(source),perm2Shape.get(target),Color.BLACK);
            }
            Permutation target = source.getInverse();
            connectPermNodes(perm2Shape.get(source),perm2Shape.get(target),INV_COLOR);
        }

    }

    private void displayMed(List<Permutation> data){
        //angle used to make circle
        double angle = 0;
        double angleDiff = (Math.PI * 2) / data.size();

        //because the # of elements is larger, we use two rings instead of one
        double radiusSmall = Math.min(visualizationPane.getHeight(),visualizationPane.getWidth())/2.5;
        double radiusLarge = Math.min(visualizationPane.getHeight(),visualizationPane.getWidth())/2.3;


        Vec2 vec = new Vec2(0,1);
        Vec2 center = new Vec2(visualizationPane.getWidth()/2, visualizationPane.getHeight()/2);

        //place all shapes
        boolean pushOut = false;
        for(Permutation perm : data){
            pushOut = !pushOut;
            Vec2 loc = vec.scale((pushOut?radiusLarge:radiusSmall)).rotate(angle).plus(center);
            perm2Shape.put(perm, placePermNode(loc,perm));
            angle += angleDiff;
        }

        //link all perms
        //because the set is larger, only show inverses until user clicks
        for(Permutation source : data){
            connectPermNodes(perm2Shape.get(source),perm2Shape.get(source.getInverse()),INV_COLOR);
        }
    }

    //displays the permutations as a grid
    private void displayLarge(List<Permutation> data){

        //fit the best square possible
        int sideLength = (int) Math.ceil(Math.sqrt(data.size()));

        double width = visualizationPane.getWidth();
        double height = visualizationPane.getHeight();

        if(width > 0 && height > 0) {

            Vec2 size = new Vec2(width / sideLength, height / sideLength);

            Vec2 index = new Vec2(0, 0);
            //place each shape
            for (int i = 1; i <= data.size(); i++) {
                perm2Shape.put(data.get(i - 1), placePermCell(index, size, data.get(i - 1)));
                if (i % sideLength == 0) {
                    index = new Vec2(0, index.Y() + size.Y());
                } else
                    index = new Vec2(index.X() + size.X(), index.Y());
            }
        }

    }

    //puts a colored line behind two shapes
    //perm nodes always show their links
    private void connectPermNodes(Shape source, Shape target, Color lineColor){
        Line line = new Line(0,0,0,0);
        line.setStroke(lineColor);
        line.startXProperty().bind(source.layoutXProperty());
        line.startYProperty().bind(source.layoutYProperty());
        line.endXProperty().bind(target.layoutXProperty());
        line.endYProperty().bind(target.layoutYProperty());
        visualizationPane.getChildren().add(line);
        line.toBack();
    }

    //binds a label representing the numer of fixed values in the perm
    private void placePermLabel(Shape s, Permutation perm){
        int numFixed = perm.getNumFixed();
        if(numFixed==0)return;
        Label label = new Label(Integer.toString( perm.getNumFixed()));
        label.setCenterShape(true);
        label.layoutXProperty().bind(s.layoutXProperty());
        label.layoutYProperty().bind(s.layoutYProperty());
        label.visibleProperty().bind(s.visibleProperty());
        visualizationPane.getChildren().add(label);
        label.toFront();
    }

    //places a square perm shape
    private Shape placePermCell(Vec2 loc, Vec2 size, Permutation perm){
        Shape rect = new Rectangle(0,0, size.X(), size.Y());
        rect.setLayoutX(loc.X());
        rect.setLayoutY(loc.Y());
        rect.setOnMousePressed(event -> {if(event.isPrimaryButtonDown()){parentController.setSelectedPerm(perm);}});
        rect.setFill(getFactoradicColor(perm));
        visualizationPane.getChildren().add(rect);
        Tooltip tooltip = new Tooltip(perm.toString());
        Tooltip.install(rect, tooltip);
        placePermLabel(rect, perm);
        return rect;
    }


    //these values just used to make moving the permutations smoother
    double lastPressX;
    double lastPressY;
    double initialLayoutX;
    double initialLayoutY;

    //places a moveable circular permutation shape
    private Shape placePermNode(Vec2 loc, Permutation perm){
        Circle shape = new Circle(0,0,PERM_ICON_RADIUS,getFactoradicColor(perm));
        shape.setOnMousePressed(event -> {if(event.isPrimaryButtonDown()){parentController.setSelectedPerm(perm);}});
        shape.setLayoutX(loc.X());
        shape.setLayoutY(loc.Y());
        visualizationPane.getChildren().add(shape);

        Tooltip tooltip = new Tooltip(perm.toString());
        Tooltip.install(shape,tooltip);
        placePermLabel(shape, perm);
        shape.setStrokeWidth(1);
        shape.setStroke(Color.BLACK);

        shape.setOnMousePressed(event -> {
            if(event.isPrimaryButtonDown()) {
                initialLayoutX = shape.getLayoutX();
                initialLayoutY = shape.getLayoutY();
                lastPressX = event.getSceneX();
                lastPressY = event.getSceneY();
                parentController.setSelectedPerm(perm);
            }
        });
        shape.setOnMouseDragged(event -> {
            if(event.isPrimaryButtonDown()) {
                double dispX = event.getSceneX() - lastPressX;
                double dispY = event.getSceneY() - lastPressY;
                shape.setLayoutX(initialLayoutX + dispX);
                shape.setLayoutY(initialLayoutY + dispY);
            }
        });
        return shape;
    }


    //maps color to a shade of green
    private Color getFactoradicColor(Permutation perm){

        //plus 1's used so that no perm is white
        int factNum = perm.getFactoradic()+1;
        int maxFact = factorial(generator.getPermutationSize())+1;
        double ratio = (double) factNum / (double) maxFact;

        return Color.hsb(100,ratio,1);//note: 100 is for green
    }

    //used to remember selection
    private Permutation prevSelection;
    public void selectPermutation(Permutation perm){
        selectionPane.getChildren().clear();
        if(markers.size()>100)
            visualizationPane.getChildren().removeAll(markers);

        if(perm2Shape != null && perm2Shape.containsKey(perm)){

            //deselect old
            if(prevSelection != null) {
                perm2Shape.get(prevSelection).setStrokeWidth(1);
                perm2Shape.get(prevSelection).setStroke(Color.BLACK);
            }
            perm2Shape.get(perm).setStroke(Color.RED);
            perm2Shape.get(perm).setStrokeWidth(2);
            prevSelection = perm;
            if(perm.getLegnth() >= 5){
                Shape thisShape = perm2Shape.get(perm);
                for(Permutation neighbor : perm.getAdjacentPerms()){
                    Shape thatShape = perm2Shape.get(neighbor);
                    connectPermCells(thisShape,thatShape, Color.BLACK);
                }
                if(perm.getLegnth() > 5)
                    connectPermCells(thisShape,perm2Shape.get(perm.getInverse()),Color.BLUE);
            }
        }
    }

    //perm cells only show the connection when clicks
    private void connectPermCells(Shape source, Shape target, Color color){
        double sCenterX = source.getLayoutBounds().getWidth()/2 + source.getLayoutX();
        double sCenterY = source.getLayoutBounds().getHeight()/2 + source.getLayoutY();

        double tCenterX = target.getLayoutBounds().getWidth()/2 + target.getLayoutX();
        double tCenterY = target.getLayoutBounds().getHeight()/2 + target.getLayoutY();

        Circle markerS = new Circle(0,0,0,Color.color(0,0,0,0));
        Circle markerT = new Circle(0,0,0,Color.color(0,0,0,0));
        markerS.setLayoutX(sCenterX);
        markerS.setLayoutY(sCenterY);
        markerT.setLayoutX(tCenterX);
        markerT.setLayoutY(tCenterY);

        markers.add(markerS);
        markers.add(markerT);

        Circle targetSelector = new Circle(0,0,SELECTION_DOT_RADIUS,color);
        targetSelector.layoutXProperty().bind(markerT.layoutXProperty());
        targetSelector.layoutYProperty().bind(markerT.layoutYProperty());
        Line selectorLine = new Line();
        selectorLine.startXProperty().bind(markerS.layoutXProperty());
        selectorLine.startYProperty().bind(markerS.layoutYProperty());
        selectorLine.endXProperty().bind(markerT.layoutXProperty());
        selectorLine.endYProperty().bind(markerT.layoutYProperty());
        selectorLine.setStroke(color);
        selectorLine.visibleProperty().bind(markerT.visibleProperty());
        targetSelector.visibleProperty().bind(markerT.visibleProperty());
        selectionPane.getChildren().add(selectorLine);
        selectionPane.getChildren().add(targetSelector);
        visualizationPane.getChildren().add(markerS);
        visualizationPane.getChildren().add(markerT);

        //need to special case this
        if(parentController.getSelectedPerm().getLegnth() == 5){
            markerS.layoutXProperty().bind(source.layoutXProperty());
            markerS.layoutYProperty().bind(source.layoutYProperty());
            markerT.layoutXProperty().bind(target.layoutXProperty());
            markerT.layoutYProperty().bind(target.layoutYProperty());
            targetSelector.toBack();
            selectorLine.toBack();
            targetSelector.setRadius(0);
        }
    }

    public void keyTypedHandler(KeyEvent event){
        if(parentController.getSelectedPerm() != null) {
            int permIndex = data.indexOf(parentController.getSelectedPerm());
            if (event.getCode() == KeyCode.LEFT) {
                permIndex++;
            } else if (event.getCode() == KeyCode.RIGHT) {
                permIndex--;
            }
            while(permIndex < 0) permIndex += data.size();
            permIndex %= data.size();
            parentController.setSelectedPerm(data.get(permIndex));
        }
    }

}
