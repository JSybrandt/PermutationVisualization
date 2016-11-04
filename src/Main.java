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

import controller.PermMatrixController;
import controller.option.PermVisOption;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.*;

public class Main extends Application{

    @Override
    public void start(Stage stage){
        BorderPane root = new BorderPane();


        Canvas canvas = new Canvas(200,200);
        PermMatrixController controller = new PermMatrixController(canvas);
        Permutation perm = new Permutation(5,3,4,1,2);
        controller.drawPermutation(perm, PermVisOption.GRAPH);
        root.setCenter(canvas);

        Scene scene = new Scene(root);
        stage.setTitle("Permutation Visualization");
        stage.setHeight(300);
        stage.setWidth(300);
        stage.setScene(scene);
        //stage.setFullScreen(true);

        stage.show();
    }

    public static void main(String[] args) {

        PermutationGenerator gen = new SwapGenerator(4);
        for(Permutation p : gen.generate()){
            System.out.println(p);
            System.out.println(p.toCyclicNotation());
        }




        launch(args);
    }
}
