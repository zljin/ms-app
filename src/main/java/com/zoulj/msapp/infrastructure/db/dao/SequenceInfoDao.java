package com.zoulj.msapp.infrastructure.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zoulj.msapp.domain.model.user.SequenceInfoEntity;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface SequenceInfoDao extends BaseMapper<SequenceInfoEntity> {
	
}
