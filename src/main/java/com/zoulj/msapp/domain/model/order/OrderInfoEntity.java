package com.zoulj.msapp.domain.model.order;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("order_info")
public class OrderInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private Long userId;
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
