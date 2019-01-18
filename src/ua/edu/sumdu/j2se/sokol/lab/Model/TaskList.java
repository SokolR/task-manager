package ua.edu.sumdu.j2se.sokol.lab.Model;

import java.io.Serializable;
import java.util.Iterator;

/**
 * Class
 * @author Ruslan Sokol
*/
public abstract class TaskList implements Iterable<Task>, Cloneable, Serializable {
    private static final  int NUMBER = 10;

    /**
    *@param task - task
    */
    public abstract void add(Task task);

    public abstract Iterator<Task> iterator();

    /**
    *@param task - task
    *@return true or false
    */
    public abstract boolean remove(Task task);

    /**
    *@return counter
    */
    public abstract int size();

    /**
    *@param index - index
    *@return tasks
    */
    public abstract Task getTask(int index);

    public int hashCode() {
        int code = NUMBER * size();
        for (int i = 0; i < size(); i++) {
            code += (i + 1) * getTask(i).hashCode();
        }
        return code;
    }

    public String toString() {
        StringBuilder str = new StringBuilder("List includes tasks:");
        for (Task task : this) {
            String a = "\n" + task.toString();
            str.append(a);
        }
        return str.toString();
    }
}