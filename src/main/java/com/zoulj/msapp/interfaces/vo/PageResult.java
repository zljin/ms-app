package com.zoulj.msapp.interfaces.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<E> {

    /**
     * @param pageNo 当前页码
     * @param pageSize 分页大小
     * @param total 总页数
     * @param list 当前页的集合数量
     */
    private long pageNo;
    private long pageSize;
    private long total;
    private List<E> list;
}
