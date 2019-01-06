package ua.edu.sumdu.j2se.sokol.lab.Controller;

import javafx.collections.FXCollections;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CreateAndEditTaskViewController {
    @FXML
    public Button btnNewTaskOk;
    @FXML
    public TextField textFieldTitle;
    @FXML
    public DatePicker datePikedDateStart;
    @FXML
    public ChoiceBox<String> choiceBoxHoursStart;
    @FXML
    public ChoiceBox<String> choiceBoxMinutesStart;
    @FXML
    public ChoiceBox<String> choiceBoxMinutesEnd;
    @FXML
    public ChoiceBox<String> choiceBoxHoursEnd;
    @FXML
    public DatePicker datePikedDateEnd;
    @FXML
    public CheckBox chkBoxActive;
    @FXML
    public TextField repeatTime;

    private Stage dialogStage;
    private Task task;
    private boolean okClicked = false;

    static final List<String > HOURS = new ArrayList<>();
    static final List<String> MINUTES = new ArrayList<>();

    public Task getTask() {
        return task;
    }

    @FXML
    private void initialize() {
        Locale.setDefault(Locale.ENGLISH);
        if (HOURS.isEmpty()) {
            for (int i = 0; i < 24; i++) {
                if (i < 10) {
                    HOURS.add("0" + i);
                } else {
                    HOURS.add("" + i);
                }
            }
        }

        if (MINUTES.isEmpty()) {
            for (int i = 0; i < 60; i++) {

                if (i < 10) {
                    MINUTES.add("0" + i);
                } else
                    MINUTES.add("" + i);
            }
        }

        choiceBoxHoursStart.setItems(FXCollections.observableArrayList(HOURS));
        choiceBoxMinutesStart.setItems(FXCollections.observableArrayList(MINUTES));

        choiceBoxHoursEnd.setItems(FXCollections.observableArrayList(HOURS));
        choiceBoxMinutesEnd.setItems(FXCollections.observableArrayList(MINUTES));
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setNewTask(Task task) {
        this.task = task;

        textFieldTitle.setText("");

        chkBoxActive.setSelected(false);

        datePikedDateStart.setValue(null);
        choiceBoxHoursStart.setValue("00");
        choiceBoxMinutesStart.setValue("00");

        datePikedDateEnd.setValue(null);
        choiceBoxHoursEnd.setValue("00");
        choiceBoxMinutesEnd.setValue("00");

        repeatTime.setText("");
    }

    public void setTask(Task task) {
        this.task = task;

        textFieldTitle.setText(task.getTitle());

        chkBoxActive.setSelected(task.isActive());

        datePikedDateStart.setValue(task.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        choiceBoxHoursStart.setValue(DateUtil.choiceBoxTime(task.getStartTime().getHours()));
        choiceBoxMinutesStart.setValue(DateUtil.choiceBoxTime(task.getStartTime().getMinutes()));

        if (task.isRepeated()) {
            datePikedDateEnd.setValue(task.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            choiceBoxHoursEnd.setValue(DateUtil.choiceBoxTime(task.getEndTime().getHours()));
            choiceBoxMinutesEnd.setValue(DateUtil.choiceBoxTime(task.getEndTime().getMinutes()));
        } else {
            choiceBoxHoursEnd.setValue("00");
            choiceBoxMinutesEnd.setValue("00");
        }
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void handleOk(ActionEvent actionEvent) throws ParseException {
        if (isInputValid()) {
            int repeatInterval;
            task.setTitle(textFieldTitle.getText());
            task.setActive(chkBoxActive.isSelected());

            LocalDate startLocalDate = datePikedDateStart.getValue();
            Instant startInstant = Instant.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()));

            Long startDay = Date.from(startInstant).getTime();
            Integer startHour = Integer.parseInt(choiceBoxHoursStart.getValue()) * 60 * 60 * 1000;
            Integer startMinute = Integer.parseInt(choiceBoxMinutesStart.getValue()) * 60 * 1000;

            Date startDate = new Date(startDay + startHour + startMinute);

            repeatInterval = DateUtil.parseInterval(repeatTime.getText());
            if (repeatInterval > 0) {
                LocalDate endLocalDate = datePikedDateEnd.getValue();
                Instant endInstant = Instant.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()));

                Long endDay = Date.from(endInstant).getTime();
                Integer endHour = Integer.parseInt(choiceBoxHoursEnd.getValue()) * 60 * 60 * 1000;
                Integer endMinute = Integer.parseInt(choiceBoxMinutesEnd.getValue()) * 60 * 1000;

                Date endDate = new Date(endDay + endHour + endMinute);
            }

            okClicked = true;
            dialogStage.close();
        }
    }

    public void handleCancel(ActionEvent actionEvent) {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMsg = "";
        int rTime = 0;
        LocalDate start = null;

        try {
            if (textFieldTitle.getText() == null || textFieldTitle.getText().isEmpty() || textFieldTitle.getText().matches("\\s+")) {
                throw new IllegalArgumentException("No valid title");
            }
            if (datePikedDateStart.getValue() == null || datePikedDateStart.getValue().isBefore(DateUtil.dateToLaocalDate(new Date(0)))
                    || datePikedDateStart.getValue().isAfter(datePikedDateEnd.getValue())) {
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

