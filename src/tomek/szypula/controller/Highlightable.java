package tomek.szypula.controller;

import javafx.beans.property.SimpleBooleanProperty;
import tomek.szypula.models.Car;

public interface Highlightable {
    void highlight();
    SimpleBooleanProperty isChangeProperty();
    Object getHighlighted();
}
