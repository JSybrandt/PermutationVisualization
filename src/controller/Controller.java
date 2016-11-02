/*
* Justin Sybrandt
*
* Description:
* This class provides a common interface for all controllers.
* Controllers should do minimal work on instantiation, and instead to most work
* when Controller.run() is called.
*
* Each controller is also intended to only handle the events of a single view.
*
* This class also automatically provides a way to handle common mouse events.
*
* Important Values:
* node - Each controller is intended to
*
* */

package controller;

import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public abstract class Controller {

    public Controller(Node node){
        registerCallbacks(node);
    }

    public void registerCallbacks(Node node){
        node.setOnMouseClicked(this::onViewMouseClicked);
        node.setOnKeyPressed(this::onViewKeyPressed);
        node.setOnKeyReleased(this::onViewKeyReleased);
        node.setOnMouseMoved(this::onViewMouseMoved);
        node.setOnScroll(this::onViewScroll);
    }

    public void onViewMouseClicked(MouseEvent event){}

    public void onViewKeyPressed(KeyEvent event){}

    public void onViewKeyReleased(KeyEvent event){}

    public void onViewMouseMoved(MouseEvent event){}

    public void onViewScroll(ScrollEvent event){}

    public abstract void run();

}
