import javafx.application.Application;

public class Main {

    public static void main(String[] args) {
        Initialization.dbInitialization();
        Application.launch(clientregistrationform.Main.class, args);
    }
}