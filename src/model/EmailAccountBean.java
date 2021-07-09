package model;

import javafx.collections.ObservableList;

import javax.mail.*;
import java.util.Properties;

public class EmailAccountBean {
    private String emailAddress;
    private String password;
    private Properties properties;

    //JavaMail
    private Store store;
    private Session session;
    private int loginState = EmailConstants.LOGIN_STATE_NOT_READY;


    public int getLoginState() {
        return loginState;
    }

    public void setLoginState(int loginState) {
        this.loginState = loginState;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public EmailAccountBean(String emailAddress,String password){
        this.emailAddress = emailAddress;
        this.password = password;
        properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.transport.protocol", "smtps");
        properties.put("mail.smtps.host", "smtp.gmail.com");
        properties.put("mail.smtps.auth", "true");
        properties.put("incomingHost", "imap.gmail.com");
        properties.put("outgoingHost", "smtp.gmail.com");

        Authenticator auth = new Authenticator(){
          @Override
          protected PasswordAuthentication getPasswordAuthentication(){
              return new PasswordAuthentication(emailAddress,password);
          }
        };
        //Connection:
        session = Session.getInstance(properties,auth);
        try{
            this.store = session.getStore();
            store.connect(properties.getProperty("incomingHost"),emailAddress,password);
            System.out.println("EmailAccountBean Constructed Successfully!");
            loginState = EmailConstants.LOGIN_STATE_SUCCEEDED;
        }catch(Exception e){
            e.printStackTrace();
            loginState = EmailConstants.LOGIN_STATE_FAILED_BY_CREDENTIALS;
        }
    }

    public void addEmailsData(ObservableList<EmailMessageBean> data) throws MessagingException{
        try {
            System.out.println(Thread.currentThread().getName());
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);

            for(int i= folder.getMessageCount();i>0;i--){
                Message message = folder.getMessage(i);
                EmailMessageBean messageBean = new EmailMessageBean(message.getSubject(),
                        message.getFrom()[0].toString(),message.getSize(),message.getSentDate(),message.getFlags().contains(Flags.Flag.SEEN),message);
                System.out.println("Got : " + messageBean);
                data.add(messageBean);

                //Threads and Daemon Threads
                //We want to stop blocking of GUI while messages load
                //JavaFX Application Thread retrieves emails
                //we want to create multiple threads

                /*************************/
                //code for multithreading
                /**************************/


            }
        }catch(MessagingException e){
            e.printStackTrace();
        }

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
