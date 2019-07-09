package tomek.szypula.view;

import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import tomek.szypula.models.Model;

public class View {
    Model model;
    ModelUI modelUI;
    ControlPanelUI controlPanelUI;
    SplitPane splitPane;


    public View(Model model, SplitPane splitPane) {
        this.model = model;
        this.splitPane = splitPane;
        modelUI = new ModelUI(model);
        controlPanelUI = new ControlPanelUI(model);

        splitPane.getItems().add(modelUI.getModelPane());
        splitPane.getItems().add(controlPanelUI.getControlPane());
    }
}
