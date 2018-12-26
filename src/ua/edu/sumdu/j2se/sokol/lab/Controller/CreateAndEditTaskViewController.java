package ua.edu.sumdu.j2se.sokol.lab.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CreateAndEditTaskViewController {
    @FXML
    public Button btnNewTaskOk;
    @FXML
    public TextField textFieldTitle;
    @FXML
    public Button btnNewTaskCancel;
    @FXML
    public DatePicker datePikedDateStart;
    @FXML
    public ChoiceBox choiceBoxHoursStart;
    @FXML
    public ChoiceBox choiceBoxMinutesStart;
    @FXML
    public ChoiceBox choiceBoxMinutesEnd;
    @FXML
    public ChoiceBox choiceBoxHoursEnd;
    @FXML
    public DatePicker datePikedDateEnd;
    @FXML
    public CheckBox chkBoxActive;
    @FXML
    public TextField repeatTime;

    public void handleOk(ActionEvent actionEvent) {
    }

    public void handleCancel(ActionEvent actionEvent) {
    }
}
