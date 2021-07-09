package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.EmailAccountBean;
import model.EmailMessageBean;

public class Test {


    public static void main(String[] args) {
      final EmailAccountBean emailAccountBean = new EmailAccountBean("tuxpenguin94@gmail.com","javafximup321");
        ObservableList<EmailMessageBean> data = FXCollections.observableArrayList();
        try {
            emailAccountBean.addEmailsData(data);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
