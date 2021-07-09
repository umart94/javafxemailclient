package model;


import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import model.table.AbstractTableItem;
import model.table.FormatableInteger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.util.*;

public class EmailMessageBean extends AbstractTableItem {
    private SimpleStringProperty sender;
    private SimpleStringProperty subject;
    private SimpleObjectProperty<FormatableInteger> size;

    private Message messageReference;

    private SimpleObjectProperty<Date> date;
    //Attachments Handling
    private List<MimeBodyPart> attachmentsList = new ArrayList<MimeBodyPart>();
    private StringBuffer attachmentsNames = new StringBuffer();



    public Message getMessageReference() {
        return messageReference;
    }

    public void setMessageReference(Message messageReference) {
        this.messageReference = messageReference;
    }


    //public static Map<String, Integer> formattedvalues = new HashMap<String, Integer>();

    public EmailMessageBean(String subject, String sender, int size,Date date,boolean isRead,Message messageReference){
        super(isRead);
        this.subject = new SimpleStringProperty(subject);
        this.sender = new SimpleStringProperty(sender);
        this.size = new SimpleObjectProperty<FormatableInteger>(new FormatableInteger(size));
        this.messageReference = messageReference;
        this.date = new SimpleObjectProperty<Date>(date) ;
    }

    public Date getDate(){
        return date.get();
    }

    public String getSender(){
        return sender.get();
    }

    public String getSubject(){
        return subject.get();
    }


    public FormatableInteger getSize(){
        return size.get();
    }





    @Override
    public String toString() {
        return "EmailMessageBean{" +
                "sender=" + sender +
                ", subject=" + subject +
                ", size=" + size +
                '}';
    }

    public List<MimeBodyPart> getAttachmentsList() {
        return attachmentsList;
    }

    public void setAttachmentsList(List<MimeBodyPart> attachmentsList) {
        this.attachmentsList = attachmentsList;
    }

    public StringBuffer getAttachmentsNames() {
        return attachmentsNames;
    }

    public void setAttachmentsNames(StringBuffer attachmentsNames) {
        this.attachmentsNames = attachmentsNames;
    }
    public void addAttachment(MimeBodyPart mbp) throws MessagingException {
        attachmentsList.add(mbp);
        attachmentsNames.append(mbp.getFileName() + "; ");

    }

    public boolean hasAttachments(){
        return attachmentsList.size() > 0;

    }
    //clear methods:
    public void clearAttachments(){
        attachmentsList.clear();
        attachmentsNames.setLength(0);

    }
}
