package com.zoulj.msapp.domain.model.product;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
@TableName("item")
public class ItemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Long id;
	/**
	 * 
	 */
	private String title;
	/**
	 * 
	 */
	private BigDecimal price;
	/**
	 * 
	 */
	private String description;
	/**
	 * 
	 */
	private Integer sales;
	/**
	 * 
	 */
	private String imgUrl;

}
