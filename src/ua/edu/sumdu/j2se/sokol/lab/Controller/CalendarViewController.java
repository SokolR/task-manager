package ua.edu.sumdu.j2se.sokol.lab.Controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;
import ua.edu.sumdu.j2se.sokol.lab.MainApp;
import ua.edu.sumdu.j2se.sokol.lab.Model.Task;
import ua.edu.sumdu.j2se.sokol.lab.Model.Tasks;

import java.util.*;

public class CalendarViewController {

    @FXML
    private TableView<Map.Entry<Date, Set<Task>>> calendarTable;
    @FXML
    private TableColumn<Map.Entry<Date, Set<Task>>, String> dateColumn;
    @FXML
    private TableColumn<Map.Entry<Date, Set<Task>>, String> taskTitleColumn;

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public  void setDates(Date startTime, Date endTime) {
        SortedMap<Date, Set<Task>> tasks = Tasks.calendar(MainApp.getTask(), startTime, endTime);

        dateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Date, Set<Task>>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Date, Set<Task>>, String> param) {
                return new SimpleStringProperty(param.getValue().getKey().toString());
            }
        });

        taskTitleColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Date, Set<Task>>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Date, Set<Task>>, String> param) {
                Iterator a = param.getValue().getValue().iterator();
                String res = "";
                while (a.hasNext()) {
                    res += ((Task) a.next()).getTitle() + "\n";
                }
                return new SimpleStringProperty(res);
            }
        });

        ObservableList<Map.Entry<Date, Set<Task>>> items = FXCollections.observableArrayList(tasks.entrySet());
        calendarTable.setItems(items);
        calendarTable.getColumns().setAll(dateColumn, taskTitleColumn);
    }
}
