package controller;

import controller.services.EmailSenderService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import model.EmailConstants;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ComposeMessageController extends AbstractController implements Initializable {

    public ComposeMessageController(ModelAccess modelAccess) {
        super(modelAccess);

    }

private List attachmentsList = new ArrayList<File>();
    @FXML
    private Button attachBtn;

    @FXML
    private Label attachmentsLabel;

    @FXML
    private ChoiceBox<String> senderChoice;
    @FXML
    private HTMLEditor composeArea;
    @FXML
    private TextField recipientField;

    @FXML
    private TextField subjectField;

    @FXML
    private Label errorLabel;



    @FXML
    void attachBtnAction() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile!= null){
            attachmentsList.add(selectedFile);
            attachmentsLabel.setText(attachmentsLabel.getText()+" " + selectedFile.getName()+ " "+" ; ");
        }
    }

    @FXML
    void sendBtnAction() {
        errorLabel.setText("");
        EmailSenderService emailSenderService =
                new EmailSenderService(getModelAccess().getEmailAccountByName(senderChoice.getValue()),
                        subjectField.getText(),
                        recipientField.getText(),
                        composeArea.getHtmlText(),
                        attachmentsList);
        emailSenderService.restart();
        emailSenderService.setOnSucceeded(e-> {
            if(emailSenderService.getValue() == EmailConstants.MESSAGE_SENT_OK){
                errorLabel.setText("Message Sent Successfully !");

            }else{
                errorLabel.setTextFill(Color.web("#BA0000", 1.0));
                errorLabel.setText("ERROR IN SENDING MESSAGE");

            }
        });
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        senderChoice.setItems(getModelAccess().getEmailAccountNames());
        senderChoice.setValue(getModelAccess().getEmailAccountNames().get(0));


    }
}
