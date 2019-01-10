package ua.edu.sumdu.j2se.sokol.lab.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ua.edu.sumdu.j2se.sokol.lab.Model.Task;
import ua.edu.sumdu.j2se.sokol.lab.Model.TaskList;
import ua.edu.sumdu.j2se.sokol.lab.Util.DateUtil;

import java.util.Date;

public class NotificationViewController {
    public static boolean okClicked = false;
    @FXML
    private Button msgBtn;
    @FXML
    private Label msgLabel;

    public static boolean okIsClicked() {
        return okClicked;
    }
    public void nearestTasks(TaskList tasks) {
        Date currentTime = new Date();
        if (tasks.size() != 0) {
            for (Task t : tasks) {
                msgLabel.setText("Nearest Task: " + "'" + t.getTitle() + "'" + " at: " + DateUtil.format(t.nextTimeAfter(currentTime)));
            }
        } else {
            msgLabel.setText("No nearest tasks!");
        }
    }

    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) msgLabel.getScene().getWindow();
        okClicked = true;
        stage.close();
    }
}
