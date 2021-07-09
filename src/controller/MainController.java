package controller;

import controller.services.CreateAndRegisterEmailAccountService;
import controller.services.FolderUpdaterService;
import controller.services.MessageRendererService;
import controller.services.SaveAttachmentsService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import model.EmailMessageBean;
import model.folder.EmailFolderBean;
import model.table.BoldableRowFactory;
import model.table.FormatableInteger;
import sample.App;
import view.ViewFactory;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class MainController extends AbstractController implements Initializable {
    //make each controller extend abstract controller so you will not have
    //instance of ModelAccess class in each controller class

    public MainController(ModelAccess modelAccess){
        super(modelAccess);
    }
    @FXML
    private Button button1;

    @FXML
    private WebView messageRenderer;

    @FXML
    private TableView<EmailMessageBean> emailtableview;

    // scenebuilder gives us generated skeleton of the column as <?,?>
    //in code we need to change it to our specific classnames / datatypes.
    @FXML
    private TableColumn<EmailMessageBean, String> subjectcol;

    @FXML
    private TableColumn<EmailMessageBean, String> sendercol;

    @FXML
    private TableColumn<EmailMessageBean, FormatableInteger> sizecol;


    @FXML
    private TableColumn<EmailMessageBean, Date> dateCol;

    @FXML
    private TreeView<String> emailfolderstreeview;

    @FXML
    private Label downAttachLabel;

    @FXML
    private ProgressBar downAttachProgress;

    @FXML
    private Button downAttachBtn;

    private SaveAttachmentsService saveAttachmentsService;

    /*@FXML
    private TreeItem<String> root = new TreeItem<String>();
*/
    @FXML
    public void Button1Action(ActionEvent event){

        Scene scene = ViewFactory.defaultFactory.getComposeMessageScene();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("../view/icon.png")));

        stage.show();
    }



                //OLD
/*
        //javafx concurrency
        //service
        //abstract class
        //has method create task that must be implemented( to make it concrete)

        Service<Void> emailService = new Service<Void>(){

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        final EmailAccountBean emailAccountBean = new EmailAccountBean("YOUR_EMAIL_ID_HERE","YOUR_PASSWORD_HERE");

                        ObservableList<EmailMessageBean> data = getModelAccess().getSelectedFolder().getData();
                        try {
                            emailAccountBean.addEmailsData(data);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        return null;
                    }
                };
            }
        };
        emailService.start();

        //Service Class : Reusable Task
        //has return types and listens on it's state

*/



    private MenuItem showDetails = new MenuItem("show details");

    private MessageRendererService messageRendererService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        downAttachProgress.setVisible(false);

        downAttachLabel.setVisible(false);

        saveAttachmentsService = new SaveAttachmentsService(downAttachProgress,downAttachLabel);


        messageRendererService = new MessageRendererService(messageRenderer.getEngine());
        downAttachProgress.progressProperty().bind(saveAttachmentsService.progressProperty());

        FolderUpdaterService folderUpdaterService = new FolderUpdaterService(getModelAccess().getfoldersList());
        folderUpdaterService.start();
        ViewFactory viewFactory = ViewFactory.defaultFactory;

        emailtableview.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        emailtableview.setRowFactory(e -> new BoldableRowFactory<>());
        subjectcol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("subject"));
       sendercol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("sender"));
       dateCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, Date>("Date"));
        sizecol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, FormatableInteger>("size"));
