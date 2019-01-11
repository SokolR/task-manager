package ua.edu.sumdu.j2se.sokol.lab.Model;


import ua.edu.sumdu.j2se.sokol.lab.MainApp;
import ua.edu.sumdu.j2se.sokol.lab.Util.DateUtil;

import java.util.Date;


public class Notificator extends Thread {

    MainApp mainApp;
    private TaskList tasks;
    private long notifyPeriod; //period when find lab 10 minutes
    public static final int PAUSE = 120000;//5 minutes

    public Notificator(TaskList tasks, long notifyPeriod, MainApp mainApp) {
        this.tasks = tasks;
        this.notifyPeriod = notifyPeriod;
        this.mainApp = mainApp;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (!mainApp.isExit()) {

            Date currentTime = new Date();
            TaskList incomingTasks = (TaskList) Tasks.incoming(tasks, currentTime, new
                    Date(currentTime.getTime() + notifyPeriod));
            if (incomingTasks != null) {
                System.out.println("Nearest lab");
                for (Task t : incomingTasks) {
                    System.out.print("Time: " + DateUtil.format(t.nextTimeAfter(currentTime)));
                    System.out.println(" " + t.getTitle());
                }
            }
            try {
                Thread.sleep(PAUSE);
            } catch (InterruptedException e) {
                System.out.println("Goodbye");
            }
        }
    }
}

