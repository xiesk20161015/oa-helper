package cn.dazd.oa.sync.response;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

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
public class AjaxPagerResponse<T> {
    private String message;
    private Integer code;
    private Long total;
    private Integer pages;
    private List<T> rows;

    public static <T> AjaxPagerResponse<T> cons(Consumer<AjaxPagerResponse<T>> s) {
        final AjaxPagerResponse<T> ajaxVO = new AjaxPagerResponse<>();
        try {
            ajaxVO.setMessage("操作成功。");
            ajaxVO.setCode(HttpStatus.OK.value());
            s.accept(ajaxVO);
        } catch (Exception var3) {
            log.error("{}", var3.toString());
            ajaxVO.code = HttpStatus.BAD_REQUEST.value();
            ajaxVO.setTotal(0L);
            ajaxVO.setPages(0);
            ajaxVO.setRows(Lists.newArrayList());
            ajaxVO.setMessage(var3.getLocalizedMessage());
        }
        return ajaxVO;
    }
}
