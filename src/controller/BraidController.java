package controller;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import model.Permutation;
import model.SwapGenerator;
import view.ApplicationPane;
import view.ZoomPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jsybran on 11/6/2016.
 */
public class BraidController extends Controller {

    Pane visPane;
    int permLength;
    ApplicationController parent;
    List<Permutation> data;
    List<Circle> selections;
    Map<Permutation,Pane> perm2Selector = new HashMap<>();
    final double DOT_RADIUS = 6;

    public BraidController(Pane node, ApplicationController parent) {
        super(node);
        visPane = node;
        visPane.widthProperty().addListener(observable -> run());
        visPane.heightProperty().addListener(observable -> run());
        this.parent = parent;
        selections = new ArrayList<>();
    }

    @Override
    public void run() {
        visPane.getChildren().clear();
        selections.clear();


        double width = effectiveWidth();
        double height = visPane.getHeight();

        SwapGenerator gen = new SwapGenerator(permLength);
        data = gen.generate();
        ArrayList<Color> valueColors = new ArrayList<>();
        double rowHeight = rowHeight();
        double valueWidth = width / (permLength - 1);
        for(int i = 0; i < permLength; i++){
            valueColors.add(Color.hsb(i*(360.0/(permLength+1)),1,1));
            Circle selection = new Circle(DOT_RADIUS,valueColors.get(i));
            selections.add(selection);
        }

        for(int permIndex = 1; permIndex < data.size(); permIndex++){
            Permutation upper = data.get(permIndex-1);
            Permutation current = data.get(permIndex);
            double startY = rowHeight * (permIndex-1) + marginSize();
            double endY = startY + rowHeight;
            for(int valueIndex = 1 ; valueIndex <= permLength; valueIndex++){
                double startX = valueWidth * upper.getIndexOf(valueIndex) + marginSize();
                double endX = valueWidth * current.getIndexOf(valueIndex) + marginSize();
                Color lineColor = valueColors.get(valueIndex-1);
                //used for zoom pane achors
                Circle startNode = new Circle(0,0,0, lineColor);
                Circle endNode = new Circle(0,0,0, Color.color(1,0,0,0));
                startNode.setLayoutX(startX);
                startNode.setLayoutY(startY);
                endNode.setLayoutX(endX);
                endNode.setLayoutY(endY);
                Line line = new Line();
                line.startXProperty().bind(startNode.layoutXProperty());
                line.startYProperty().bind(startNode.layoutYProperty());
                line.endXProperty().bind(endNode.layoutXProperty());
                line.endYProperty().bind(endNode.layoutYProperty());
                line.setStroke(lineColor);
                line.setFill(lineColor);
                line.setStrokeWidth(2);
                visPane.getChildren().add(startNode);
                visPane.getChildren().add(endNode);
                visPane.getChildren().add(line);
            }
            addSelector(rowHeight,startY,current);
        }
        addSelector(rowHeight,marginSize()-rowHeight,data.get(0));

    }

    private void addSelector(double rowHeight, double startY, Permutation perm){
        Pane selector = new Pane();
        selector.resize(visPane.getWidth(), rowHeight);
        selector.setPrefWidth(effectiveWidth());
        selector.setPrefHeight(rowHeight);
        selector.setLayoutX(marginSize());
        selector.setLayoutY(startY+rowHeight/2);
        selector.setOnMouseClicked(event -> parent.setSelectedPerm(perm));
        //selector.setStyle("-fx-border-color: black");
        perm2Selector.put(perm,selector);
        visPane.getChildren().add(selector);
    }

    public void setPermLength(int permLength){
        this.permLength = permLength;
    }

    public void setSelectedPerm(Permutation perm){
        if(perm != null && data != null && data.contains(perm)) {
            for(Shape s : selections) {
                if(!visPane.getChildren().contains(s))
                    visPane.getChildren().add(s);
            }
            double valueWidth = effectiveWidth() / (perm.getLegnth() - 1);
            Pane selector = perm2Selector.get(perm);
            double yval = selector.getLayoutY() + selector.getBoundsInParent().getHeight()/2;
            for (int i = 0; i < perm.getLegnth(); i++) {
                Circle s = selections.get(i);
                s.setLayoutX(perm.getIndexOf(i + 1) * valueWidth + marginSize());
                s.setLayoutY(yval);
            }
        }
    }

    private double effectiveWidth(){
        //allows for effective margin
        return visPane.getWidth()*0.8;
    }
    private double marginSize(){
        return (visPane.getWidth() - effectiveWidth()) / 2;
    }
    private double rowHeight(){
        return 50;//Math.max(visPane.getHeight() / (data.size()-1),30);
    }

    public void keyTypedHandler(KeyEvent event){
        if(parent.getSelectedPerm() != null && data != null && data.size() > 0) {
            int permIndex = data.indexOf(parent.getSelectedPerm());
            if (event.getCode() == KeyCode.UP && permIndex > 0) {
                permIndex--;
            } else if (event.getCode() == KeyCode.DOWN && permIndex < data.size() - 1) {
                permIndex++;
            }
            parent.setSelectedPerm(data.get(permIndex));
        }
    }

}
