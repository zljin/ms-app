package com.zoulj.msapp.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zoulj.msapp.domain.model.user.UserInfoEntity;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfoEntity> {
	
}
