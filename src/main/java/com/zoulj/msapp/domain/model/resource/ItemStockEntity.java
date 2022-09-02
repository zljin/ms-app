package com.zoulj.msapp.domain.model.resource;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("item_stock")
public class ItemStockEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Long id;
	/**
	 * 
	 */
	private Integer stock;
	/**
	 * 
	 */
	private Long itemId;

}
