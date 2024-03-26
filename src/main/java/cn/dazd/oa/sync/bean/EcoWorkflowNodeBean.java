package cn.dazd.oa.sync.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@Data
@ToString
public class EcoWorkflowNodeBean {
    private Integer workflowId;
    private Integer workTypeId;
    private String workTypeName;
    private String workflowName;
    private String workflowNodeName;
    private Integer workflowNodeId;

}
