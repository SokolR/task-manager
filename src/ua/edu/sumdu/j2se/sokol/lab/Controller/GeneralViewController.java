package ua.edu.sumdu.j2se.sokol.lab.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private final Logger log = LogManager.getLogger(GeneralViewController.class.getSimpleName());

    /**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
        calendarTaskStartDatePiker.setValue(LocalDate.now());
        calendarTaskEndDatePiker.setValue(calendarTaskStartDatePiker.getValue().plusDays(7));

        titleColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("title"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("StartTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("EndTime"));

        tasksTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showTaskDetails(newValue));
    }

    /**
     * Вызывается главным приложением, которое даёт на себя ссылку.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        tasksTable.setItems(mainApp.getTasksData());
    }

    /**
     * Заполняет все текстовые поля.
     * Если задача = null, то все текстовые поля очищаются.
     *
     * @param task — задача типа Task или null
     */
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

    /**
     * Заполняет календарь
     */
    public void calendarHandler(ActionEvent actionEvent) {
        Date startTime = DateUtil.localDateToDate(calendarTaskStartDatePiker.getValue().atTime(0, 0, 0));
        Date endTime = DateUtil.localDateToDate(calendarTaskEndDatePiker.getValue().atTime(23, 59, 59));
        if (startTime != null && endTime != null && endTime.compareTo(startTime) > 0) {
            mainApp.showCalendarWindow(startTime, endTime);
            log.info("Calendar show from " + startTime + " to " + endTime);
        } else {
            log.info("Wrong calendar data");
        }
    }

    /**
     * Добавление новой задачи
     */
    public void newTaskHandler(ActionEvent actionEvent) {
        Task tempTask = new Task("default", new Date());
        boolean okClicked = mainApp.showCreateAndEditWindow(tempTask, true);
        if (okClicked) {
            mainApp.getTasksData().add(tempTask);
            MainApp.getTask().add(tempTask);
            showTaskDetails(tempTask);
            log.info("Create task: " + tempTask.getTitle());
        }
    }

    /**
     * Редактирование выбранной задачи
     */
    public void editTaskHandler(ActionEvent actionEvent) {
        Task selectedTask = tasksTable.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            boolean okClicked = mainApp.showCreateAndEditWindow(selectedTask, false);
            if (okClicked) {
                tasksTable.getColumns().get(0).setVisible(false);
                tasksTable.getColumns().get(0).setVisible(true);
                showTaskDetails(selectedTask);
                log.info("Task " + selectedTask.getTitle().toString() + " was changed");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("ERROR");
            alert.setHeaderText("No task selected");
            alert.setContentText("Select task in the table");

            alert.showAndWait();
        }
    }

    /**
     * Удаления выбранной задачи
     */
    public void deleteTaskHandler(ActionEvent actionEvent) {
        int selectIndex = tasksTable.getSelectionModel().getSelectedIndex();
        if (selectIndex >= 0) {
            Task tempTask = tasksTable.getItems().get(selectIndex);
            MainApp.getTask().remove(tempTask);
            tasksTable.getItems().remove(selectIndex);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("ERROR");
            alert.setHeaderText("No task selected");
            alert.setContentText("Select task in the table!");

            alert.showAndWait();
        }
    }

}

