package tomek.szypula.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import tomek.szypula.controller.UniqueId;

import java.util.UUID;

public class OnRamp implements UniqueId {
    private BooleanProperty onRamp = new SimpleBooleanProperty();
    private UUID uuid = UUID.randomUUID();

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

    @Override
    public String getId() {
        return uuid.toString();
    }
}
