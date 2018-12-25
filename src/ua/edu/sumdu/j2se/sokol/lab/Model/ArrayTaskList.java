package ua.edu.sumdu.j2se.sokol.lab.Model;


import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class which help to work with several tasks.
 * @author Ruslan Sokol
*/
public class ArrayTaskList extends TaskList {
    private static final int START_LENGTH = 10;
    private int counter;
    private Task[] tasks = new Task[START_LENGTH];

    /**
    *@param task - task
    */
    public void add(Task task) {
        if (task == null) {
            throw new NullPointerException("Task can't be null");
        }
        if (counter == tasks.length - 1) {
            Task[] tempTask = new Task[tasks.length * 2];
            System.arraycopy(tasks, 0, tempTask, 0, tasks.length);
            Arrays.fill(tasks, null);
            tasks = new Task[tempTask.length];
            System.arraycopy(tempTask, 0, tasks, 0, tempTask.length);
            tasks[counter] = task;
        } else {
            tasks[counter] = task;
        }
        counter++;
    }

    /**
    *@param task - task
    *@return true or false
    */
    public boolean remove(Task task) {
        for (int i = 0; i < size(); i++) {
            if (tasks[i].equals(task)) {
                System.arraycopy(tasks, i + 1, tasks, i, size() - i - 1);
                counter--;
                return true;
            }
        }
        return false;
    }

    /**
    *@return counter
    */
    public int size() {
        return counter;
    }

    /**
    *@param index - index
    *@return tasks[index]
    */
    public Task getTask(int index) {
        if (index < tasks.length) {
            return tasks[index];
        }
        return null;
    }

    /**
     * @return new iterator
     */
    public Iterator<Task> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Task> {
        private int index = 0;
        private boolean canRemove = false;

        public boolean hasNext() {
            if (index < size()) {
                return true;
            }
            return false;
        }

        public Task next() {
            if (!hasNext())
                throw new NoSuchElementException();
            canRemove = true;
            return getTask(index++);

        }

        public void remove() {
            if (!canRemove)
                throw new IllegalStateException();
            for (int i = index - 1; i < size(); i++)
                tasks[i] = tasks[i + 1];
            counter--;
            index--;
            canRemove = false;
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || object.getClass() != this.getClass())
            return false;

        TaskList list = (TaskList) object;
        if (hashCode() != list.hashCode())
            return false;
        for (int i = 0; i < size(); i++) {
            if (!this.getTask(i).equals(list.getTask(i)))
                return false;
        }
        return true;
    }

    /**
     * @return clone
     */

    @Override
    public ArrayTaskList clone() {
        try {
            ArrayTaskList clone = (ArrayTaskList) super.clone();
            clone.tasks = this.tasks.clone();
            for (int i = 0; i < size(); i++)
                clone.tasks[i] = (Task) this.tasks[i].clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new IllegalArgumentException();
        }
    }
}



















