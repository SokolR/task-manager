package ua.edu.sumdu.j2se.sokol.lab.Model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.sumdu.j2se.sokol.lab.Controller.NotificationViewController;
import ua.edu.sumdu.j2se.sokol.lab.MainApp;


public class Notificator implements Runnable {
    private static final Logger log = LogManager.getLogger(Notificator.class);
    private static Window node;
    private Stage stages;


    public Notificator(Stage stages) {
        this.stages = stages;
    }

    public static void setNode(Window node) {
        Notificator.node = node;
    }

    public static Window getNode() {
        return node;
    }

    /**
     * Вызов окна с сообщение о ближайшей задаче
     */
    public void run() {
        if (stages.isShowing()) {
                try {
                    showNotification();
                } catch (Exception e) {
                    log.error("Ошибка в добавление нового элемента" + e.getMessage());
                }
            }
        }

    /**
     * Показывает диалоговое окно с сообщениями о ближайшей задаче.
     */
    public void showNotification() {
        try {
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(Notificator.class.getResource("../View/NotificationView.fxml"));

            Parent root = (Parent) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Notification");
            dialogStage.initOwner(Notificator.getNode());
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            NotificationViewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.nearestTasks(MainApp.getTask());

            dialogStage.showAndWait();
        } catch (Exception e) {
            log.catching(e);
            log.info("Error when loading calendar layout at showNotification()");
        }
    }

}
