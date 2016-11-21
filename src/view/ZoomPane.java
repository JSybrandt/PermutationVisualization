package view;

import javafx.collections.ListChangeListener;
import javafx.geometry.Bounds;
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
            getChildren().addListener(new ListChangeListener<Node>() {
                @Override
                public void onChanged(Change<? extends Node> c) {
                    c.next();
                    if(c.wasAdded()) {
                        for (Node node : c.getAddedSubList()) {
                            if(!node.visibleProperty().isBound()) {
                                boolean vis = getLayoutBounds().contains(node.getBoundsInParent());
                                node.setVisible(vis && node.isVisible());
                            }
                        }
                    }
                }
            });
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

    }

    private void zoom(double zoomDelta){
        double newZoom = Math.min(Math.max(zoomFactor + zoomDelta, MIN_ZOOM), MAX_ZOOM);
        double zoomFactorStep = newZoom / zoomFactor;
        zoomFactor = newZoom;

        Vec2 center = getScreenCenter();
        for(Node node : getChildren()){
            if(isManipulatable(node)){
                Vec2 nodePos = getNodePos(node);
                setNodePos(node,scaleFromPoint(nodePos,center,zoomFactorStep));
                if(!(node instanceof Circle)) {
                    node.setScaleY(zoomFactor);
                    if (allowHorizontalMotion)
                        node.setScaleX(zoomFactor);
                }
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
            if(isManipulatable(node))
                setNodePos(node,getNodePos(node).plus(viewDelta));
        }
        showOnlyOnScreenNodes();
    }

    private void showOnlyOnScreenNodes(){

        Bounds bounds = getLayoutBounds();
        for(Node node : getChildren()){
            if(!node.visibleProperty().isBound()) {
                if (bounds.contains(node.getBoundsInParent())) {
                    node.setVisible(true);
                } else {
                    node.setVisible(false);
                }
            }
        }

        /*
        for(Node node : getChildren()){
            Vec2 absPos = new Vec2(node.getTranslateX() + node.getLayoutX(), node.getTranslateY() + node.getLayoutY());
            System.out.println("--"+absPos);
            node.setVisible(absPos.X() >=0 && absPos.X() <= getWidth()
                         && absPos.Y() >= 0 && absPos.Y() <= getHeight());
        }
        */
    }

    public void reset(){
        getChildren().clear();
        zoomFactor = MIN_ZOOM;
        viewPaneLocation = new Vec2(0,0);
    }

    private Vec2 getScreenCenter(){
        Vec2 center = new Vec2(getWidth(),getHeight());
        return center.scale(0.5).plus(viewPaneLocation);
    }

    private void setNodePos(Node node, Vec2 pos){
            node.setLayoutX(pos.X());
            node.setLayoutY(pos.Y());

    }

    private Vec2 getNodePos(Node node){
            return new Vec2(node.getLayoutX(), node.getLayoutY());
    }

    private Vec2 getMouseScreenPos(MouseEvent event){
        return new Vec2(event.getScreenX(),event.getScreenY());
    }

    public void setHorizontalMotion(boolean bool){
        allowHorizontalMotion = bool;
    }

    private boolean isManipulatable(Node node){
        return !(node instanceof Line) && !(node.layoutXProperty().isBound() || node.layoutYProperty().isBound());
    }
}
