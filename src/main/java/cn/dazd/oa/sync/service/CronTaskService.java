package cn.dazd.oa.sync.service;

import cn.dazd.oa.sync.dao.CronTaskDAO;
import cn.dazd.oa.sync.entity.CronTaskEntity;
import cn.hutool.cron.CronUtil;
import cn.hutool.setting.Setting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CronTaskService {
    private final CronTaskDAO cronTaskDAO;

    public CronTaskService(CronTaskDAO cronTaskDAO) {
        this.cronTaskDAO = cronTaskDAO;
    }

    public void doStart() {
        CronUtil.setMatchSecond(true);
        List<CronTaskEntity> taskEntities = cronTaskDAO.getAllList();
        final Setting cronSetting = new Setting();
        taskEntities.forEach(cronTaskEntity -> {
            cronSetting.set(cronTaskEntity.getTaskClassWithMethodName(), cronTaskEntity.getSchedulingPattern());
        });
        CronUtil.setCronSetting(cronSetting);
        CronUtil.start();
    }

    public CronTaskEntity findByTaskClassWithMethodName(String classWithMethodName) {
        return cronTaskDAO.findByTaskClassWithMethodName(classWithMethodName);
    }
}
