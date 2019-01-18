package ua.edu.sumdu.j2se.sokol.lab;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.sumdu.j2se.sokol.lab.Controller.CalendarViewController;
import ua.edu.sumdu.j2se.sokol.lab.Controller.CreateAndEditTaskViewController;
import ua.edu.sumdu.j2se.sokol.lab.Controller.GeneralViewController;
import ua.edu.sumdu.j2se.sokol.lab.Model.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class MainApp extends Application {
    private final Logger log = LogManager.getLogger(MainApp.class.getSimpleName());
    final Thread currentThread = Thread.currentThread();
    private static TaskList tasks = new LinkedTaskList();
    private ObservableList<Task> tasksData = FXCollections.observableArrayList();
    public static final File DATABASE = new File("src/resources/database");
    private boolean exit = false;


    public ObservableList<Task> getTasksData() {
        return tasksData;
    }

    public static TaskList getTask() {
        return tasks;
    }

    private Stage primaryStage;
    private VBox rootLayout;



    /**
     * Старт приложения.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        final Stage finalPrimaryStageCopy = primaryStage;

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Task Manager");
        this.primaryStage.setMinWidth(750);
        this.primaryStage.setMinHeight(500);

        log.info("Program Open");

        initRootLayout();

        Notificator.setNode(primaryStage.getOwner());

        final Thread notify = new Thread(new Notificator(finalPrimaryStageCopy));

        final Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(12000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (primaryStage.isShowing()) {
                    Platform.runLater(notify);
                    try {
                        Thread.sleep(120000);
                    } catch (InterruptedException e) {
                        e.getStackTrace();
                    }
                    if (currentThread.isInterrupted()) {
                        notify.interrupt();
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();

        this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                writeInDataBase();
                exit = true;
                log.info("Program Close");
            }
        });
    }

    /**
     * Метод для проверки закрытия программы
     * @return exit - true, если программа закрвается
     */
    public boolean isExit () {
        return exit;
    }

    /**
     * Конструктор по умолчанию
     */
    public MainApp() {
        try {
            TaskIO.readBinary(tasks, DATABASE);
            tasksData.clear();
            for (Task t : tasks) {
                tasksData.add(t);
            }
        } catch (IOException e) {
            log.catching(e);
            log.info("Can't read task from null, need create file");
        }
    }

    /**
     * Инициализирует корневой макет.
     */
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("View/GeneralView.fxml"));
            rootLayout = loader.load();

            GeneralViewController controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            log.catching(e);
            log.info("Error when loading root layout at initRootLayout()");
        }
    }

    /**
     * Открывает диалоговое окно для изменения деталей задачи.
     *
     * @param task - объект адресата, который надо изменить
     * @return true, если пользователь кликнул OK, в противном случае false.
     */
    public boolean showCreateAndEditWindow(Task task, boolean newTask) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("View/CreateAndEditTaskView.fxml"));

            GridPane page = loader.load();

            Stage dialogStage = new Stage();
            if (newTask) {
                dialogStage.setTitle("Create Task");
            } else {
                dialogStage.setTitle("Edit Task");
            }

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            CreateAndEditTaskViewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            if (newTask) {
                controller.setNewTask(task);
            } else {
                controller.setTask(task);
            }

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            log.catching(e);
            log.info("Error when loading create and edit layout at showCreateAndEditWindow()");
            return false;
        }
    }

    /**
     * Открывает диалоговое окно для просмотра календаря.
     *
     * @param startTime - начальная дата календаря
     * @param endTime - конечная дата календаря
     */
    public void showCalendarWindow(Date startTime, Date endTime) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("View/CalendarView.fxml"));

            HBox page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Calendar");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            CalendarViewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setDates(startTime, endTime);

            dialogStage.showAndWait();
        } catch (IOException e) {
            log.catching(e);
            log.info("Error when loading calendar layout at showCalendarWindow()");
        }
    }

    /**
     * Главное окно приложения
     * @return - главное окно приложения
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Запись задач в файл.
     */
    private void writeInDataBase() {
        try {
            TaskIO.writeBinary(tasks, DATABASE);
        } catch (IOException e) {
            log.catching(e);
            log.info("Can't write task to null, need create file");
        }
    }

    /**
     * Метод который запускает приложение
     */
    public static void main(String[] args) {
        launch(args);
    }
}
