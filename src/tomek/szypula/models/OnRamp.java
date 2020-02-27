package tomek.szypula.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class OnRamp {
    private BooleanProperty onRamp = new SimpleBooleanProperty();

    public boolean isOnRamp() {
        return onRamp.get();
    }

    public BooleanProperty onRampProperty() {
        return onRamp;
    }

    public void setOnRamp(boolean onRamp) {
        this.onRamp.set(onRamp);
    }

    public void switchValue(){
        this.onRamp.set(!this.onRamp.get());
    }
}
