package ua.edu.sumdu.j2se.sokol.lab.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ua.edu.sumdu.j2se.sokol.lab.Model.Task;

import java.time.LocalDate;
import java.util.Locale;

public class GeneralViewController {
    @FXML
    public TableView<Task> tasksTable;
    @FXML
    public TableColumn<Task, String> startTimeColumn;
    @FXML
    public TableColumn<Task, String> endTimeColumn;
    @FXML
    public TableColumn<Task, String> titleColumn;

    @FXML
    public Label titleTask;
    @FXML
    public Label titleLabel;
    @FXML
    public Label startTimeLabel;
    @FXML
    public Label endTimeLabel;
    @FXML
    public Label repeatLabel;
    @FXML
    public Label activityLabel;

    @FXML
    public DatePicker calendarTaskStartDatePiker;
    @FXML
    public DatePicker calendarTaskEndDatePiker;
    @FXML
    public Button showBtn;

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

