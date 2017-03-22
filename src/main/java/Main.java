import javafx.application.Application;
import util.DBUtil;

public class Main {

    public static void main(String[] args) {
        DBUtil.Init();
        Application.launch(clientregistrationform.Main.class, args);
    }
}