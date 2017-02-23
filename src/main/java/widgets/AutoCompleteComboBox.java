package widgets;

import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class AutoCompleteComboBox<T> extends ComboBox<T> {

    // --- Auto complete comparator
    public interface ComparisonMethod<T> {
        boolean matches(String typedText, T objectToCompare);
    }
    private ComparisonMethod<T> comparisonMethod;

    private AutoCompleteComboBox<T> autoCompleteComboBox;
    private ObservableList<T> items;
    private ObservableList<T> suggestions = FXCollections.observableArrayList();

    // --- Constructors
    public AutoCompleteComboBox() {
        super();
    }

    public AutoCompleteComboBox(ObservableList<T> items) {
        super(items);
    }

    public AutoCompleteComboBox(ComparisonMethod<T> comparisonMethod) {
        super();
        this.comparisonMethod = comparisonMethod;
    }

    public AutoCompleteComboBox(ObservableList<T> items, ComparisonMethod<T> comparisonMethod) {
        super(items);
        this.comparisonMethod = comparisonMethod;
    }

    // --- Methods
    public void setComparisonMethod(ComparisonMethod<T> comparisonMethod) {
        this.comparisonMethod = comparisonMethod;
    }

    public void setMaxNumChars(int numChars){
        // here we reject any change which exceeds the length
        UnaryOperator<TextFormatter.Change> rejectChange = c -> {
            // check if the change might effect the validating predicate
            if (c.isContentChange()) {
                // check if change is valid
                if (c.getControlNewText().length() > numChars) {
                    // invalid change, return null to reject the change
                    return null;
                }
            }
            // valid change: accept the change by returning it
            return c;
        };
        this.getEditor().setTextFormatter(new TextFormatter(rejectChange));
    }

    public void initialize() {
        // AutoCompleteComboBox
        autoCompleteComboBox = this;
        // List of items
        items = autoCompleteComboBox.getItems();
        // List of suggested items
        suggestions.addAll(items);
        // Set editable (true)
        autoCompleteComboBox.setEditable(true);
        // Listeners
        autoCompleteComboBox.armedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!autoCompleteComboBox.isShowing()) {
                    int size = suggestions.size() > 10 ? 10 : suggestions.size();
                    autoCompleteComboBox.setVisibleRowCount(size);
                    autoCompleteComboBox.show();
                }
            }
        });
        autoCompleteComboBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (!autoCompleteComboBox.getSelectionModel().isEmpty()) {
                autoCompleteComboBox.getEditor().positionCaret(autoCompleteComboBox.getSelectionModel().getSelectedItem().toString().length());
            }
        });
        autoCompleteComboBox.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
            // Out focus
            if (!newValue) {
                String typedText = autoCompleteComboBox.getEditor().getText();
                items.stream().filter(item -> item != null && typedText != null && !typedText.trim().isEmpty() && Objects.equals(item.toString().toLowerCase(), typedText.toLowerCase())).forEach(item -> autoCompleteComboBox.getSelectionModel().select(item));
            }
        });
        // Event handlers
        autoCompleteComboBox.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.DOWN) {
                if (!autoCompleteComboBox.isShowing() && !suggestions.isEmpty()) {
                    int size = suggestions.size() > 10 ? 10 : suggestions.size();
                    autoCompleteComboBox.setVisibleRowCount(size);
                    autoCompleteComboBox.show();
                }
            }
        });
        autoCompleteComboBox.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            // Clear the selection
            if (event.getCode() != KeyCode.DOWN && event.getCode() != KeyCode.UP && !autoCompleteComboBox.getSelectionModel().isEmpty()) {
                String typedText = autoCompleteComboBox.getEditor().getText();
                int caretPosition = autoCompleteComboBox.getEditor().getCaretPosition();
                autoCompleteComboBox.getSelectionModel().clearSelection();
                autoCompleteComboBox.getEditor().setText(typedText);
                autoCompleteComboBox.getEditor().positionCaret(caretPosition);
            }
            // Show new suggestions
            if (event.getCode() != KeyCode.ENTER && event.getCode() != KeyCode.LEFT && event.getCode() != KeyCode.RIGHT && !event.getCode().equals(KeyCode.SHIFT) && !event.getCode().equals(KeyCode.CONTROL) && !event.isControlDown() && event.getCode() != KeyCode.HOME && event.getCode() != KeyCode.END && event.getCode() != KeyCode.TAB && event.getCode() != KeyCode.UP && event.getCode() != KeyCode.DOWN) {
                // Update the suggestions
                String typedText = autoCompleteComboBox.getEditor().getText();
                suggestions.clear();
                if (typedText.trim().isEmpty()) {
                    suggestions.addAll(items);
                }
                else {
                    suggestions.addAll(items.stream().filter(item -> item != null && comparisonMethod.matches(typedText, item)).collect(Collectors.toList()));
                }
                autoCompleteComboBox.setItems(suggestions);
                if (typedText.trim().isEmpty() || suggestions.isEmpty()) {
                    autoCompleteComboBox.hide();
                }
                else {
                    int size = suggestions.size() > 10 ? 10 : suggestions.size();
                    autoCompleteComboBox.hide();
                    autoCompleteComboBox.setVisibleRowCount(size);
                    autoCompleteComboBox.show();
                }
            }
        });
    }
}