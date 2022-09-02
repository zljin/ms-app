package com.zoulj.msapp.infrastructure.db.dao;

import com.zoulj.msapp.domain.model.promote.PromoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface PromoDao extends BaseMapper<PromoEntity> {
	
}
