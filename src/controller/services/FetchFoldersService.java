package controller.services;

import controller.ModelAccess;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import model.EmailAccountBean;
import model.folder.EmailFolderBean;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;

public class FetchFoldersService extends Service<Void> {

    private EmailFolderBean foldersRoot;
    private EmailAccountBean emailAccountBean;
    private ModelAccess modelAccess;
    private static int NUMBER_OF_FETCHFOLDERSERVICES_ACTIVE = 0;

    //
    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                NUMBER_OF_FETCHFOLDERSERVICES_ACTIVE++;
                if(emailAccountBean != null){
                    Folder[] folders = emailAccountBean.getStore().getDefaultFolder().list();

                    for(Folder folder : folders){
                        modelAccess.addFolder(folder);
                        EmailFolderBean<String> item = new EmailFolderBean<>(folder.getName(),folder.getFullName());
                        foldersRoot.getChildren().add(item);
                        item.setExpanded(true);
                        addMessageListenerToFolder(folder,item);
                        FetchMessagesOnFolderService FetchMessagesOnFolderService = new FetchMessagesOnFolderService(item,folder);
                        FetchMessagesOnFolderService.start();

                        System.out.println("added : " + folder.getName());
                        Folder[] subfolders = folder.list();

                        for(Folder subFolder: subfolders){
                            modelAccess.addFolder(subFolder);
                            EmailFolderBean<String> subitem = new EmailFolderBean<>(subFolder.getName(),subFolder.getFullName());
                            item.getChildren().add(subitem);
                            addMessageListenerToFolder(subFolder,subitem);
                            FetchMessagesOnFolderService FetchMessagesOnSubFolderService = new FetchMessagesOnFolderService(subitem,subFolder);
                            FetchMessagesOnSubFolderService.start();

                            item.setExpanded(true);

                            System.out.println("added : " + subFolder.getName());

                        }
                    }
                }
                return null;
            }
        };
    }

    public FetchFoldersService(EmailFolderBean foldersRoot, EmailAccountBean emailAccountBean,ModelAccess modelAccess) {
        this.foldersRoot = foldersRoot;
        this.emailAccountBean = emailAccountBean;
        this.modelAccess = modelAccess;

        //Service Succeed State (property)
        this.setOnSucceeded(e->{
            NUMBER_OF_FETCHFOLDERSERVICES_ACTIVE--;
        });
    }

    private void addMessageListenerToFolder(Folder folder, EmailFolderBean<String> item){
        folder.addMessageCountListener(new MessageCountAdapter() {
            @Override
            public void messagesAdded(MessageCountEvent e) {
               for(int i=0;i<e.getMessages().length;i++){
                   try {
                       Message currentMessage = folder.getMessage(folder.getMessageCount() - i);
                        item.addEmail(0,currentMessage);
                   } catch (MessagingException e1) {
                       e1.printStackTrace();
                   }
               }
            }
        });

    }

    public static boolean noServicesActive(){
        return NUMBER_OF_FETCHFOLDERSERVICES_ACTIVE ==0;
    }
}
