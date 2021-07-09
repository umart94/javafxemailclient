package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.EmailAccountBean;
import model.EmailMessageBean;
import model.folder.EmailFolderBean;

import javax.mail.Folder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelAccess {
    private List<Folder> foldersList = new ArrayList<Folder>();

    private EmailMessageBean selectedMessage;
    private Map<String, EmailAccountBean> emailAccounts = new HashMap<String, EmailAccountBean>();
    private ObservableList<String> emailAccountsNames = FXCollections.observableArrayList();

    public ObservableList<String> getEmailAccountNames(){
        return emailAccountsNames;
    }

    public EmailAccountBean getEmailAccountByName(String name){
        return emailAccounts.get(name);
    }
    //From Choose Box (email sender Choice Field)
    public void addAccount(EmailAccountBean account){
        emailAccounts.put(account.getEmailAddress(), account);
        emailAccountsNames.add(account.getEmailAddress());
    }








    public List<Folder> getfoldersList(){
        return foldersList;
    }

    public void addFolder(Folder folder){
        this.foldersList.add(folder);
    }

    public EmailMessageBean getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(EmailMessageBean selectedMessage) {
        this.selectedMessage = selectedMessage;
    }
    private EmailFolderBean<String> selectedFolder;

    public EmailFolderBean<String> getSelectedFolder() {
        return selectedFolder;
    }

    public void setSelectedFolder(EmailFolderBean<String> selectedFolder) {
        this.selectedFolder = selectedFolder;
    }
}
