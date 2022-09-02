package com.zoulj.msapp.infrastructure.db.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zoulj.msapp.domain.model.resource.ItemStockEntity;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ItemStockDao extends BaseMapper<ItemStockEntity> {
	
}
