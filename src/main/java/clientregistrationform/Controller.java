package clientregistrationform;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import widgets.MaskField;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Text legalPersonalityText;
    public ChoiceBox<String> legalPersonalityChoiceBox;
    public Text personsRegisterText;
    public MaskField personsRegisterMaskField;
    public Label personsRegisterError;

    public void initialize(URL location, ResourceBundle resources) {

        try {
            legalPersonalityChoiceBox.setItems(
                    FXCollections.observableArrayList(
                            new String(resources.getString("physical").getBytes("ISO-8859-1"), "UTF-8"),
                            new String(resources.getString("juridical").getBytes("ISO-8859-1"), "UTF-8"),
                            new String(resources.getString("publicAgency").getBytes("ISO-8859-1"), "UTF-8")
                    )
            );
            legalPersonalityChoiceBox.getSelectionModel().selectFirst();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        personsRegisterText.setText("CPF");

        personsRegisterMaskField.setMask("DDD.DDD.DDD-DD");


    }
}
