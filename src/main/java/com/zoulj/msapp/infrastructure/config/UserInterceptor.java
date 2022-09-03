package com.zoulj.msapp.infrastructure.config;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.zoulj.msapp.domain.model.user.UserInfoEntity;
import com.zoulj.msapp.infrastructure.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author leonard
 * @Description 接口访问拦截器
 * @date 2021-11-19 14:19
 */
@Slf4j
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info(DateUtil.now() + "--preHandle:" + request.getRequestURL());

        try {
            String token = request.getHeader("X-Auth-Token");
            if(StrUtil.isEmpty(token) || !jwtUtil.checkedJWT(token)){
                return true;
            }
            Claims claims = jwtUtil.parseJWT(token);
            String email = MapUtil.getStr(claims, "username");
            String userStr = stringRedisTemplate.opsForValue().get("User:"+email);
            if(StrUtil.isEmpty(userStr)){
                return true;
            }
            UserInfoEntity user = JSONUtil.toBean(userStr, UserInfoEntity.class);
            if(user==null){
                return true;
            }
            ClientInfoHolder.setClientInfo(user);
        }catch (Exception e){

        }

        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.info(DateUtil.now() + "--postHandle:" + request.getRequestURL());
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        log.info(DateUtil.now() + "--afterCompletion:" + request.getRequestURL());
    }
}
