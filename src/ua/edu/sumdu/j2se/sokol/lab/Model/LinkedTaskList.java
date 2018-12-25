package ua.edu.sumdu.j2se.sokol.lab.Model;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class which help to work with several tasks.
 * @author Ruslan Sokol
*/
public class LinkedTaskList extends TaskList {
    private int counter;
    private Entry header;

    /**
    *
    */
    public LinkedTaskList() {
        this.header = new Entry(null, null, null);
    }

    /**
    *@param task - task
    */
    public void add(Task task) {
        if (task == null) {
            throw new NullPointerException("Task can't be null");
        }
        Entry newTask = new Entry(task, header, header.prev);

        newTask.prev.next = newTask;
        newTask.next.prev = newTask;
        counter++;
    }

    /**
    *@param task - task
    *@return true or false
    */
    public boolean remove(Task task) {
        Entry currentEntry = header.next;
        while (currentEntry.task != null && !currentEntry.task.equals(task)) {
            currentEntry = currentEntry.next;
        }
        if (currentEntry.task != null) {
            currentEntry.prev.next = currentEntry.next;
            currentEntry.next.prev = currentEntry.prev;
            currentEntry.next = null;
            currentEntry.prev = null;
            currentEntry.task = null;
            counter--;
            return true;
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
    *@return tasks
    */
    public Task getTask(int index) {
        Entry currentEntry = header.next;
        int currentIndex = 0;

        while (currentEntry != header) {
            if (currentIndex == index) {
                return currentEntry.task;
            }
            currentEntry = currentEntry.next;
            currentIndex++;
        }
        return null;
    }

    /**
     * @return iterator
     */
    public Iterator<Task> iterator() {
        return new LinkedIterator();
    }

    private class LinkedIterator implements Iterator<Task> {
        private Entry current;
        boolean canRemove = false;

        LinkedIterator() {
            current = header.next;
        }

        public boolean hasNext() {
            return current != header;
        }

        public Task next() {
            if (!hasNext())
                throw new NoSuchElementException();
            canRemove = true;
            current = current.next;
            return current.prev.task;
        }

        public void remove() {
            if (!canRemove)
                throw new IllegalStateException();
            current = current.prev;
            current.next.prev = current.prev;
            current.prev.next = current.next;
            current = current.next;
            counter--;
            canRemove = false;
        }
    }

    @Override
    public boolean equals(Object object) {
       if (object == null || object.getClass() != this.getClass()) {
           return false;
       }

       TaskList list = (TaskList) object;
       if (list.size() != this.size()) {
           return false;
       }
       
       if (this.hashCode() != list.hashCode()) {
           return false;
       }
       for (int i = 0; i < size(); i++) {
           if (!this.getTask(i).equals(list.getTask(i))) {
               return false;
           }
       }
       return true;
    }

    /**
     * @return clone
     */

    @Override
    public LinkedTaskList clone() {
        try {
            LinkedTaskList clone = (LinkedTaskList) super.clone();
            clone.header = this.header.clone();
            clone.counter = 0;
            for (int i = 0; i < size(); i++) {     
                clone.add((Task) getTask(i).clone());
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            System.out.println("Tish object can't be cloned");
            return null;
        }
    }

    private static class Entry implements Cloneable {
        private Task task;
        private Entry next;
        private Entry prev;

        Entry(Task task, Entry next, Entry prev) {
            this.task = task;
            this.next = next;
            this.prev = prev;

            if (next == null) {
                this.next = this;
            }
            if (prev == null) {
                this.prev = this;
            }
        }

        /**
         * @return new clone of task
         */
        public Entry clone() {
            try {       
                Entry clone = (Entry) super.clone();
                clone.task = null;
                clone.next = clone;
                clone.prev = clone;
                return clone;
            } catch (CloneNotSupportedException e) {
                throw new IllegalArgumentException();
            }
        } 
    }
}


















