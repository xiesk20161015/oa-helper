package cn.dazd.oa.sync.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.function.Consumer;

@EqualsAndHashCode
@ToString
@Data
@Slf4j
public class AjaxResponse<T> {
    private String message;
    private Integer code;
    private T data;

    public static <T> AjaxResponse<T> cons(Consumer<AjaxResponse<T>> s) {
        final AjaxResponse<T> ajaxVO = new AjaxResponse<>();
        try {
            ajaxVO.setMessage("操作成功。");
            ajaxVO.setCode(HttpStatus.OK.value());
            s.accept(ajaxVO);
        } catch (Exception var3) {
            log.error("{}", var3.toString());
            ajaxVO.code = HttpStatus.BAD_REQUEST.value();
            ajaxVO.setMessage(var3.getLocalizedMessage());
        }
        return ajaxVO;
    }
}
