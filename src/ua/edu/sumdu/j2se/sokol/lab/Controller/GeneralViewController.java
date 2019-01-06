package ua.edu.sumdu.j2se.sokol.lab.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ua.edu.sumdu.j2se.sokol.lab.MainApp;
import ua.edu.sumdu.j2se.sokol.lab.Model.Task;
import ua.edu.sumdu.j2se.sokol.lab.Util.DateUtil;

import java.time.LocalDate;
import java.util.Date;

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
    private MainApp mainApp;

    public GeneralViewController() {
    }
    
    @FXML
    private void initialize() {
        calendarTaskStartDatePiker.setValue(LocalDate.now());
        calendarTaskEndDatePiker.setValue(calendarTaskStartDatePiker.getValue().plusDays(7));

        titleColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("title"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("StartTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("EndTime"));

        tasksTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showTaskDetails(newValue));
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        tasksTable.setItems(mainApp.getTasksData());
    }

    private void showTaskDetails(Task task) {
        if (task != null) {
            titleLabel.setText(task.getTitle());
            startTimeLabel.setText(DateUtil.format(task.getStartTime()));
            repeatLabel.setText(DateUtil.secondsToStringTime(task.getRepeatInterval()));

            if (task.getRepeatInterval() != 0) {
                endTimeLabel.setText(DateUtil.format(task.getEndTime()));
            } else {
                endTimeLabel.setText("");
            }

            activityLabel.setText(Boolean.toString(task.isActive()));
        } else {
            titleLabel.setText("empty");
            startTimeLabel.setText("empty");
            endTimeLabel.setText("empty");
            repeatLabel.setText("empty");
            activityLabel.setText("empty");
        }
    }

    public void calendarHandler(ActionEvent actionEvent) {
        mainApp.showCalendarWindow();
    }

    public void newTaskHandler(ActionEvent actionEvent) {
        Task tempTask = new Task("default", new Date());
        boolean okClicked = mainApp.showCreateAndEditWindow(tempTask, true);
        if (okClicked) {
            mainApp.getTasksData().add(tempTask);
            MainApp.getTask().add(tempTask);
            showTaskDetails(tempTask);
        }
    }

    public void editTaskHandler(ActionEvent actionEvent) {
    }

    public void deleteTaskHandler(ActionEvent actionEvent) {
        int selectIndex = tasksTable.getSelectionModel().getSelectedIndex();
        tasksTable.getItems().remove(selectIndex);
    }

}

