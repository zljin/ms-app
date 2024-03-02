# ms-app Getting Started

> 前端代码：https://github.com/zljin/ms-ui-app

## 简要介绍

基于springboot+redis+mysql并采用领域驱动模型实现商品优惠卷秒杀的功能

主要功能有：
1. 用户的登录注册
2. 商品的添加和分页展示
3. 优惠卷逻辑（在优惠时间内可用优惠卷抵扣）
4. 订单秒杀：创建订单记录,扣减库存,增加销量数据

启动项目：
1. 安装mysql,将数据导入数据库
2. 安装redis
3. 将postman脚本导入调试

Deploy目录提供了部署的脚本和管道代码

src/main/api目录提供了postman脚本和db脚本还有jmeter的脚本

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

## 其他

```
mvn clean package
docker build -t=’msapp’ .
docker run -di --name=msapp -p 8090:8090 msapp:latest
kubectl apply -f deploy.yaml
```

maven必须安装此插件，否则docker build的容器会error manifest 
```
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```



