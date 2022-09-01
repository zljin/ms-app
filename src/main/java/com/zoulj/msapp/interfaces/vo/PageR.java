package com.zoulj.msapp.interfaces.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageR<E> {

    private int pageNo;
    private int pageSize;
    private int total;
    private List<E> list;

    /**
     * @param pageNo 当前页码
     * @param pageSize 分页大小
     * @param total 总页数
     * @param list 当前页的集合数量
     */
    public PageR(int pageNo, int pageSize, int total, List<E> list) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.total = total;
        this.list = list;
    }
}
