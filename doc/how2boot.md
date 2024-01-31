# SpringBoot
> https://github.com/zljin/ms-app

基于以上项目实现了以下springboot的功能
```
1. 整合mpb
com.zoulj.msapp.infrastructure.config.MybatisConfig

2. 整合swagger
com.zoulj.msapp.infrastructure.config.Swagger2

3. 整合@Aspect切面
com.zoulj.msapp.infrastructure.aspect.ServiceLogAspect

4. 事务的两种写法
com.zoulj.msapp.application.service.impl.ItemServiceImpl#createItem
com.zoulj.msapp.application.service.impl.OrderServiceImpl#createOrder


5. axios跨域配置
com.zoulj.msapp.infrastructure.config.CorsConfig

6. commandline
com.zoulj.msapp.infrastructure.schedule.StarUpDevTask

7. 定时任务
@EnableScheduling
com.zoulj.msapp.infrastructure.schedule.ScheduledJob

8. 拦截器
com.zoulj.msapp.infrastructure.config.UserInterceptor
com.zoulj.msapp.infrastructure.config.WebMvcConfig

9. 全局异常处理
com.zoulj.msapp.infrastructure.config.GlobalExceptionHandler

10. 日志框架
logging.config: classpath:logback.xml

11. jwt的token登录生失效与验证
com.zoulj.msapp.infrastructure.utils.JwtUtil#createJWT

12. @Validate的传参校验和swagger annotation sample
com.zoulj.msapp.interfaces.command.ItemCommand
com.zoulj.msapp.interfaces.controller.ItemController#createItem

```

其他整合：
> git@github.com:hansonwang99/Spring-Boot-In-Action.git


# SpringCloud
> https://github.com/alibaba

> https://github.com/zljin/go-cloud