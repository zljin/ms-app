package com.zoulj.msapp.domain.model.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sequence_info")
public class SequenceInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private Integer currentValue;
	/**
	 * 
	 */
	private Integer step;

}
