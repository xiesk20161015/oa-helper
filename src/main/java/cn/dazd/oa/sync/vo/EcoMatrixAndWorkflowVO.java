package cn.dazd.oa.sync.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Date;
import java.sql.Timestamp;

@EqualsAndHashCode
@Data
@ToString
public class EcoMatrixAndWorkflowVO {
    private int id;
    private String matrixName;
    private int matrixInfoId;
    private int matrixFieldId;
    private String matrixFieldName;
    private String matrixFieldCnName;
    private int workflowId;
    private String workflowName;
    private String workflowNodeName;
    private int workflowNodeId;
    private String workflowNodeGroup;
    private int workflowNodeGroupId;
    private int workTypeId;
    private String workTypeName;
    private Date createTime;
    private Date updateTime;
    private int synchronizedStatus;
    private String synchronizedDetail;
}
