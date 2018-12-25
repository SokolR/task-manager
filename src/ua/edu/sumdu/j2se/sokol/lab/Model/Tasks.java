package ua.edu.sumdu.j2se.sokol.lab.Model;

import java.util.*;

public class Tasks {
    private static final int NUMBER = 1000;

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

        SortedMap<Date, Set<Task>> map = new TreeMap<Date, Set<Task>>();
        Iterator it = tasks.iterator();
        while (it.hasNext()) {
            Task bfr = (Task)it.next();
            for (Date date = bfr.getStartTime(); date.compareTo(bfr.getEndTime()) <= 0;) {
                if (date.after(start) && date.compareTo(end) <= 0) {
                    if (map.get(date) != null) {
                        Set<Task> copy = map.get(date);
                        copy.add(bfr);
                        map.put(date, copy);
                    }
                    else {
                        Set<Task> tmp = new LinkedHashSet<Task>();
                        tmp.add(bfr);
                        map.put(date, tmp);
                    }
                }
                date = new Date(date.getTime() + bfr.getRepeatInterval() * NUMBER);
            }
        }
        return map;
    }
}