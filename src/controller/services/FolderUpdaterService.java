package controller.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.Folder;
import java.util.List;

public class FolderUpdaterService extends Service<Void> {

    public FolderUpdaterService(List<Folder> folderList) {
        this.folderList = folderList;
    }

    private List<Folder> folderList;

    //after every few seconds
    //check for new messages in a folder
    //will run until program runs
    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for(;;){
                    try{
                        Thread.sleep(10000);
                        //To avoid ConcurrentModificationException
                        //use the static boolean method to check if NUMBER_OF_FETCHFOLDERSERVICES_ACTIVE is 0
                        //if NUMBER_OF_FETCHFOLDERSERVICES_ACTIVE is 0 then AND ONLY then we will iterate / perform any actions on folders.
                        if (FetchFoldersService.noServicesActive()) {
                            for(Folder folder: folderList){
                                if(folder.getType() != Folder.HOLDS_FOLDERS && folder.isOpen()){
                                    folder.getMessageCount();
                                }

                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
        };
    }
}
