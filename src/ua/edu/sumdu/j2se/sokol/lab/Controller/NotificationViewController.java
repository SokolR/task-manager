package ua.edu.sumdu.j2se.sokol.lab.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class NotificationViewController {
    @FXML
    public Button msgBtn;
    @FXML
    public Label msgLabel;

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
