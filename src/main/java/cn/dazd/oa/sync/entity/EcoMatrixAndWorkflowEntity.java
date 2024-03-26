package cn.dazd.oa.sync.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@EqualsAndHashCode
@Data
@ToString
@Entity
@Table(name = "EcoMatrixAndWorkflow")
public class EcoMatrixAndWorkflowEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "matrixName")
    private String matrixName;
    @Basic
    @Column(name = "matrixInfoId")
    private int matrixInfoId;
    @Basic
    @Column(name = "matrixFieldId")
    private int matrixFieldId;
    @Basic
    @Column(name = "matrixFieldName")
    private String matrixFieldName;
    @Basic
    @Column(name = "matrixFieldCnName")
    private String matrixFieldCnName;
    @Basic
    @Column(name = "workflowId")
    private int workflowId;
    @Basic
    @Column(name = "workflowName")
    private String workflowName;
    @Basic
    @Column(name = "workflowNodeName")
    private String workflowNodeName;
    @Basic
    @Column(name = "workflowNodeId")
    private int workflowNodeId;
    @Basic
    @Column(name = "workflowNodeGroup")
    private String workflowNodeGroup;
    @Basic
    @Column(name = "workflowNodeGroupId")
    private int workflowNodeGroupId;
    @Basic
    @Column(name = "workTypeId")
    private int workTypeId;
    @Basic
    @Column(name = "workTypeName")
    private String workTypeName;
    @Basic
    @Column(name = "createTime")
    private Timestamp createTime;
    @Basic
    @Column(name = "updateTime")
    private Timestamp updateTime;
    @Basic
    @Column(name = "synchronizedStatus")
    private int synchronizedStatus;
    @Basic
    @Column(name = "synchronizedDetail")
    private String synchronizedDetail;
}
