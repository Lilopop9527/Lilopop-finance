package com.common.core.pojo;

import java.util.List;

/**
 * @author: Lilopop
 * @description:
 */
public class PageBody<T> {
    private Long total;
    private List<T> data;
    private Integer pageNum;
    private Integer pageSize;

    public PageBody() {
    }

    public PageBody(Long total, List<T> data, Integer pageNum, Integer pageSize) {
        this.total = total;
        this.data = data;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "PageBody{" +
                "total=" + total +
                ", data=" + data +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
