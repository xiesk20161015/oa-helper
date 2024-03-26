package cn.dazd.oa.sync.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@Data
@ToString
public class EcoMatrixInfoBean {
    private Integer id;
    private String name;
    private Integer fid;
    private String displayName;
    private String fieldName;
}
