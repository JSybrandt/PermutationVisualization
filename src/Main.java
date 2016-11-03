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

        //System.out.println(new Permutation(1,2,3));
        //System.out.println(new Permutation(1,3,2));
        //System.out.println(new Permutation(2,1,3));
        //System.out.println(new Permutation(2,3,1));
        //System.out.println(new Permutation(3,1,2));
        //System.out.println(new Permutation(3,2,1));

        int[] data = {5,9,1,8,2,6,4,7,3};
        Permutation perm = new Permutation(data);
        int[] inv = perm.getInversionVector();
        int fact = perm.getFactoradic();
        Permutation duplicate = Permutation.fromFactoradic(fact,9);

        //System.out.println(Permutation.fromFactoradic(22,4));

        for(int i = 0 ; i < 24; i ++){
            System.out.println(Permutation.fromFactoradic(i,4));
        }


        //launch(args);
    }
}
