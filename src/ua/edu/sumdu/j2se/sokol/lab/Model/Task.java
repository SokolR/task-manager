package ua.edu.sumdu.j2se.sokol.lab.Model;

import java.io.Serializable;
import java.util.Date;

/** 
 * Class which contain information about the task.
 * @author Ruslan Sokol
*/
public class Task implements Cloneable, Serializable {
    private static final  int NUMBER = 31;
    private static final  int TIME = 1000;

    private String title;
    private Date time;
    private Date start;
    private Date end;
    private int interval;
    private boolean active;
    private boolean repeated;
    
    /**
    *Constructor for non-active and non-repeatable task
    *@param title - task name
    *@param time - task time
    */
    public Task(String title, Date time) {
        if (time == null) {
            throw new NullPointerException("Time can't be null: "
            + time);
        }
        this.title = title;
        this.time = time;
    }
    
    /**
    *Constructor non-active for repeatable task
    *@param title - task name
    *@param start - task start time
    *@param end - task end time
    *@param interval - task repeat interval
    */
    public Task(String title, Date start, Date end, int interval) {
        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException("Time can't be negative:");
        }
        if (interval <= 0) {
            throw new IllegalArgumentException("Interval can't be < 0: " 
            + interval);
        }
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
        repeated = true;
    }
    
    /**
    *Method for reading task name
    *@return task name
    */
    public String getTitle() {
        return title;
    }
    
    /**
    *Method for setting task name
    *@param title - set task name
    */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
    *Method for reading the task state
    *@return task state
    */
    public boolean isActive() {
        return active;
    }
    
    /**
    *Methods for setting the task state
    @param active - set task state
    */
    public void setActive(boolean active) {
        this.active = active;
    }
    
    /**
    *Method for reading time of non-repeatable task
    *@return task time or task start time if it's repeatable task
    */
    public Date getTime() {
        if (repeated) {
            Date newStart = new Date(start.getTime());
            return newStart;
        }

        Date newTime = new Date(time.getTime());
        return newTime;
    } 
    
    /**
    *Method for setting time of non-repeatable task
    *@param time - task time
    */
    public void setTime(Date time) {
        if (time == null) {
            throw new NullPointerException("Time can't be null: "
            + time);
        }
        repeated = false;
        this.time = time;
    }
    
    /**
    *Method for reading start time of repeatable task
    *@return task start time or task time if it's non-repeatable task
    */
    public Date getStartTime() {
        if (!repeated) {
            Date newTime = new Date(time.getTime());
            return newTime;
        }

        Date newStart = new Date(start.getTime());
        return newStart;
    }
    
    /**
    *Method for reading end time of repeatable task
    *@return task end time or task time if it's non-repeatable task
    */
    public Date getEndTime() {
        if (!repeated) {
            Date newTime = new Date(time.getTime());
            return newTime;
        }
        Date newEnd = new Date(end.getTime());
        return newEnd;
    }
    
    /**
    *Method for reading repeat interval of repeatable task
    *@return task repeat interval or 0 if it's non-repeatable task
    */
    public int getRepeatInterval() {
        if (!repeated) {
            return 0;
        }
        
        return interval;
    }
    
    /**
    *Method for setting repeat for task
    *@param start - task start time
    *@param end - task end time
    *@param interval - task interval
    */
    public void setTime(Date start, Date end, int interval) {
        if (end.compareTo(start) < 0) {
            throw new NullPointerException();
        }
        if (interval <= 0) {
            throw new IllegalArgumentException("Interval can't be < 0: " 
            + interval);
        }
        this.start = start;
        this.end = end;
        this.interval = interval;
        repeated = true;
    }
    
    /**
    *Method for checking task repeating
    @return state of repetition of the task
    */
    public boolean isRepeated() {
        return repeated;
    }
    
    /**
    *Method for reading next time when task will be execute
    *@return task next time when task will be execute 
    *or time if task is non-repeatable or -1 in other cases
    */
    public Date nextTimeAfter(Date time) {
        if (time == null) {
            throw new NullPointerException();
        }

        if (!isActive()) {
            return null;
        }    
        
        if (isRepeated()) {
            for (Date nextTime = getStartTime(); getEndTime().compareTo(nextTime) >= 0;) {
                if (nextTime.compareTo(time) > 0) {
                    return nextTime;
                }
                nextTime = new Date(nextTime.getTime() + interval * TIME);
            }
        }
        
        if (getTime().after(time)) {
            return getTime();
        }      
            
        return null;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int hashCode() {
        long hash = 0;
        if (isActive()) {
            hash = NUMBER + (isRepeated()
                    ? getStartTime().getTime() + getEndTime().getTime() * (long)getRepeatInterval()
                    : getTime().getTime());
        }
        return (int)hash;

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass().equals(this.getClass())) {
            Task chek = (Task) obj;
            if (chek.isActive() == this.isActive()) {
                if ((chek.title).equals(this.title) && (chek.getStartTime()).equals(this.getStartTime()) &&
                        (chek.getEndTime()).equals(this.getEndTime()) && chek.getRepeatInterval() ==  this.getRepeatInterval()) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }


}