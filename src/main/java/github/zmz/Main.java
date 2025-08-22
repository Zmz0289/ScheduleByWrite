package github.zmz;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public static void main(String[] args) {

        ScheduleService scheduleService = new ScheduleService();

        String format = "HH:mm:ss SSS";
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        scheduleService.schedule(() -> {
            System.out.println("任务1 时间：" + sdf.format(new Date()));
        }, 200);

        scheduleService.schedule(() -> {
            System.out.println("任务2 时间：" + sdf.format(new Date()));
        }, 600);

        System.out.println("主线程执行完毕");


    }

}
