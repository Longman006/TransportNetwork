package tomek.szypula.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public interface BooleanPropertyInterface {
    BooleanProperty booleanProperty = new SimpleBooleanProperty();
    //TODO Use this for the outOfService and OnRamp for all future boolean attributes
}
