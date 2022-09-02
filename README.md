# ms-app Getting Started

## Ready do it
### step 1 Choice project architecture

we choose DDD to design our project
please refer to this wiki:
```
https://blog.csdn.net/weixin_45203607/article/details/120248829
https://pdai.tech/md/arch/arch-x-view.html#ddd%E5%88%B0%E5%90%84%E7%A7%8D%E6%9E%B6%E6%9E%84
```

### step 2 Build the project foundation

1. add user interceptor
2. add swagger api document
3. add advices (ServiceLogAspect)
4. Global exception handling (GlobalExceptionHandler)
5. add schedule
6. add globalization
7. add CorsConfig

### step 3 Integration middleware TODO

#### add mysql mybatis plus config

### step 4 Business code design

1. vaild parameters
2. transaction rollback
3. page search

http://localhost:8090/swagger-ui.html#!/item-controller/createItemUsingPOST

```json
{
  "description": "联想拯救者Y700 8.8英寸游戏平板 骁龙870 2.5k 120Hz 100%DCI-P3色域 游戏视野模式 双X轴线性马达 12G+256G",
  "id": 0,
  "imgUrl": "https://img13.360buyimg.com/n1/s450x450_jfs/t1/120483/2/30805/76004/630ddc3cE21c1e940/ac5321959771f718.jpg.avif",
  "price": 2599.2,
  "promo": {
    "endDate": "2022-09-02T08:51:25.878Z",
    "id": 0,
    "itemId": 0,
    "promoItemPrice": 0,
    "promoName": "string",
    "startDate": "2022-09-02T08:51:25.878Z",
    "status": 0
  },
  "sales": 1000,
  "stock": 1000,
  "title": "联想拯救者Y700"
}
```

#### tool recommend
> https://github.com/zljin/renren-generator
> renren-generator是人人开源项目的代码生成器，可在线生成entity、xml、dao、service、html、js、sql代码，减少70%以上的开发任务



### step 5 Deploy this project to kubenetes
1. add jenkinsfile
2. add k8s yaml

## function
