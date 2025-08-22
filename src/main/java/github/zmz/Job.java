package github.zmz;

import java.util.Comparator;

public class Job implements Comparable<Job> {

    Runnable task;

    Long startTime;

    Long delay;

    public Runnable getTask() {
        return task;
    }

    public void setTask(Runnable task) {
        this.task = task;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getDelay() {
        return delay;
    }

    public void setDelay(Long delay) {
        this.delay = delay;
    }

    @Override
    public int compareTo(Job o) {
        return (int) (this.startTime - o.startTime);
    }

    public static Comparator<Job> comparator() {
        return (o1, o2) -> (int) (o1.startTime - o2.startTime);
    }

    public Job(Runnable task, Long startTime, Long delay) {
        this.task = task;
        this.startTime = startTime;
        this.delay = delay;
    }
}
