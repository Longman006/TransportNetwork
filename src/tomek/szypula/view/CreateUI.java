package tomek.szypula.view;

import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public interface CreateUI {
    public void createUI(Group parent);

    public Paint getfill();
    public ObjectProperty<Paint> getfillProperty();
}