//give default comparator to sizeCol
        sizecol.setComparator(new FormatableInteger(0));
        /*
        sizecol.setComparator(new Comparator<String>() {
            Integer int1,int2;

            @Override
            public int compare(String o1, String o2) {
                int1 = EmailMessageBean.formattedvalues.get(o1);
                int2 = EmailMessageBean.formattedvalues.get(o2);
                return int1.compareTo(int2);
            }
        });
*/
        EmailFolderBean<String> root = new EmailFolderBean<>("");

        emailfolderstreeview.setRoot(root);

        emailfolderstreeview.setShowRoot(false);


        /******** MULTIPLE EMAIL ACCOUNTS -- CONDITION THAT THEY SHOULD BE OF GMAIL *********/
        CreateAndRegisterEmailAccountService createAndRegisterEmailAccountService =
                new CreateAndRegisterEmailAccountService("YOUR_EMAILADDRESS_HERE",
                        "YOUR_PASSWORD_HERE",root,getModelAccess()
                );
    createAndRegisterEmailAccountService.start();

        CreateAndRegisterEmailAccountService createAndRegisterEmailAccountService2 =
                new CreateAndRegisterEmailAccountService("YOUR_EMAILADDRESS_HERE",
                        "YOUR_PASSWORD_HERE",root,getModelAccess()
                );
        createAndRegisterEmailAccountService2.start();
        /******** MULTIPLE EMAIL ACCOUNTS -- CONDITION THAT THEY SHOULD BE OF GMAIL *********/


        /*
        EmailFolderBean<String> umar = new EmailFolderBean<>("umartariqx94@gmail.com");
        root.getChildren().add(umar);

        //Folder Structure
        EmailFolderBean<String> Inbox = new EmailFolderBean<>("Inbox","CompleteInbox");
        EmailFolderBean<String> Sent = new EmailFolderBean<>("Sent","CompleteSent");
        Sent.getChildren().add(new EmailFolderBean<>("Subfolder1","SubFolder1Complete"));
        Sent.getChildren().add(new EmailFolderBean<>("Subfolder1","SubFolder1Complete2"));
        EmailFolderBean<String> Spam = new EmailFolderBean<>("Spam","CompleteSpam");
        umar.getChildren().addAll(Inbox,Sent,Spam);

*/



        /*
        root.setValue("umar@gmail.com");
        root.setGraphic(viewFactory.resolveIcon(root.getValue()));
        TreeItem<String> Inbox = new TreeItem<String>("Inbox",viewFactory.resolveIcon("Inbox"));
        TreeItem<String> Sent = new TreeItem<String>("Sent",viewFactory.resolveIcon("Sent"));
        TreeItem<String> SubItem1 = new TreeItem<String>("SubItem1",viewFactory.resolveIcon("SubItem1"));
        TreeItem<String> SubItem2 = new TreeItem<String>("SubItem2",viewFactory.resolveIcon("SubItem2"));
        Sent.getChildren().addAll(SubItem1,SubItem2);
        TreeItem<String> Spam = new TreeItem<String>("Spam",viewFactory.resolveIcon("Spam"));
        TreeItem<String> Trash = new TreeItem<String>("Trash",viewFactory.resolveIcon("Trash"));
        root.getChildren().addAll(Inbox,Sent,Spam,Trash);
        root.setExpanded(true);
*/
        emailtableview.setContextMenu(new ContextMenu(showDetails));

        emailfolderstreeview.setOnMouseClicked(e -> {
            EmailFolderBean<String> item = (EmailFolderBean<String>)emailfolderstreeview.getSelectionModel().getSelectedItem();

            //TreeItem<String> item = emailfolderstreeview.getSelectionModel().getSelectedItem();
            if(item != null && !item.isTopElement()){
                emailtableview.setItems(item.getData());
                getModelAccess().setSelectedFolder(item);
                //clear the selected message
                getModelAccess().setSelectedMessage(null);


            }
        });
        emailtableview.setOnMouseClicked(e ->{
            EmailMessageBean message = emailtableview.getSelectionModel().getSelectedItem();
            if(message != null){

               /***OLD METHOD ***/
                // messageRenderer.getEngine().loadContent(message.getContent());

                /*** WRONG METHOD ***/


                //messageRendererService.restart(); dont use servicevoid Task call method
                //use instead the run method of runnable

                //Platform.runLater(messageRendererService);
            //WRONG.. runs on application thread

                //run method of runnable interface
                //SwingUtilities.invokeLater(messageRendererService);

                /*** NEW METHOD****/
                getModelAccess().setSelectedMessage(message);
                messageRendererService.setMessageToRender(message);
                messageRendererService.restart();

            }
        });

        showDetails.setOnAction(e ->{
            try {
                //Pane pane = FXMLLoader.load(getClass().getResource("../view/EmailDetailsLayout.fxml"));
                Scene scene = viewFactory.getEmailDetailsScene();
                //getResource Method gets the resource from package where class resides
                //so you need to use the appropriate package using ../ and respective package name (hierarchy)
                //scene.getStylesheets().add(getClass().getResource("../view/application.css").toExternalForm());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.getIcons().add(new Image(MainController.class.getResourceAsStream("../view/icon.png")));

                stage.show();
            }catch (Exception exception){
                exception.printStackTrace();
            }
        });
    }
   /*
    @FXML
    void changeReadAction(ActionEvent event) {
        EmailMessageBean message = getModelAccess().getSelectedMessage();
        if(message != null){
            boolean value = message.isRead();
            message.setRead(!value);
            EmailFolderBean<String> selectedFolder = getModelAccess().getSelectedFolder();
            if(selectedFolder != null){
                if(value){
                    selectedFolder.incrementUnreadMessagesCount(1);

                }else{
                    selectedFolder.decrementUnreadMessagesCount();
                }

            }


        }
    }
    */





    @FXML
    void downAttachBtnAction(ActionEvent event) {
        EmailMessageBean messageBean = emailtableview.getSelectionModel().getSelectedItem();
        if(messageBean != null && messageBean.hasAttachments()){
            saveAttachmentsService.setMessageToDownload(messageBean);
            saveAttachmentsService.restart();
        }
    }



}
