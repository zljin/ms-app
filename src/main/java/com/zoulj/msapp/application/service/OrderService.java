package com.zoulj.msapp.application.service;

import com.zoulj.msapp.infrastructure.exception.BusinessException;
import com.zoulj.msapp.interfaces.vo.OrderVO;

public interface OrderService {

    OrderVO createOrder(Integer itemId, Integer promoId, Integer amount) throws BusinessException;

}
