package view;


import controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.naming.OperationNotSupportedException;

public class ViewFactory {
    //starting point of application
    //create a modelaccess
    public static ViewFactory defaultFactory = new ViewFactory();
    private ModelAccess modelAccess = new ModelAccess();

    private MainController mainController;
    private EmailDetailsController emailDetailsController;

    private final String EMAIL_DETAILS_FXML="EmailDetailsLayout.fxml";
    private final String DEFAULT_CSS="application.css";
    private final String MAIN_SCREEN_FXML="MainLayout.fxml";
    private final String COMPOSE_SCREEN_FXML="ComposeMessageLayout.fxml";

    private static boolean mainViewInitialized = false;
    private Scene initializeScene(String fxmlPath, AbstractController controller){
        FXMLLoader loader;
        Parent parent;
        Scene scene;
        try {
            loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setController(controller);
            parent = loader.load();
        } catch (Exception e) {
            return null;
        }

        scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource(DEFAULT_CSS).toExternalForm());
        return scene;
    }

    public Scene getMainScene() throws OperationNotSupportedException{
        if (!mainViewInitialized) {
            mainController = new MainController(modelAccess);
            mainViewInitialized = true;
            return initializeScene(MAIN_SCREEN_FXML, mainController);
        } else{
            throw new OperationNotSupportedException("Main Scene already initialized");
        }

    }

    public Scene getEmailDetailsScene(){
        emailDetailsController = new EmailDetailsController(modelAccess);
        return initializeScene(EMAIL_DETAILS_FXML, emailDetailsController);
    }






/*
    public Scene getMainScene(){
        Pane pane;
        try{
            pane = FXMLLoader.load(getClass().getResource("MainLayout.fxml"));

        }catch(IOException e){
            pane = null;
        }
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        return scene;

    }
    public Scene getEmailDetailsScene(){
        Pane pane;
        try{
            pane = FXMLLoader.load(getClass().getResource("EmailDetailsLayout.fxml"));
        }catch(IOException e){
            pane = null;

        }
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        return scene;

    }*/

    public Node resolveIcon(String treeItemValue){
        String lowerCaseTreeItemValue = treeItemValue.toLowerCase();
        ImageView returnIcon;
        try{
            if(lowerCaseTreeItemValue.contains("inbox")){
                returnIcon = new ImageView(new Image(getClass().getResourceAsStream("/view/images/inbox.png")));

            }
            else if(lowerCaseTreeItemValue.contains("sent")){
                returnIcon = new ImageView(new Image(getClass().getResourceAsStream("/view/images/sent2.png")));

            }
            else if(lowerCaseTreeItemValue.contains("spam")){
                returnIcon = new ImageView(new Image(getClass().getResourceAsStream("/view/images/spam.png")));

            }
            else if(lowerCaseTreeItemValue.contains("@")){
                returnIcon = new ImageView(new Image(getClass().getResourceAsStream("/view/images/email.png")));

            }else {
                returnIcon = new ImageView(new Image(getClass().getResourceAsStream("/view/images/folder.png")));
            }

        }
        catch(NullPointerException e){
            System.out.println("Invalid Image Location");
            e.printStackTrace();
            returnIcon = new ImageView();
        }
        returnIcon.setFitHeight(16);
        returnIcon.setFitWidth(16);
        return returnIcon;
    }

    private Scene InitializeScene(String fxmlPath, AbstractController abstractController){
        FXMLLoader loader;
        Parent parent;
        Scene scene;
        try{
            loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setController(abstractController);
            parent = loader.load();
        }catch (Exception e){

            e.printStackTrace();
            return null;
        }
        scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource(DEFAULT_CSS).toExternalForm());


        return scene;
    }

    public Scene getComposeMessageScene(){
        AbstractController composeController = new ComposeMessageController(modelAccess);
        return initializeScene(COMPOSE_SCREEN_FXML,composeController);
    }
}
