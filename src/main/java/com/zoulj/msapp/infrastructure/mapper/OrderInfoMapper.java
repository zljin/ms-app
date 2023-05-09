package com.zoulj.msapp.infrastructure.mapper;

import com.zoulj.msapp.domain.model.order.OrderInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfoEntity> {
	
}
