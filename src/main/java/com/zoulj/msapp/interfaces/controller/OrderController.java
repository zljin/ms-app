package com.zoulj.msapp.interfaces.controller;

import com.zoulj.msapp.application.service.OrderService;
import com.zoulj.msapp.infrastructure.exception.BusinessException;
import com.zoulj.msapp.interfaces.vo.CommonReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;


    //封装下单请求
    @PostMapping("/createorder")
    public CommonReturnType createOrder(@RequestParam(name = "itemId") Long itemId,
                                        @RequestParam(name = "amount") Integer amount,
                                        @RequestParam(name = "promoId", required = false) Long promoId) throws BusinessException {
        return CommonReturnType.create(orderService.createOrder(itemId, promoId, amount));
    }


}
