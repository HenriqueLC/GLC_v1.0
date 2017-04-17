import javafx.application.Application;
import util.DBUtil;

public class Main {

    public static void main(String[] args) {
        // db initialization and consistency check
        DBUtil.Init();
        // application launch
        Application.launch(controllers.Main.class, args);
    }
}