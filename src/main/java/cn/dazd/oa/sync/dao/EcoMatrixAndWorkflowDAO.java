package cn.dazd.oa.sync.dao;

import cn.dazd.oa.sync.entity.EcoMatrixAndWorkflowEntity;
import cn.dazd.oa.sync.entity.QEcoMatrixAndWorkflowEntity;
import cn.dazd.oa.sync.repo.EcoMatrixAndWorkflowRepo;
import cn.dazd.oa.sync.tools.dsl.Predicates;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EcoMatrixAndWorkflowDAO {
    private final EcoMatrixAndWorkflowRepo ecoMatrixAndWorkflowRepo;
    private final JPAQueryFactory jpaQueryFactory;
    private final QEcoMatrixAndWorkflowEntity qEcoMatrixAndWorkflowEntity = QEcoMatrixAndWorkflowEntity.ecoMatrixAndWorkflowEntity;

    public EcoMatrixAndWorkflowDAO(EcoMatrixAndWorkflowRepo ecoMatrixAndWorkflowRepo, JPAQueryFactory jpaQueryFactory) {
        this.ecoMatrixAndWorkflowRepo = ecoMatrixAndWorkflowRepo;
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Transactional(rollbackOn = Exception.class)
    public void save(EcoMatrixAndWorkflowEntity ecoMatrixAndWorkflowEntity) {
        ecoMatrixAndWorkflowRepo.save(ecoMatrixAndWorkflowEntity);
    }

    @Transactional(rollbackOn = Exception.class)
    public void saveAll(List<EcoMatrixAndWorkflowEntity> ecoMatrixAndWorkflowEntities) {
        ecoMatrixAndWorkflowRepo.saveAll(ecoMatrixAndWorkflowEntities);
    }

    @Transactional(rollbackOn = Exception.class)
    public void clearEcoMatrix() {
        ecoMatrixAndWorkflowRepo.deleteAll();
    }

    public QueryResults<EcoMatrixAndWorkflowEntity> getMatrixAndNodeListPage(Integer matrixInfoId, Integer matrixFieldId, String workflowName, String workflowNodeName, PageRequest page) {
        final Predicates predicates = Predicates.create();
        predicates.add(() -> NumberUtil.isValidNumber(matrixInfoId), () -> qEcoMatrixAndWorkflowEntity.matrixInfoId.eq(matrixInfoId));
        predicates.add(() -> NumberUtil.isValidNumber(matrixFieldId), () -> qEcoMatrixAndWorkflowEntity.matrixFieldId.eq(matrixFieldId));
        predicates.add(() -> StrUtil.isNotEmpty(workflowName), () -> qEcoMatrixAndWorkflowEntity.workflowName.like(Expressions.asString("%" + workflowName + "%")));
        predicates.add(() -> StrUtil.isNotEmpty(workflowNodeName), () -> qEcoMatrixAndWorkflowEntity.workflowNodeName.like(Expressions.asString("%" + workflowNodeName + "%")));
        return jpaQueryFactory.selectFrom(qEcoMatrixAndWorkflowEntity)
                .offset(page.getOffset()).limit(page.getPageSize())
                .where(predicates.getPredicates().toArray(new Predicate[0]))
                .orderBy(qEcoMatrixAndWorkflowEntity.matrixInfoId.asc(), qEcoMatrixAndWorkflowEntity.matrixFieldCnName.asc(), qEcoMatrixAndWorkflowEntity.workflowNodeId.asc()).fetchResults();
    }

    public List<EcoMatrixAndWorkflowEntity> getMatrixList() {
        return jpaQueryFactory.selectDistinct(
                Projections.bean(qEcoMatrixAndWorkflowEntity,
                        qEcoMatrixAndWorkflowEntity.matrixInfoId, qEcoMatrixAndWorkflowEntity.matrixName)
        ).from(qEcoMatrixAndWorkflowEntity).fetch();
    }

    public List<EcoMatrixAndWorkflowEntity> getMatrixFeildList(Integer matrixInfoId) {
        return jpaQueryFactory.selectDistinct(
                Projections.bean(qEcoMatrixAndWorkflowEntity,
                        qEcoMatrixAndWorkflowEntity.matrixFieldId, qEcoMatrixAndWorkflowEntity.matrixFieldName, qEcoMatrixAndWorkflowEntity.matrixFieldCnName)
        ).from(qEcoMatrixAndWorkflowEntity).where(qEcoMatrixAndWorkflowEntity.matrixInfoId.eq(matrixInfoId)).fetch();
    }
}
