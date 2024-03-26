package cn.dazd.oa.sync.service;

import cn.dazd.oa.sync.bean.EcoMatrixInfoBean;
import cn.dazd.oa.sync.bean.EcoWorkflowNodeBean;
import cn.dazd.oa.sync.dao.EcoMatrixAndWorkflowDAO;
import cn.dazd.oa.sync.dto.MatrixListPageDTO;
import cn.dazd.oa.sync.entity.EcoMatrixAndWorkflowEntity;
import cn.dazd.oa.sync.tools.db.OADbConfig;
import cn.dazd.oa.sync.vo.EcoMatrixAndWorkflowVO;
import cn.dazd.oa.sync.vo.EcoMatrixFieldVO;
import cn.dazd.oa.sync.vo.EcoMatrixVO;
import cn.dazd.oa.sync.vo.PagerVO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.BeanListHandler;
import com.google.common.collect.Lists;
import com.querydsl.core.QueryResults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EcoMatrixAndWorkflowService {
    private final EcoMatrixAndWorkflowDAO ecoMatrixAndWorkflowDAO;
    private final OADbConfig oaDbConfig;

    public EcoMatrixAndWorkflowService(EcoMatrixAndWorkflowDAO ecoMatrixAndWorkflowDAO, OADbConfig oaDbConfig) {
        this.ecoMatrixAndWorkflowDAO = ecoMatrixAndWorkflowDAO;
        this.oaDbConfig = oaDbConfig;
    }

    public List<EcoMatrixInfoBean> getMatrixListFromOA() {
        Entity where = Entity.create("ECOLOGY.MATRIXINFO m INNER JOIN ECOLOGY.MATRIXFIELDINFO f on f.MATRIXID = m.ID ORDER BY M.ID ASC");
        try {
            return DbUtil.use(oaDbConfig.getDataSource()).find(Lists.newArrayList("M.NAME", "M.ID", "f.DISPLAYNAME", "f.FIELDNAME", "f.ID as FID"), where, BeanListHandler.create(EcoMatrixInfoBean.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public EcoMatrixAndWorkflowEntity makeEcoMatrix(EcoMatrixInfoBean ecoMatrixInfoBean, EcoWorkflowNodeBean ecoWorkflowNodeBean) {
        EcoMatrixAndWorkflowEntity ecoMatrixAndWorkflowEntity = new EcoMatrixAndWorkflowEntity();
        ecoMatrixAndWorkflowEntity.setMatrixFieldId(ecoMatrixInfoBean.getFid());
        ecoMatrixAndWorkflowEntity.setMatrixName(ecoMatrixInfoBean.getName());
        ecoMatrixAndWorkflowEntity.setMatrixInfoId(ecoMatrixInfoBean.getId());
        ecoMatrixAndWorkflowEntity.setMatrixFieldName(ecoMatrixInfoBean.getFieldName());
        ecoMatrixAndWorkflowEntity.setMatrixFieldCnName(ecoMatrixInfoBean.getDisplayName());
        ecoMatrixAndWorkflowEntity.setWorkflowId(ecoWorkflowNodeBean.getWorkflowId());
        ecoMatrixAndWorkflowEntity.setWorkflowName(ecoWorkflowNodeBean.getWorkflowName());
        ecoMatrixAndWorkflowEntity.setWorkflowNodeId(ecoWorkflowNodeBean.getWorkflowNodeId());
        ecoMatrixAndWorkflowEntity.setWorkflowNodeName(ecoWorkflowNodeBean.getWorkflowNodeName());
        ecoMatrixAndWorkflowEntity.setCreateTime(DateUtil.date().toTimestamp());
        ecoMatrixAndWorkflowEntity.setUpdateTime(DateUtil.date().toTimestamp());
        ecoMatrixAndWorkflowEntity.setWorkTypeId(ecoWorkflowNodeBean.getWorkTypeId());
        ecoMatrixAndWorkflowEntity.setWorkTypeName(ecoWorkflowNodeBean.getWorkTypeName());
        ecoMatrixAndWorkflowEntity.setSynchronizedStatus(1);
        ecoMatrixAndWorkflowEntity.setSynchronizedDetail(StrUtil.EMPTY);
        return ecoMatrixAndWorkflowEntity;
    }

    public void saveAndUpdateMatrix(List<EcoMatrixAndWorkflowEntity> ecoMatrixAndWorkflowEntities) {
        ecoMatrixAndWorkflowDAO.saveAll(ecoMatrixAndWorkflowEntities);
    }

    public List<EcoWorkflowNodeBean> getWorkflowNodeFromOA(Integer matrixFieldId) {
        try {
            return DbUtil.use(oaDbConfig.getDataSource())
                    .query("SELECT a.ID AS workflowId, wt.TYPENAME as workTypeName,wt.id as workTypeId,a.workflowname, c.nodeid as workflowNodeId, b.NODENAME AS workflowNodeName " +
                            "FROM ECOLOGY.WORKFLOW_BASE a LEFT JOIN ECOLOGY.WORKFLOW_TYPE wt on wt.id = a.WORKFLOWTYPE LEFT JOIN ECOLOGY.WORKFLOW_FLOWNODE c ON a.ID = c.WORKFLOWID LEFT JOIN ECOLOGY.WORKFLOW_NODEBASE b ON b.ID = c.NODEID " +
                            " WHERE a.ISVALID = 1 AND c.nodeid IN ( " +
                            "SELECT DISTINCT nodeid FROM ECOLOGY.WORKFLOW_NODEGROUP WHERE id IN ( SELECT groupid FROM ECOLOGY.WORKFLOW_GROUPDETAIL WHERE id IN ( " +
                            "SELECT groupdetailid FROM ECOLOGY.WORKFLOW_GROUPDETAIL_MATRIX WHERE value_field = ?))) ", BeanListHandler.create(EcoWorkflowNodeBean.class), matrixFieldId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearEcoMatrix() {
        ecoMatrixAndWorkflowDAO.clearEcoMatrix();
    }

    public PagerVO<EcoMatrixAndWorkflowVO> getMatrixAndNodeListPage(MatrixListPageDTO matrixListPageDTO) {
        PageRequest page = PageRequest.of(matrixListPageDTO.getPage(), matrixListPageDTO.getRows());
        QueryResults<EcoMatrixAndWorkflowEntity> queryResults = ecoMatrixAndWorkflowDAO.getMatrixAndNodeListPage(
                matrixListPageDTO.getMatrixInfoId(), matrixListPageDTO.getMatrixFieldId(), matrixListPageDTO.getWorkflowName(), matrixListPageDTO.getWorkflowNodeName(), page
        );
        return PagerVO.cons(pagerVO -> {
            pagerVO.setRows(queryResults.getResults().stream().map(r -> BeanUtil.copyProperties(r, EcoMatrixAndWorkflowVO.class)).collect(Collectors.toList()));
            pagerVO.setTotal(queryResults.getTotal());
        });
    }

    public List<EcoMatrixVO> getMatrixList() {
        List<EcoMatrixAndWorkflowEntity> ecoMatrixAndWorkflowEntities = ecoMatrixAndWorkflowDAO.getMatrixList();
        return ecoMatrixAndWorkflowEntities.stream().map(s -> BeanUtil.copyProperties(s, EcoMatrixVO.class)).collect(Collectors.toList());
    }

    public List<EcoMatrixFieldVO> getMatrixFeildList(Integer matrixInfoId) {
        List<EcoMatrixAndWorkflowEntity> ecoMatrixAndWorkflowEntities = ecoMatrixAndWorkflowDAO.getMatrixFeildList(matrixInfoId);
        return ecoMatrixAndWorkflowEntities.stream().map(s -> BeanUtil.copyProperties(s, EcoMatrixFieldVO.class)).collect(Collectors.toList());
    }
}
