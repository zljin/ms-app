# ms-app Getting Started

> 前端代码：https://github.com/zljin/ms-ui-app

## 简要介绍

项目采用领域驱动模型实现的springboot应用，主要整合了redis,mysql实现秒杀功能
其中也包括端到端部署的脚本，能够从jar包到docker容器到kubernetes
未来希望集成wiremock写集成测试，再通过jacoo实现代码覆盖率扫描报告todo

主要功能有：
用户的登录注册
商品的添加，优惠卷的添加（在优惠时间内可用优惠卷抵扣）
订单秒杀：创建订单记录，扣减库存,增加销量数据

## 资料
Deploy\ 目录存放着部署的脚本和管道代码
在 ms-app\src\main\api中提供了postman脚本和db脚本还有jmeter的脚本

要启动项目请先安装mysql,将数据导入数据库
然后安装redis
然后将postman脚本导入调试


## 对应的springboot应用场景
> 某些功能实现的具体方法

```
1. 整合mybatis-plus
com.zoulj.msapp.infrastructure.config.MybatisConfig

2. 整合swagger
com.zoulj.msapp.infrastructure.config.Swagger2

http://localhost:8090/swagger-ui.html

3. 整合@Aspect切面（日志切面和用户验证切面）
com.zoulj.msapp.infrastructure.aspect.ServiceLogAspect
com.zoulj.msapp.infrastructure.aspect.UserCheckAspect

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

13. 国际化
com.zoulj.msapp.AppTest.testGlobalization

14. 分页实现

15. JSR2.0 validate 校验

```

