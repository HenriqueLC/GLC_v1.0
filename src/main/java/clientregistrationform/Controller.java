package clientregistrationform;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javax.xml.stream.Location;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import org.controlsfx.control.textfield.*;

public class Controller implements Initializable {
    public GridPane personForm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NaturalPersonController naturalPersonController = new NaturalPersonController();
        personForm.getChildren().add(naturalPersonController);
        naturalPersonController.getLegalPersonalityChoiceBox().valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(Objects.equals(newValue, resources.getString("juridical"))) {

                }
        }
        });

    }
}
