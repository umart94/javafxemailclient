package controller.services;

import controller.ModelAccess;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import model.EmailAccountBean;
import model.EmailConstants;
import model.folder.EmailFolderBean;

public class CreateAndRegisterEmailAccountService extends Service<Integer> {

    //IMAP or POP must be enabled on email provider and less secure app access must also be enabled

    private String emailAddress = "";
    private String password = "";
    private EmailFolderBean<String> folderRoot;
    private ModelAccess modelAccess;

    public CreateAndRegisterEmailAccountService(String emailAddress, String password, EmailFolderBean<String> folderRoot,ModelAccess modelAccess) {
        this.emailAddress = emailAddress;
        this.password = password;
        this.folderRoot = folderRoot;
        this.modelAccess = modelAccess;
    }

    @Override
    protected Task<Integer> createTask() {
        return new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                EmailAccountBean emailAccountBean = new EmailAccountBean(emailAddress,password);

                if(emailAccountBean.getLoginState() == EmailConstants.LOGIN_STATE_SUCCEEDED){
                    modelAccess.addAccount(emailAccountBean);
                    EmailFolderBean<String> emailFolderBean = new EmailFolderBean<String>(emailAddress);
                    folderRoot.getChildren().add(emailFolderBean);
                    FetchFoldersService fetchFoldersService = new FetchFoldersService(emailFolderBean,emailAccountBean,modelAccess);
                    fetchFoldersService.start();

                }
                return emailAccountBean.getLoginState();
            }
        };
    }
}
