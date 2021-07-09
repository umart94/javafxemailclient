package model.folder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import model.EmailMessageBean;
import view.ViewFactory;

import javax.mail.Message;
import javax.mail.MessagingException;

import static javax.mail.Flags.Flag;

public class EmailFolderBean<T> extends TreeItem<String> {

    private boolean topElement = false;
    private int unreadMessageCount;
    private String name;
    @SuppressWarnings("unused")
    private String completeName;
    private ObservableList<EmailMessageBean> data = FXCollections.observableArrayList();

    /**
     * Constructor for Top/Root Elements in TreeView
     * @param value
     */

    //Root Tree Items
    public EmailFolderBean(String value){
        super(value, ViewFactory.defaultFactory.resolveIcon(value));
        this.name = value;
        this.completeName = value;
        this.data = null;
        topElement = true;
        this.setExpanded(true);


    }

    //Sub-Tree Items
    public EmailFolderBean(String value,String completeName){
        super(value, ViewFactory.defaultFactory.resolveIcon(value));
        this.name = value;
        this.completeName = value;
    }

    private void updateValue(){
        if(unreadMessageCount > 0){
            this.setValue((String) name+"("+unreadMessageCount+")");
        }

    }

    public void incrementUnreadMessagesCount(int newMessages){
        unreadMessageCount = unreadMessageCount + newMessages;
        updateValue();
    }

    public void decrementUnreadMessagesCount(){
        unreadMessageCount--;
        updateValue();
    }

    /*public void addEmail(EmailMessageBean message){
        data.add(message);
        if(!message.isRead()){
            incrementUnreadMessagesCount(1);

        }

    }
*/
    public void addEmail(int position, Message message) throws MessagingException {
       boolean isRead = message.getFlags().contains(Flag.SEEN);
       EmailMessageBean emailMessageBean = new EmailMessageBean(message.getSubject(),
               message.getFrom()[0].toString()
               ,message.getSize(),message.getSentDate(),isRead,
               message
               );
        if (position<0) {
            data.add(emailMessageBean);
        } else {
            data.add(position,emailMessageBean);
        }
        if(!isRead){
           incrementUnreadMessagesCount(1);

       }

    }

    public boolean isTopElement(){
        return topElement;
    }

    public ObservableList<EmailMessageBean> getData(){
        return data;
    }



}
