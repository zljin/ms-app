package com.zoulj.msapp.interfaces.vo;

import com.zoulj.msapp.interfaces.command.ItemCommand;

import java.math.BigDecimal;

/**
 * @author leonard
 * @date 2022/9/2
 * @Description TODO
 */
public class ItemVO extends ItemCommand {

    //Long类型在前端超长处理方式
    private String idStr;

    //秒杀活动开始时间
    private String startDate;

    private String promoId;

    private BigDecimal promoItemPrice;

    //商品是否在秒杀活动中，以及对应的状态：0表示没有秒杀活动，1表示秒杀活动等待开始，2表示进行中
    private Integer promoStatus;

    public Integer getPromoStatus() {
        return promoStatus;
    }

    public void setPromoStatus(Integer promoStatus) {
        this.promoStatus = promoStatus;
    }

    public String getPromoId() {
        return promoId;
    }

    public void setPromoId(String promoId) {
        this.promoId = promoId;
    }

    public BigDecimal getPromoItemPrice() {
        return promoItemPrice;
    }

    public void setPromoItemPrice(BigDecimal promoItemPrice) {
        this.promoItemPrice = promoItemPrice;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }
}
