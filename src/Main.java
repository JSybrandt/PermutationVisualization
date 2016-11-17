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

import controller.ApplicationController;
import controller.option.GeneratorOption;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.ApplicationPane;

public class Main extends Application{

    @Override
    public void start(Stage stage){
        BorderPane root = new BorderPane();

        ApplicationPane mainAppPane = new ApplicationPane();
        root.setCenter(mainAppPane);
        Scene scene = new Scene(root);
        ApplicationController mainAppController = new ApplicationController(mainAppPane);
        mainAppController.setGeneratorOption(GeneratorOption.INSERT);
        mainAppController.setPermutationLength(4);
        mainAppController.run();

        stage.setTitle("Permutation Visualization");
        stage.setHeight(300);
        stage.setWidth(300);
        stage.setScene(scene);
        //stage.setFullScreen(true);
        stage.setMaximized(true);

        stage.show();
        mainAppController.launchHelp();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
