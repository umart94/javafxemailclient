package model;

public class Singleton {
    private Singleton(){}
    private static Singleton instance = new Singleton();
    public static Singleton getInstance(){
        return instance;
    }


    private EmailMessageBean message;
    public EmailMessageBean getMessage() {
        return message;
    }

    public void setMessage(EmailMessageBean message) {
        this.message = message;
    }


}
