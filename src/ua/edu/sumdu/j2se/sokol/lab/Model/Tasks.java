package ua.edu.sumdu.j2se.sokol.lab.Model;

import java.util.*;

public class Tasks {
    public static Iterable<Task> incoming(Iterable<Task> tasks, Date start, Date end) {

        Set<Task> incom = new LinkedHashSet<Task>();
        Iterator it = tasks.iterator();
        while (it.hasNext()) {
            Task bfr = (Task)it.next();

            if (bfr.nextTimeAfter(start) != null && bfr.nextTimeAfter(start).compareTo(end) <= 0) {
                incom.add(bfr);
            }
        }
        return incom;
    }

    public static SortedMap<Date, Set<Task>> calendar(Iterable<Task> tasks, Date start, Date end) {
        TreeMap<Date, Set<Task>> map = new TreeMap<Date, Set<Task>>();
        Iterable<Task> it = incoming(tasks, start, end);
        for (Task task : it) {
            Date date = task.nextTimeAfter(start);
            while(date != null && date.compareTo(end) <= 0) {
                if (map.containsKey(date)) {
                    map.get(date).add(task);
                } else {
                    Set<Task> tmp = new HashSet<Task>();
                    tmp.add(task);
                    map.put(date, tmp);
                }
                date = task.nextTimeAfter(date);
            }
        }
        return map;
    }
}