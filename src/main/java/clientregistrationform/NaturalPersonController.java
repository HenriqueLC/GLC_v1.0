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

public class NaturalPersonController extends GridPane implements Initializable {
    @FXML
    private ChoiceBox<String> legalPersonalityChoiceBox;

    @FXML
    private MaskField naturalPersonsRegisterMaskField;

    @FXML
    private Label naturalPersonsRegisterErrorLabel;

    @FXML
    private TextField naturalPersonFullNameTextField;

    @FXML
    private Label naturalPersonFullNameWarningLabel;

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

    NaturalPersonController() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setResources(ResourceBundle.getBundle("UIResources", new Locale("pt", "BR")));
        fxmlLoader.setLocation(getClass().getResource("NaturalPerson.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public ChoiceBox<String> getLegalPersonalityChoiceBox() {
        return legalPersonalityChoiceBox;
    }

    public void setLegalPersonalityChoiceBox(ChoiceBox<String> legalPersonalityChoiceBox) {
        this.legalPersonalityChoiceBox = legalPersonalityChoiceBox;
    }

    public MaskField getNaturalPersonsRegisterMaskField() {
        return naturalPersonsRegisterMaskField;
    }

    public void setNaturalPersonsRegisterMaskField(MaskField naturalPersonsRegisterMaskField) {
        this.naturalPersonsRegisterMaskField = naturalPersonsRegisterMaskField;
    }

    public Label getNaturalPersonsRegisterErrorLabel() {
        return naturalPersonsRegisterErrorLabel;
    }

    public void setNaturalPersonsRegisterErrorLabel(Label naturalPersonsRegisterErrorLabel) {
        this.naturalPersonsRegisterErrorLabel = naturalPersonsRegisterErrorLabel;
    }

    public TextField getNaturalPersonFullNameTextField() {
        return naturalPersonFullNameTextField;
    }

    public void setNaturalPersonFullNameTextField(TextField naturalPersonFullNameTextField) {
        this.naturalPersonFullNameTextField = naturalPersonFullNameTextField;
    }

    public Label getNaturalPersonFullNameWarningLabel() {
        return naturalPersonFullNameWarningLabel;
    }

    public void setNaturalPersonFullNameWarningLabel(Label naturalPersonFullNameWarningLabel) {
        this.naturalPersonFullNameWarningLabel = naturalPersonFullNameWarningLabel;
    }

    public ToggleGroup getAddressToggleGroup() {
        return addressToggleGroup;
    }

    public void setAddressToggleGroup(ToggleGroup addressToggleGroup) {
        this.addressToggleGroup = addressToggleGroup;
    }

    public MaskField getZipCodeMaskField() {
        return zipCodeMaskField;
    }

    public void setZipCodeMaskField(MaskField zipCodeMaskField) {
        this.zipCodeMaskField = zipCodeMaskField;
    }

    public ComboBox<String> getStateComboBox() {
        return stateComboBox;
    }

    public void setStateComboBox(ComboBox<String> stateComboBox) {
        this.stateComboBox = stateComboBox;
    }

    public TextField getCityTextField() {
        return cityTextField;
    }

    public void setCityTextField(TextField cityTextField) {
        this.cityTextField = cityTextField;
    }

    public TextField getDistrictTextField() {
        return districtTextField;
    }

    public void setDistrictTextField(TextField districtTextField) {
        this.districtTextField = districtTextField;
    }

    public TextField getStreetTextField() {
        return streetTextField;
    }

    public void setStreetTextField(TextField streetTextField) {
        this.streetTextField = streetTextField;
    }

    public MaskField getNumberMaskField() {
        return numberMaskField;
    }

    public void setNumberMaskField(MaskField numberMaskField) {
        this.numberMaskField = numberMaskField;
    }

    public TextField getOptionalAddressInformationTextField() {
        return optionalAddressInformationTextField;
    }

    public void setOptionalAddressInformationTextField(TextField optionalAddressInformationTextField) {
        this.optionalAddressInformationTextField = optionalAddressInformationTextField;
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
        naturalPersonsRegisterErrorLabel.setVisible(false);
        naturalPersonFullNameWarningLabel.setVisible(false);
        // [ ] use address [x] do not use address
        addressToggleGroup.getToggles().get(0).setSelected(false);
        addressToggleGroup.getToggles().get(1).setSelected(true);
    }

}
