package cn.dazd.oa.sync.dto;

import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * @author xiesh
 * @version 1.0
 * @date 2021/11/19 9:27
 */
public abstract class AbstractPager {

    private int page;
    @Setter
    private int rows;

    public PageRequest getPageRequest(Sort sort) {
        return PageRequest.of(this.getPage(), this.getRows(), sort);
    }

    public PageRequest getPageRequest() {
        return PageRequest.of(this.getPage(), this.getRows());
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = (page - 1) < 1 ? 0 : (page - 1);
    }

    public int getRows() {
        return rows;
    }

}
