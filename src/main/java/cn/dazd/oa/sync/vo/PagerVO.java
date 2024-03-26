package cn.dazd.oa.sync.vo;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author xiesh
 * @version 1.0
 * @date 2022/10/13 12:32
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Slf4j
public class PagerVO<T> {
    private Long total;
    private List<T> rows;

    public static <T> PagerVO<T> cons(Consumer<PagerVO<T>> s) {
        final PagerVO<T> ajaxVO = new PagerVO<>();
        try {
            s.accept(ajaxVO);
        } catch (Exception var3) {
            log.error("{}", var3.toString());
            ajaxVO.setTotal(0L);
            ajaxVO.setRows(Lists.newArrayList());
        }
        return ajaxVO;
    }
}
