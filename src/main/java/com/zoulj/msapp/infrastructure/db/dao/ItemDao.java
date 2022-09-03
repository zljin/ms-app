package com.zoulj.msapp.infrastructure.db.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zoulj.msapp.domain.model.product.ItemEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface ItemDao extends BaseMapper<ItemEntity> {

    int increaseSales(@Param("id") Long id, @Param("amount") Integer amount);

}
