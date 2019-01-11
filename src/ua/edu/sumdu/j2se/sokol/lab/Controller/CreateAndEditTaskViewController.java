package ua.edu.sumdu.j2se.sokol.lab.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ua.edu.sumdu.j2se.sokol.lab.Model.Task;
import ua.edu.sumdu.j2se.sokol.lab.Util.DateUtil;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class CreateAndEditTaskViewController {
    @FXML
    public Button btnNewTaskOk;
    @FXML
    public TextField textFieldTitle;
    @FXML
    public DatePicker datePikedDateStart;
    @FXML
    public Spinner<Integer> choiceBoxHoursStart;
    @FXML
    public Spinner<Integer> choiceBoxMinutesStart;
    @FXML
    public Spinner<Integer> choiceBoxMinutesEnd;
    @FXML
    public Spinner<Integer> choiceBoxHoursEnd;
    @FXML
    public DatePicker datePikedDateEnd;
    @FXML
    public CheckBox chkBoxActive;
    @FXML
    public TextField repeatTime;

    private Stage dialogStage;
    private Task task;
    private boolean okClicked = false;


    final int initValue = 00;

    SpinnerValueFactory<Integer> startHours = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, initValue);
    SpinnerValueFactory<Integer> startMinutes = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, initValue);
    SpinnerValueFactory<Integer> endHours = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, initValue);
    SpinnerValueFactory<Integer> endMinutes = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, initValue);

    public Task getTask() {
        return task;
    }

    /**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
        Locale.setDefault(Locale.ENGLISH);

        choiceBoxHoursStart.setValueFactory(startHours);
        choiceBoxHoursStart.setEditable(true);
        choiceBoxMinutesStart.setValueFactory(startMinutes);
        choiceBoxMinutesStart.setEditable(true);

        choiceBoxHoursEnd.setValueFactory(endHours);
        choiceBoxHoursEnd.setEditable(true);
        choiceBoxMinutesEnd.setValueFactory(endMinutes);
        choiceBoxMinutesEnd.setEditable(true);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Задает новую задачу
     *
     * @param task - задача
     */
    public void setNewTask(Task task) {
        this.task = task;

        textFieldTitle.setText("");

        chkBoxActive.setSelected(false);

        datePikedDateStart.setValue(task.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        choiceBoxHoursStart.setValueFactory(startHours);
        choiceBoxMinutesStart.setValueFactory(startMinutes);

        datePikedDateEnd.setValue(null);
        choiceBoxHoursEnd.setValueFactory(endHours);
        choiceBoxMinutesEnd.setValueFactory(endMinutes);

        repeatTime.setText("");
    }

    /**
     * Задает текующую задачу, для редактирования
     *
     * @param task - задача
     */
    public void setTask(Task task) {
        this.task = task;

        textFieldTitle.setText(task.getTitle());

        chkBoxActive.setSelected(task.isActive());

        datePikedDateStart.setValue(task.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        choiceBoxHoursStart.setValueFactory(startHours);
        choiceBoxMinutesStart.setValueFactory(startMinutes);

        if (task.isRepeated()) {
            datePikedDateEnd.setValue(task.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            choiceBoxHoursEnd.setValueFactory(endHours);
            choiceBoxMinutesEnd.setValueFactory(endMinutes);
            repeatTime.setText(DateUtil.secondsToStringTime(task.getRepeatInterval()));
        } else {
            choiceBoxHoursEnd.setValueFactory(endHours);;
            choiceBoxMinutesEnd.setValueFactory(endMinutes);;
        }
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке OK.
     */
    public void handleOk(ActionEvent actionEvent) throws ParseException {
        if (isInputValid()) {
            int repeatInterval;
            task.setTitle(textFieldTitle.getText());
            task.setActive(chkBoxActive.isSelected());

            LocalDate startLocalDate = datePikedDateStart.getValue();
            Instant startInstant = Instant.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()));

            Long startDay = Date.from(startInstant).getTime();
            Integer startHour = choiceBoxHoursStart.getValue() * 60 * 60 * 1000;
            Integer startMinute = choiceBoxMinutesStart.getValue() * 60 * 1000;

            Date startDate = new Date(startDay + startHour + startMinute);

            repeatInterval = DateUtil.parseInterval(repeatTime.getText());
            if (repeatInterval > 0) {
                LocalDate endLocalDate = datePikedDateEnd.getValue();
                Instant endInstant = Instant.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()));

                Long endDay = Date.from(endInstant).getTime();
                Integer endHour = choiceBoxHoursEnd.getValue()* 60 * 60 * 1000;
                Integer endMinute = choiceBoxMinutesEnd.getValue() * 60 * 1000;

                Date endDate = new Date(endDay + endHour + endMinute);

                task.setTime(startDate, endDate, repeatInterval);
            } else {
                task.setTime(startDate);
            }

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке Cancel.
     */
    public void handleCancel(ActionEvent actionEvent) {
        dialogStage.close();
    }

    /**
     * Проверяет пользовательский ввод
     *
     * @return true, если пользовательский ввод корректен
     */
    private boolean isInputValid() {
        String errorMsg = "";
        int rTime = 0;
        LocalDate start = null;

        try {
            if (textFieldTitle.getText() == null || textFieldTitle.getText().isEmpty() || textFieldTitle.getText().matches("\\s+")) {
                throw new IllegalArgumentException("No valid title");
            }
            if (datePikedDateStart.getValue() == null || datePikedDateStart.getValue().isBefore(DateUtil.dateToLaocalDate(new Date(0)))) {
                throw new IllegalArgumentException("Incorrect start date!");
            } else {
                start = datePikedDateStart.getValue();
            }try {
                rTime = DateUtil.parseInterval(repeatTime.getText());

            } catch (ParseException e) {
                throw new IllegalArgumentException("Incorrect repeat time!");
            } catch (IllegalArgumentException a) {
                throw new IllegalArgumentException(a.getMessage());
            }
            if (rTime > 0) {
                if (datePikedDateEnd.getValue() == null || datePikedDateEnd
                        .getValue().isBefore(DateUtil.dateToLaocalDate(new Date(0)))) {

                    throw new IllegalArgumentException("Incorrect end date!");
                }
                if (datePikedDateEnd.getValue().isBefore(start)) {
                    throw new IllegalArgumentException("Incorrect end date!");
                }
            }

            return true;
        } catch (IllegalArgumentException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Invalid Fields");
                alert.setHeaderText("Please correct invalid fields");
                alert.setContentText(e.getMessage());

                alert.showAndWait();

                return false;
            }
        }
}

