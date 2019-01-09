package ua.edu.sumdu.j2se.sokol.lab.Model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import ua.edu.sumdu.j2se.sokol.lab.Controller.NotificationViewController;
import ua.edu.sumdu.j2se.sokol.lab.MainApp;

import java.io.IOException;
import java.util.Date;

public class Notificator implements Runnable {
    private static Window node;
    private Stage dialogStage;
    private MainApp mainApp;


    public Notificator(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public static void setNode(Window node) {
        Notificator.node = node;
    }

    public static Window getNode() {
        return node;
    }

    @Override
    public void run() {

        while (dialogStage.isShowing()) {
            Date currentTime = new Date();
            ArrayTaskList incomingTasks = (ArrayTaskList) Tasks.incoming(MainApp.getArrayTaskList(), new Date( ), new Date(currentTime.getTime()));
            if (incomingTasks != null) {
                showNotification(incomingTasks);
            }
        }
    }

    public void showNotification(TaskList tasks) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("View/NotificationView.fxml"));

            GridPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Notification");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(Notificator.getNode());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            NotificationViewController controller = loader.getController();
            controller.nearestTasks(tasks);

            if (!dialogStage.isShowing()) {
                dialogStage.showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
