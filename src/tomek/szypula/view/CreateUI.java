package tomek.szypula.view;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public interface CreateUI {
    public void createUI(Group parent);

    void setColor(Color color);
}
