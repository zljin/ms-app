package com.zoulj.msapp.interfaces.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author leonard
 * @date 2022/9/3
 * @Description TODO
 */
@Data
public class OrderVO {

    /**
     *
     */
    private String id;
    /**
     *
     */
    private String userId;
    /**
     *
     */
    private Long itemId;
    /**
     *
     */
    private BigDecimal itemPrice;
    /**
     *
     */
    private Integer amount;
    /**
     *
     */
    private BigDecimal orderPrice;
    /**
     *
     */
    private Long promoId;
}
