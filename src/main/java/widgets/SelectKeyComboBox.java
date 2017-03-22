package widgets;

import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;

import java.util.LinkedList;

public class SelectKeyComboBox extends ComboBox<String> {
    private SelectKeyComboBox selectKeyComboBox;

    {
        selectKeyComboBox = this;
        selectKeyComboBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String pressedKey = event.getText().toLowerCase();
                // check if it is alphanumerics
                if (!pressedKey.matches("^\\W*$")) {
                    // get the selected value, if any
                    String selected = selectKeyComboBox.getValue();
                    ObservableList<String> items;
                    if (selected != null) {
                        LinkedList<String> tmp = new LinkedList<>();
                        // list of items, stating with the next one
                        boolean hasPassedOver = false;
                        for (String item : selectKeyComboBox.getItems()) {
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
                        items = selectKeyComboBox.getItems();
                    }

                    for (String item : items) {
                        if (item.toLowerCase().startsWith(pressedKey)) {
                            selectKeyComboBox.setValue(item);
                            ListView listView = ((ComboBoxListViewSkin) selectKeyComboBox.getSkin()).getListView();
                            listView.scrollTo(listView.getSelectionModel().getSelectedIndex());
                        }
                    }
                }
            }
        });
    }
}
