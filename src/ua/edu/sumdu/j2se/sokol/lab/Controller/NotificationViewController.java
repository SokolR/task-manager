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

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Выводит ближайщую задачу в диалоговое окно
     * @param tasks - список задач
     */
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

    /**
     * Закрывает окно, по нажатию на кнопку ОК
     */
    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) msgLabel.getScene().getWindow();
        okClicked = true;
        stage.close();
    }
}
