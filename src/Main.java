/**
 * Justin Sybrandt
 * This is the main class for the Maze Generator
 *
 * The main class is responsible for starting the javafx application
 * (this just creates the window)
 * and the main class also needs to spawn the main controller.
 *
 * Note: JavaFX requires that the main application define a root pane, although this slightly
 * contradicts a string MVC paradigm. If it were me, the ApplicationController would created the
 * ApplicationPane.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Permutation;

public class Main extends Application{

    @Override
    public void start(Stage stage){
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);
        stage.setTitle("Permutation Visualization");
        stage.setHeight(300);
        stage.setWidth(300);
        stage.setScene(scene);
        //stage.setFullScreen(true);

        stage.show();
    }

    public static void main(String[] args) {

        Permutation perm = new Permutation(1,2,3);
        System.out.println(perm.getFactoradic());
        perm = new Permutation(2,1,3);
        System.out.println(perm.getFactoradic());



        launch(args);
    }
}
