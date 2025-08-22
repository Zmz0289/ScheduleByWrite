package github.zmz;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.LockSupport;

public class ScheduleService {

    ExecutorService executorService = Executors.newFixedThreadPool(3);

    Trigger trigger = new Trigger();

    void schedule(Runnable task, long delay) {
        Job job = new Job(task, System.currentTimeMillis() + delay, delay);
        trigger.addJob(job);
        trigger.wakeUp();
    }

    class Trigger {
        BlockingQueue<Job> jobList = new PriorityBlockingQueue<>(100, Job.comparator());

        Thread thread = new Thread(runnable(), "触发器线程");

        {
            thread.start();
        }

        Runnable runnable() {
            return () -> {
                while (true) {
                    if (!jobList.isEmpty()) {
                        Job currentJob = jobList.peek();

                        long delay = currentJob.getStartTime() - System.currentTimeMillis();
                        if (delay < 0) {
                            currentJob = jobList.poll();
                            executorService.execute(currentJob.getTask());

                            // nextJob
                            addJob(new Job(currentJob.getTask(), System.currentTimeMillis() + currentJob.getDelay(), currentJob.getDelay()));
                        } else {
                            LockSupport.parkUntil(currentJob.getStartTime());
                        }
                    }

                }
            };
        }


        void addJob(Job job) {
            jobList.offer(job);
        }

        void wakeUp() {
            LockSupport.unpark(thread);
        }

    }

}
