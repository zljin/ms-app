package com.zoulj.msapp.interfaces.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<E> {

    private String status;

    /**
     * @param pageNo 当前页码
     * @param pages 总页数
     * @param total 总条数
     * @param list 当前页的集合数量
     */
    private long pageCurrent;
    private long pages;
    private long total;
    private List<E> data;
}
