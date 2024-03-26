package cn.dazd.oa.sync.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@Data
@ToString
public class EcoMatrixFieldVO {
    private int matrixFieldId;
    private String matrixFieldName;
    private String matrixFieldCnName;
}
