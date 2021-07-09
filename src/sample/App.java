package sample;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.ViewFactory;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{

        ViewFactory viewFactory = ViewFactory.defaultFactory;
        Scene scene = viewFactory.getMainScene();
        scene.getStylesheets().add(getClass().getResource("../view/application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.getIcons().add(new Image(App.class.getResourceAsStream("../view/icon.png")));
            primaryStage.show();
        }
    }