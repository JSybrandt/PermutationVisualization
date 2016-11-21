

package controller;

import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

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
public abstract class Controller {

    protected Node thisNode;
    public Controller(Node node){
        thisNode = node;
    }


    public abstract void run();

}
