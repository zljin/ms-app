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
    http://localhost:8090/swagger-ui.html
3. add advices (ServiceLogAspect,UsercheckAspect)
4. Global exception handling (GlobalExceptionHandler)
5. add schedule
6. add globalization
7. add CorsConfig

### step 3 Integration middleware TODO

1. add mysql and mybatis plus
2. add redis 

### step 4 Business code design

feature: 
1. vaildate parameters
2. transaction rollback
3. page search

business interface:
http://localhost:8090/swagger-ui.html#!/item-controller/createItemUsingPOST
```json
{
  "description": "联想拯救者Y7000P 2022 英特尔酷睿i5 15.6英寸游戏笔记本电脑(12代i5-12500H 16G 512G RTX3050 2.5k电竞屏)",
  "imgUrl": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ0fYHgzv0IJWDWX3YKxaEuepxdq0FjF-9J0U66FfSrjg&s",
  "price": 7299.2,
  "promo": {
    "endDate": "2022-09-10T08:51:25.878Z",
    "promoItemPrice": 6299.2,
    "promoName": "联想拯救者Y7000P 1000折扣卷",
    "startDate": "2022-09-07T08:51:25.878Z",
    "status": 1
  },
  "sales": 0,
  "stock": 100,
  "title": "联想拯救者Y7000P"
}
```
http://localhost:8090/swagger-ui.html#!/user-controller/getOtpUsingGET（先获取验证码）
http://localhost:8090/swagger-ui.html#!/user-controller/registerUsingPOST
```json
{
  "age": 24,
  "email": "leonard_zou@163.com",
  "gender": "1",
  "name": "leonard",
  "otpCode": "612888",
  "password": "leonard",
  "registerMode": "by email",
  "telphone": "15073311125",
  "thirdPartyId": "77777"
}
```

http://localhost:8090/swagger-ui.html#!/order-controller/createOrderUsingPOST
curl 'http://localhost:8090/order/createorder?itemId=76340491978381728816&amount=1&promoId=763404919825760256' \
  -X 'POST' \
  -H 'X-Auth-Token: eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwic3ViIjoibG9naW5fdG9rZW4iLCJpYXQiOjE2NjIxOTQ3NzQsInVzZXJuYW1lIjoibGVvbmFyZF96b3VAMTYzLmNvbSIsInBhc3N3b3JkIjoibGVvbmFyZCIsImV4cCI6MTY2MjE5NTM3NH0.5fQXTvQw79FHLBskPU_mqf17CBTdzz7izeRaELZZU8I' \
  --compressed

#### tool recommend
> https://github.com/zljin/renren-generator
> renren-generator是人人开源项目的代码生成器，可在线生成entity、xml、dao、service、html、js、sql代码，减少70%以上的开发任务



### step 5 Deploy this project to kubenetes
1. add jenkinsfile
2. add k8s yaml

## function
