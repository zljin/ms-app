package com.zoulj.msapp.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zoulj.msapp.domain.model.user.UserPasswordEntity;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserPasswordMapper extends BaseMapper<UserPasswordEntity> {
	
}
