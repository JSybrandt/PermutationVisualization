import controller.ApplicationController;
import controller.option.GeneratorOption;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.ApplicationPane;

/*
Justin Sybrandt

Purpose:

This is main, and it needs to start the application, start the main controller, and link the two
 */
public class Main extends Application{

    @Override
    public void start(Stage stage){
        ApplicationPane mainAppPane = new ApplicationPane();

        ApplicationController mainAppController = new ApplicationController(mainAppPane);
        mainAppController.setGeneratorOption(GeneratorOption.INSERT);
        mainAppController.setPermutationLength(4);
        mainAppController.run();

        Scene scene = new Scene(mainAppPane);
        stage.setScene(scene);
        stage.setTitle("Permutation Visualization");
        stage.setMaximized(true);

        stage.show();
        mainAppController.launchHelp();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
