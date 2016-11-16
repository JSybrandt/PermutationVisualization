package view;

import javafx.collections.ListChangeListener;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import util.Vec2;

public class ZoomPane extends Pane {

    final double MAX_ZOOM = 3;
    final double MIN_ZOOM = 1;
    double zoomFactor = MIN_ZOOM;

    Vec2 viewPaneLocation = new Vec2(0,0);
    Vec2 initialMousePress = null;

    private boolean allowHorizontalMotion = true;

    public ZoomPane(){
        setOnScroll(scrollEvent -> {
            double zoomDelta = scrollEvent.getDeltaY()/400;
            zoom(zoomDelta);
        });


        setOnMousePressed(event -> {
            if(event.getButton().equals(MouseButton.SECONDARY))
                initialMousePress = getMouseScreenPos(event);
        });

        setOnMouseReleased(event -> {
            initialMousePress = null;
        });

        setOnMouseDragged(dragEvent -> {
            if(initialMousePress!=null) {
                if (dragEvent.getButton().equals(MouseButton.SECONDARY)) {
                    Vec2 viewDelta = getMouseScreenPos(dragEvent).minus(initialMousePress);
                    if(!allowHorizontalMotion) viewDelta = new Vec2(0,viewDelta.Y());
                    pan(viewDelta);
                    initialMousePress = getMouseScreenPos(dragEvent);
                }
            }
        });

        setOnKeyReleased(keyEvent -> {
            if(keyEvent.getCharacter().equals("r"))
                reset();
        });

        getChildren().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(Change<? extends Node> c) {

            }
        });
    }

    private void zoom(double zoomDelta){
        System.out.println(zoomDelta);
        double newZoom = Math.min(Math.max(zoomFactor + zoomDelta, MIN_ZOOM), MAX_ZOOM);
        double zoomFactorStep = newZoom / zoomFactor;
        zoomFactor = newZoom;

        Vec2 center = getScreenCenter();
        for(Node node : getChildren()){
            if(! (node instanceof Line)){
                Vec2 nodePos = getNodePos(node);
                setNodePos(node,scaleFromPoint(nodePos,center,zoomFactorStep));
                node.setScaleY(zoomFactor);
                if(allowHorizontalMotion)
                    node.setScaleX(zoomFactor);
            }

        }
        viewPaneLocation = scaleFromPoint(viewPaneLocation,center,zoomFactorStep);
        showOnlyOnScreenNodes();
    }

    private Vec2 scaleFromPoint(Vec2 vec, Vec2 center, double scale){
        if(!allowHorizontalMotion)
            return new Vec2(vec.X(), (vec.Y() - center.Y())*scale + center.Y());
        return vec.minus(center).scale(scale).plus(center);
    }

    private void pan(Vec2 viewDelta){
        viewPaneLocation = viewPaneLocation.plus(viewDelta);
        for(Node node : getChildren()){
            if(!(node instanceof Line))
            setNodePos(node,getNodePos(node).plus(viewDelta));
        }
        showOnlyOnScreenNodes();
    }

    private void showOnlyOnScreenNodes(){
        /*
        for(Node node : getChildren()){
            Vec2 absPos = new Vec2(node.getTranslateX() + node.getLayoutX(), node.getTranslateY() + node.getLayoutY());
            System.out.println("--"+absPos);
            node.setVisible(absPos.X() >=0 && absPos.X() <= getWidth()
                         && absPos.Y() >= 0 && absPos.Y() <= getHeight());
        }
        */
    }

    private void reset(){
        zoom(zoomFactor-MIN_ZOOM);
        pan(viewPaneLocation.scale(-1));
    }

    private Vec2 getScreenCenter(){
        Vec2 center = new Vec2(getWidth(),getHeight());
        return center.scale(0.5).plus(viewPaneLocation);
    }

    private void setNodePos(Node node, Vec2 pos){
        if(node instanceof Circle){
            ((Circle) node).setCenterX(pos.X());
            ((Circle) node).setCenterY(pos.Y());
        } else {
            node.setLayoutX(pos.X());
            node.setLayoutY(pos.Y());
        }
    }

    private Vec2 getNodePos(Node node){
        if(node instanceof Circle)
            return new Vec2(((Circle) node).getCenterX(),((Circle) node).getCenterY());
        else
            return new Vec2(node.getLayoutX(), node.getLayoutY());
    }

    private Vec2 getMouseScreenPos(MouseEvent event){
        return new Vec2(event.getScreenX(),event.getScreenY());
    }

    public void setHorizontalMotion(boolean bool){
        allowHorizontalMotion = bool;
    }
}
