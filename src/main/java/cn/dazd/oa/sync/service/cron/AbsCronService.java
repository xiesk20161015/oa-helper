package cn.dazd.oa.sync.service.cron;

import cn.dazd.oa.sync.entity.CronTaskEntity;
import cn.dazd.oa.sync.service.CronTaskService;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.Getter;

import java.util.concurrent.ExecutorService;


@Getter
public abstract class AbsCronService {
    private static final ExecutorService executorService = ThreadUtil.newFixedExecutor(1, AbsCronService.class.getName(), false);
    private final CronTaskService cronTaskService;
    private final String taskClassWithMethodName;

    public <T> AbsCronService(Class<T> tClass) {
        cronTaskService = SpringUtil.getBean(CronTaskService.class);
        taskClassWithMethodName = tClass.getName() + ".doTask";
    }

    public synchronized void run() {
        executorService.execute(() -> {
            CronTaskEntity cronTaskEntity = getCronTaskService().findByTaskClassWithMethodName(getTaskClassWithMethodName());
            if (cronTaskEntity.getTaskStatus() == 1) {
                doTask();
            }
        });
    }

    public abstract void doTask();
}
