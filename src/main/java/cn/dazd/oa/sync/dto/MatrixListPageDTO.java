package cn.dazd.oa.sync.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = false)
@Data
@ToString
public class MatrixListPageDTO extends AbstractPager {
    private Integer matrixInfoId;
    private Integer matrixFieldId;
    private String workflowName;
    private String workflowNodeName;
}
