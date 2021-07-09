package controller.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import model.EmailMessageBean;

import javax.mail.internet.MimeBodyPart;

public class SaveAttachmentsService extends Service<Void> {


    private EmailMessageBean messageToDownload;
    //we save attachments in this location
    private String LOCATION_OF_DOWNLOADS = System.getProperty("user.home")+"/DownloadedAttachments/";

    private ProgressBar progressBar;
    private Label label;

    public SaveAttachmentsService(ProgressBar progressBar, Label label) {
        //showVisuals(false);
        this.progressBar = progressBar;
        this.label = label;
        this.setOnRunning(e->{
            showVisuals(true);
        });
        this.setOnSucceeded(e->{
            showVisuals(false);
        });
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                for(MimeBodyPart mbp : messageToDownload.getAttachmentsList()){
                   //updateProgress(WORKDONE,MAX);
                    updateProgress(messageToDownload.getAttachmentsList().indexOf(mbp),messageToDownload.getAttachmentsList().size());

                    mbp.saveFile(LOCATION_OF_DOWNLOADS+mbp.getFileName());
                }

                return null;
            }
        };
    }

    public EmailMessageBean getMessageToDownload() {
        return messageToDownload;
    }

    //always call before starting
    public void setMessageToDownload(EmailMessageBean messageToDownload) {
        this.messageToDownload = messageToDownload;
    }

    private void showVisuals(boolean show){
        progressBar.setVisible(show);
        label.setVisible(show);
    }
}
