package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;
import model.EmailMessageBean;
import view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class EmailDetailsController extends AbstractController implements Initializable {
    //make each controller extend abstract controller so you will not have
    //instance of ModelAccess class in each controller class

    public EmailDetailsController(ModelAccess modelAccess){
        super(modelAccess);
    }

    @FXML
    private WebView detailswebview;

    @FXML
    private Label subjectlabel;

    @FXML
    private Label senderlabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ViewFactory viewFactory = ViewFactory.defaultFactory;
        EmailMessageBean selectedMessage = getModelAccess().getSelectedMessage();


        subjectlabel.setText("Subject:  " + selectedMessage.getSubject());
        senderlabel.setText("Sender:  " + selectedMessage.getSender());

        //detailswebview.getEngine().loadContent(selectedMessage.getContent());

    }
}
