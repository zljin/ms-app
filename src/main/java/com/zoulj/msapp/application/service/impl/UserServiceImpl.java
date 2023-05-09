package com.zoulj.msapp.application.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zoulj.msapp.application.service.UserService;
import com.zoulj.msapp.domain.model.user.UserInfoEntity;
import com.zoulj.msapp.domain.model.user.UserPasswordEntity;
import com.zoulj.msapp.infrastructure.exception.BusinessException;
import com.zoulj.msapp.infrastructure.exception.EmBusinessError;
import com.zoulj.msapp.infrastructure.mapper.UserInfoMapper;
import com.zoulj.msapp.infrastructure.mapper.UserPasswordMapper;
import com.zoulj.msapp.infrastructure.utils.*;
import com.zoulj.msapp.interfaces.command.RegisterCommand;
import com.zoulj.msapp.interfaces.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserPasswordMapper userPasswordMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private JwtUtil jwtUtil;


    @Value("${jwt.config.ttl:12000}")
    private long ttl;

    @Override
    public UserVo getUserById(String id) throws BusinessException {
        UserInfoEntity userInfoEntity = userInfoMapper.selectById(id);
        if (null == userInfoEntity) {
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userInfoEntity, userVo);
        return userVo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
    public void register(RegisterCommand registerCommand) throws BusinessException {
        if (null == registerCommand) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        String otp = stringRedisTemplate.opsForValue().get(registerCommand.getEmail());
        if (!StrUtil.equals(otp, registerCommand.getOtpCode())) {
            throw new BusinessException(EmBusinessError.USER_OTP_FAIL);
        }

        try {
            UserInfoEntity userInfoEntity = new UserInfoEntity();
            BeanUtils.copyProperties(registerCommand, userInfoEntity);
            Long userId = SnowFlakeUtil.getInstance().nextId();
            userInfoEntity.setId(userId.toString());
            userInfoMapper.insert(userInfoEntity);
            UserPasswordEntity userPasswordEntity = new UserPasswordEntity();
            userPasswordEntity.setId(SnowFlakeUtil.getInstance().nextId());
            userPasswordEntity.setUserId(userId);
            userPasswordEntity.setEncrptPassword(registerCommand.getPassword());
            userPasswordMapper.insert(userPasswordEntity);
        } catch (Exception e) {
            throw new BusinessException(EmBusinessError.SQL_ERROR, e.toString());
        }
    }

    @Override
    public UserVo login(String email, String password) throws BusinessException {
        UserInfoEntity userInfoEntity
                = userInfoMapper.selectOne(new QueryWrapper<UserInfoEntity>().eq("email", email));
        if (null == userInfoEntity) {
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }

        UserPasswordEntity userPasswordEntity
                = userPasswordMapper.selectOne(new QueryWrapper<UserPasswordEntity>().eq("user_id", userInfoEntity.getId()));
        if (!StrUtil.equals(password, AESUtil.decrypt(userPasswordEntity.getEncrptPassword()))) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }

        UserVo userVo = getUserById(userInfoEntity.getId());
        String generateToken = jwtUtil.createJWT("1", "login_token", email, password);
        userVo.setToken(generateToken);

        String tokenKey = AppConstant.LOGIN_USER_KEY + generateToken;
        Map<String, Object> userMap = BeanUtil.beanToMap(userVo, false, true);
        stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
        stringRedisTemplate.expire(tokenKey, AppConstant.LOGIN_USER_TTL, TimeUnit.MINUTES);
        return userVo;
    }


    @Override
    public void getOtp(String email) {
        String otp = ValidateCodeUtils.generateValidateCode(6);
        stringRedisTemplate.opsForValue().set(email, otp, 1, TimeUnit.MINUTES);
        //hutool 发邮件
        //String text = MailUtil.send(email, "ms-app 验证码", "验证码： "+otp, false);
        //log.info("email:{} otp:{} text:{}", email, otp,text);
        log.info("email:{} otp:{}", email, otp);
    }
}
