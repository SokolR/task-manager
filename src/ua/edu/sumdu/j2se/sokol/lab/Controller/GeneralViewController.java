package ua.edu.sumdu.j2se.sokol.lab.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ua.edu.sumdu.j2se.sokol.lab.Model.Task;

public class GeneralViewController {
    @FXML
    private TableView<Task> tasksTable;
    @FXML
    private TableColumn<Task, String> startTimeColumn;
    @FXML
    private TableColumn<Task, String> endTimeColumn;
    @FXML
    private TableColumn<Task, String> titleColumn;

    @FXML
    private Label titleTask;
    @FXML
    private Label titleLabel;
    @FXML
    private Label startTimeLabel;
    @FXML
    private Label endTimeLabel;
    @FXML
    private Label repeatLabel;
    @FXML
    private Label activityLabel;

    @FXML
    DatePicker calendarTaskStartDatePiker;
    @FXML
    DatePicker calendarTaskEndDatePiker;
    @FXML
    Button showBtn;

    @FXML
    private void initialize() {
    }

    public void calendarHandler(ActionEvent actionEvent) {
    }

    public void newTaskHandler(ActionEvent actionEvent) {
    }

    public void editTaskHandler(ActionEvent actionEvent) {
    }

    public void deleteTaskHandler(ActionEvent actionEvent) {
    }
}

