package clientregistrationform;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import util.DBUtil;
import widgets.MaskField;
import widgets.SelectKeyComboBox;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class NaturalPersonController extends GridPane implements Initializable {
    @FXML
    private ChoiceBox<String> legalPersonalityChoiceBox;

    @FXML
    private MaskField naturalPersonsRegisterMaskField;
    private boolean wasNaturalPersonsRegisterMaskFieldDisabled;

    @FXML
    private Label naturalPersonsRegisterErrorLabel;
    private boolean wasNaturalPersonsRegisterErrorLabelDisabled;

    @FXML
    private TextField naturalPersonFullNameTextField;
    private boolean wasNaturalPersonFullNameTextFieldDisabled;

    @FXML
    private Label naturalPersonFullNameWarningLabel;
    private boolean wasNaturalPersonFullNameWarningLabelDisabled;

    @FXML
    private ToggleGroup addressToggleGroup;
    private boolean wasAddressToggleGroupDisabled;

    @FXML
    private RadioButton useAddress;
    private boolean wasUseAddressDisabled;

    @FXML
    private RadioButton doNotUseAddress;
    private boolean wasDoNotUseAddressDisabled;

    @FXML
    private MaskField zipCodeMaskField;
    private boolean wasZipCodeMaskFieldDisabled;

    @FXML
    private ComboBox<String> stateSelectKeyComboBox;
    private boolean wasStateComboBoxDisabled;

    @FXML
    private TextField cityTextField;
    private boolean wasCityTextFieldDisabled;

    @FXML
    private TextField districtTextField;
    private boolean wasDistrictTextFieldDisabled;

    @FXML
    private TextField roadTextField;
    private boolean wasRoadTextFieldDisabled;

    @FXML
    private MaskField numberMaskField;
    private boolean wasNumberMaskFieldDisabled;

    @FXML
    private TextField optionalAddressInformationTextField;
    private boolean wasOptionalAddressInformationTextFieldDisabled;

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

    ChoiceBox<String> getLegalPersonalityChoiceBox() {
        return legalPersonalityChoiceBox;
    }

    void setLegalPersonalityChoiceBox(ChoiceBox<String> legalPersonalityChoiceBox) {
        this.legalPersonalityChoiceBox = legalPersonalityChoiceBox;
    }

    MaskField getNaturalPersonsRegisterMaskField() {
        return naturalPersonsRegisterMaskField;
    }

    void setNaturalPersonsRegisterMaskField(MaskField naturalPersonsRegisterMaskField) {
        this.naturalPersonsRegisterMaskField = naturalPersonsRegisterMaskField;
    }

    Label getNaturalPersonsRegisterErrorLabel() {
        return naturalPersonsRegisterErrorLabel;
    }

    void setNaturalPersonsRegisterErrorLabel(Label naturalPersonsRegisterErrorLabel) {
        this.naturalPersonsRegisterErrorLabel = naturalPersonsRegisterErrorLabel;
    }

    TextField getNaturalPersonFullNameTextField() {
        return naturalPersonFullNameTextField;
    }

    void setNaturalPersonFullNameTextField(TextField naturalPersonFullNameTextField) {
        this.naturalPersonFullNameTextField = naturalPersonFullNameTextField;
    }

    Label getNaturalPersonFullNameWarningLabel() {
        return naturalPersonFullNameWarningLabel;
    }

    void setNaturalPersonFullNameWarningLabel(Label naturalPersonFullNameWarningLabel) {
        this.naturalPersonFullNameWarningLabel = naturalPersonFullNameWarningLabel;
    }

    ToggleGroup getAddressToggleGroup() {
        return addressToggleGroup;
    }

    void setAddressToggleGroup(ToggleGroup addressToggleGroup) {
        this.addressToggleGroup = addressToggleGroup;
    }

    RadioButton getUseAddress() {
        return useAddress;
    }

    void setUseAddress(RadioButton useAddress) {
        this.useAddress = useAddress;
    }

    RadioButton getDoNotUseAddress() {
        return doNotUseAddress;
    }

    void setDoNotUseAddress(RadioButton doNotUseAddress) {
        this.doNotUseAddress = doNotUseAddress;
    }

    MaskField getZipCodeMaskField() {
        return zipCodeMaskField;
    }

    void setZipCodeMaskField(MaskField zipCodeMaskField) {
        this.zipCodeMaskField = zipCodeMaskField;
    }

    ComboBox<String> getStateComboBox() {
        return stateSelectKeyComboBox;
    }

    void setStateComboBox(SelectKeyComboBox stateSelectKeyComboBox) {
        this.stateSelectKeyComboBox = stateSelectKeyComboBox;
    }

    TextField getCityTextField() {
        return cityTextField;
    }

    void setCityTextField(TextField cityTextField) {
        this.cityTextField = cityTextField;
    }

    TextField getDistrictTextField() {
        return districtTextField;
    }

    void setDistrictTextField(TextField districtTextField) {
        this.districtTextField = districtTextField;
    }

    public TextField getRoadTextField() {
        return roadTextField;
    }

    void setRoadTextField(TextField roadTextField) {
        this.roadTextField = roadTextField;
    }

    MaskField getNumberMaskField() {
        return numberMaskField;
    }

    void setNumberMaskField(MaskField numberMaskField) {
        this.numberMaskField = numberMaskField;
    }

    TextField getOptionalAddressInformationTextField() {
        return optionalAddressInformationTextField;
    }

    void setOptionalAddressInformationTextField(TextField optionalAddressInformationTextField) {
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
        // [ ] use address [x] do not use address
        useAddress.setSelected(false);
        doNotUseAddress.setSelected(true);
        // Enable/disable
        naturalPersonsRegisterErrorLabel.setVisible(false);
        naturalPersonFullNameWarningLabel.setVisible(false);
        useAddress.setDisable(true);
        doNotUseAddress.setDisable(true);
        zipCodeMaskField.setDisable(true);
        stateSelectKeyComboBox.setDisable(true);
        cityTextField.setDisable(true);
        districtTextField.setDisable(true);
        roadTextField.setDisable(true);
        numberMaskField.setDisable(true);
        optionalAddressInformationTextField.setDisable(true);
        // Natural persons register listeners
        naturalPersonsRegisterMaskField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            // save the current state
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // on focus
                if (newValue) {
                    wasUseAddressDisabled = useAddress.isDisabled();
                    wasDoNotUseAddressDisabled = doNotUseAddress.isDisabled();
                    wasZipCodeMaskFieldDisabled = zipCodeMaskField.isDisabled();
                    wasStateComboBoxDisabled = stateSelectKeyComboBox.isDisabled();
                    wasCityTextFieldDisabled = cityTextField.isDisabled();
                    wasDistrictTextFieldDisabled = districtTextField.isDisabled();
                    wasRoadTextFieldDisabled = roadTextField.isDisabled();
                    wasNumberMaskFieldDisabled = numberMaskField.isDisabled();
                    wasOptionalAddressInformationTextFieldDisabled = optionalAddressInformationTextField.isDisabled();
                }
            }
        });
        naturalPersonsRegisterMaskField.plainTextProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() > 0 && newValue.length() < 11) {
                    naturalPersonFullNameTextField.setDisable(true);
                    naturalPersonFullNameWarningLabel.setDisable(true);
                    useAddress.setDisable(true);
                    doNotUseAddress.setDisable(true);
                    zipCodeMaskField.setDisable(true);
                    stateSelectKeyComboBox.setDisable(true);
                    cityTextField.setDisable(true);
                    districtTextField.setDisable(true);
                    roadTextField.setDisable(true);
                    numberMaskField.setDisable(true);
                    optionalAddressInformationTextField.setDisable(true);
                } else {
                    // if contained in database, do sth; else, unlock next field and load saved state
                    naturalPersonFullNameTextField.setDisable(false);
                    naturalPersonFullNameWarningLabel.setDisable(false);
                    useAddress.setDisable(wasUseAddressDisabled);
                    doNotUseAddress.setDisable(wasDoNotUseAddressDisabled);
                    zipCodeMaskField.setDisable(wasZipCodeMaskFieldDisabled);
                    stateSelectKeyComboBox.setDisable(wasStateComboBoxDisabled);
                    cityTextField.setDisable(wasCityTextFieldDisabled);
                    districtTextField.setDisable(wasDistrictTextFieldDisabled);
                    roadTextField.setDisable(wasRoadTextFieldDisabled);
                    numberMaskField.setDisable(wasNumberMaskFieldDisabled);
                    optionalAddressInformationTextField.setDisable(wasOptionalAddressInformationTextFieldDisabled);
                }
            }
        });
        // Natural person full name listener
        naturalPersonFullNameTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // on focus
                if (newValue) {
                    wasZipCodeMaskFieldDisabled = zipCodeMaskField.isDisabled();
                    wasStateComboBoxDisabled = stateSelectKeyComboBox.isDisabled();
                    wasCityTextFieldDisabled = cityTextField.isDisabled();
                    wasDistrictTextFieldDisabled = districtTextField.isDisabled();
                    wasRoadTextFieldDisabled = roadTextField.isDisabled();
                    wasNumberMaskFieldDisabled = numberMaskField.isDisabled();
                    wasOptionalAddressInformationTextFieldDisabled = optionalAddressInformationTextField.isDisabled();
                }
            }
        });
        naturalPersonFullNameTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() == 0) {
                    useAddress.setDisable(true);
                    doNotUseAddress.setDisable(true);
                    zipCodeMaskField.setDisable(true);
                    stateSelectKeyComboBox.setDisable(true);
                    cityTextField.setDisable(true);
                    districtTextField.setDisable(true);
                    roadTextField.setDisable(true);
                    numberMaskField.setDisable(true);
                    optionalAddressInformationTextField.setDisable(true);
                } else {
                    // if contained in database, do sth; else, unlock next field and load saved state
                    useAddress.setDisable(false);
                    doNotUseAddress.setDisable(false);
                    zipCodeMaskField.setDisable(wasZipCodeMaskFieldDisabled);
                    stateSelectKeyComboBox.setDisable(wasStateComboBoxDisabled);
                    cityTextField.setDisable(wasCityTextFieldDisabled);
                    districtTextField.setDisable(wasDistrictTextFieldDisabled);
                    roadTextField.setDisable(wasRoadTextFieldDisabled);
                    numberMaskField.setDisable(wasNumberMaskFieldDisabled);
                    optionalAddressInformationTextField.setDisable(wasOptionalAddressInformationTextFieldDisabled);
                }
            }
        });
        // Address listener
        addressToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (newValue.getToggleGroup().getToggles().get(1).isSelected()) {
                    zipCodeMaskField.clear();
                    zipCodeMaskField.setDisable(true);
                    stateSelectKeyComboBox.valueProperty().set(null);
                    stateSelectKeyComboBox.setDisable(true);
                    cityTextField.clear();
                    cityTextField.setDisable(true);
                    districtTextField.clear();
                    districtTextField.setDisable(true);
                    roadTextField.clear();
                    roadTextField.setDisable(true);
                    numberMaskField.clear();
                    numberMaskField.setDisable(true);
                    optionalAddressInformationTextField.clear();
                    optionalAddressInformationTextField.setDisable(true);
                } else {
                    zipCodeMaskField.setDisable(false);
                }
            }
        });
        // Zip code listener
        zipCodeMaskField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            // save the current state
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // on focus
                if (newValue) {
                    wasStateComboBoxDisabled = stateSelectKeyComboBox.isDisabled();
                    wasCityTextFieldDisabled = cityTextField.isDisabled();
                    wasDistrictTextFieldDisabled = districtTextField.isDisabled();
                    wasRoadTextFieldDisabled = roadTextField.isDisabled();
                    wasNumberMaskFieldDisabled = numberMaskField.isDisabled();
                    wasOptionalAddressInformationTextFieldDisabled = optionalAddressInformationTextField.isDisabled();
                }
            }
        });
        zipCodeMaskField.plainTextProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() != 8) {
                    stateSelectKeyComboBox.valueProperty().set(null);
                    stateSelectKeyComboBox.setDisable(true);
                    cityTextField.clear();
                    cityTextField.setDisable(true);
                    districtTextField.clear();
                    districtTextField.setDisable(true);
                    roadTextField.clear();
                    roadTextField.setDisable(true);
                    numberMaskField.clear();
                    numberMaskField.setDisable(true);
                    optionalAddressInformationTextField.clear();
                    optionalAddressInformationTextField.setDisable(true);
                } else {
                    // check if the zip code is contained in the db
                    try (Connection connection = DBUtil.getConnection()) {
                        // Try to load
                        System.out.println("Checking zip code...");
                        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT S.Abbreviation, C.Name, Z.District, Z.Road FROM States S, Cities C, ZipCodes Z WHERE S.ID = Z.FK_States_ZipCodes AND C.ID = Z.FK_Cities_ZipCodes AND Z.ZipCode = ?;")) {
                            preparedStatement.setInt(1, Integer.parseInt(newValue));
                            ResultSet resultSet = preparedStatement.executeQuery();
                            // if it is contained, then load the values; else, unlock next field and load saved state
                            if (resultSet.next()) {
                                System.out.println("Founded!");
                                String abbreviation = resultSet.getString("Abbreviation");
                                String city = resultSet.getString("Name");
                                String district = resultSet.getString("District");
                                String road = resultSet.getString("Road");
                                if (city.isEmpty()) {
                                    cityTextField.setDisable(false);
                                }

                                if (district.isEmpty()) {
                                    districtTextField.setDisable(false);
                                }

                                if (road.isEmpty()) {
                                    roadTextField.setDisable(false);
                                }
                                stateSelectKeyComboBox.setValue(abbreviation);
                                cityTextField.setText(city);
                                districtTextField.setText(district);
                                roadTextField.setText(road);
                                numberMaskField.setDisable(false);
                            } else {
                                System.out.println("Not founded!");
                                stateSelectKeyComboBox.setDisable(false);
                                // if unloaded, connect and load from the db
                                if (stateSelectKeyComboBox.getItems().isEmpty()) {
                                    // Try to load
                                    System.out.println("Loading states...");
                                    try (Statement statement = connection.createStatement()) {
                                        resultSet = statement.executeQuery("SELECT * FROM States;");
                                        System.out.println("Loaded!");
                                        ArrayList<String> abbreviations = new ArrayList<>();
                                        while (resultSet.next()) {
                                            abbreviations.add(resultSet.getString("Abbreviation"));
                                        }
                                        stateSelectKeyComboBox.setItems(FXCollections.observableList(abbreviations));
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}