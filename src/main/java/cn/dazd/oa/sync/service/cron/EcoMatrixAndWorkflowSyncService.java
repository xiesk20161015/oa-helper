package cn.dazd.oa.sync.service.cron;

import cn.dazd.oa.sync.bean.EcoMatrixInfoBean;
import cn.dazd.oa.sync.bean.EcoWorkflowNodeBean;
import cn.dazd.oa.sync.entity.EcoMatrixAndWorkflowEntity;
import cn.dazd.oa.sync.service.EcoMatrixAndWorkflowService;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class EcoMatrixAndWorkflowSyncService {
    private final EcoMatrixAndWorkflowService ecoMatrixAndWorkflowService;

    public EcoMatrixAndWorkflowSyncService() {
        ecoMatrixAndWorkflowService = SpringUtil.getBean(EcoMatrixAndWorkflowService.class);
    }

    @SuppressWarnings("unused")
    public synchronized void doTask() {
        new AbsCronService(EcoMatrixAndWorkflowSyncService.class) {
            @Override
            public void doTask() {
                final List<EcoMatrixInfoBean> ecoMatrixInfoBeanList = ecoMatrixAndWorkflowService.getMatrixListFromOA();
                final List<EcoMatrixAndWorkflowEntity> ecoMatrixAndWorkflowEntities = Lists.newArrayList();
                ecoMatrixInfoBeanList.forEach(ecoMatrixInfoBean -> {
                    List<EcoWorkflowNodeBean> ecoWorkflowNodeBeans = ecoMatrixAndWorkflowService.getWorkflowNodeFromOA(ecoMatrixInfoBean.getFid());
                    ecoMatrixAndWorkflowEntities.addAll(
                            ecoWorkflowNodeBeans.stream().map(ecoWorkflowNodeBean -> ecoMatrixAndWorkflowService.makeEcoMatrix(ecoMatrixInfoBean, ecoWorkflowNodeBean)).collect(Collectors.toList())
                    );
                });
                ecoMatrixAndWorkflowService.clearEcoMatrix();
                ecoMatrixAndWorkflowService.saveAndUpdateMatrix(ecoMatrixAndWorkflowEntities);
            }
        }.run();

    }
}
