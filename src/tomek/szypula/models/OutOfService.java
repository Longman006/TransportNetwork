package tomek.szypula.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class OutOfService {
    private BooleanProperty outOfService = new SimpleBooleanProperty();

    public OutOfService() {
        setOutOfService(false);
    }
    public void switchOutOfService(){
        setOutOfService(!isOutOfService());
    }

    public boolean isOutOfService() {
        return outOfService.get();
    }

    public BooleanProperty outOfServiceProperty() {
        return outOfService;
    }

    public void setOutOfService(boolean outOfService) {
        this.outOfService.set(outOfService);
    }
}
