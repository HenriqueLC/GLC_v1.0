package widgets;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyEvent;

import java.util.LinkedList;

public class FilterComboBox extends ComboBox<String> {
    private FilterComboBox filterComboBox;

    {
        filterComboBox = this;
        filterComboBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String pressedKey = event.getText().toLowerCase();
                // check if it is alphanumerics
                if (!pressedKey.matches("^\\W*$")) {
                    // get the selected value, if any
                    String selected = filterComboBox.getValue();
                    ObservableList<String> items;
                    if (selected != null) {
                        LinkedList<String> tmp = new LinkedList<>();
                        // list of items, stating with the next one
                        boolean hasPassedOver = false;
                        for (String item : filterComboBox.getItems()) {
                            if (hasPassedOver) {
                                tmp.addLast(item);
                            }
                            else {
                                tmp.addFirst(item);
                            }
                            hasPassedOver = item.equals(selected);
                        }
                        items = FXCollections.observableArrayList(tmp);
                    }
                    else {
                        items = filterComboBox.getItems();
                    }

                    for (String item : items) {
                        if (item.toLowerCase().startsWith(pressedKey)) {
                            filterComboBox.setValue(item);
                        }
                    }
                }
            }
        });
    }
}
