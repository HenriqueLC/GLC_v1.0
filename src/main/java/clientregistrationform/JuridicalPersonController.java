package clientregistrationform;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import widgets.MaskField;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class JuridicalPersonController extends GridPane implements Initializable {
    @FXML
    private ChoiceBox<String> legalPersonalityChoiceBox;

    @FXML
    private MaskField juridicalPersonNationalRegisterMaskField;

    @FXML
    private Label juridicalPersonNationalRegisterErrorLabel;

    @FXML
    private TextField companyNameTextField;

    @FXML
    private Label companyNameWarningLabel;

    @FXML
    private ToggleGroup addressToggleGroup;

    @FXML
    private MaskField zipCodeMaskField;

    @FXML
    private ComboBox<String> stateComboBox;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField districtTextField;

    @FXML
    private TextField streetTextField;

    @FXML
    private MaskField numberMaskField;

    @FXML
    private TextField optionalAddressInformationTextField;


    JuridicalPersonController() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setResources(ResourceBundle.getBundle("UIResources", new Locale("pt", "BR")));
        fxmlLoader.setLocation(getClass().getResource("JuridicalPerson.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        legalPersonalityChoiceBox.setItems(
                FXCollections.observableArrayList(
                        resources.getString("natural"),
                        resources.getString("juridical"),
                        resources.getString("public.agency")
                )
        );
        // physical person
        legalPersonalityChoiceBox.getSelectionModel().selectFirst();
        juridicalPersonNationalRegisterErrorLabel.setVisible(false);
        companyNameWarningLabel.setVisible(false);
        // [ ] use address [x] do not use address
        addressToggleGroup.getToggles().get(0).setSelected(false);
        addressToggleGroup.getToggles().get(1).setSelected(true);
    }
}
