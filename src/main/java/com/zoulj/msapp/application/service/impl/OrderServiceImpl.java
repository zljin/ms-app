package com.zoulj.msapp.application.service.impl;

import com.zoulj.msapp.application.service.OrderService;
import com.zoulj.msapp.infrastructure.exception.BusinessException;
import com.zoulj.msapp.interfaces.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author leonard
 * @date 2022/9/3
 * @Description TODO
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Override
    @Transactional
    public OrderVO createOrder(Integer itemId, Integer promoId, Integer amount) throws BusinessException {
        OrderVO orderVO = new OrderVO();
        //1.通过url上传过来秒杀活动id，然后下单接口内校验对应id是否属于对应商品且活动已开始
        //用户是否登录
        //获取用户信息
        //1.校验下单状态，下单的商品是否存在，用户是否合法，购买数量是否正确
        //校验活动信息
        //(1)校验对应活动是否存在这个适用商品
        //(2)校验活动是否正在进行中
        //2.落单减库存(采用前者)和支付减库存
        //3.订单入库
        //生成交易流水号,即订单号
        //加上商品销量
        //4.返回前端
        return orderVO;
    }
}
