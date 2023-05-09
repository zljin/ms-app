package com.zoulj.msapp.application.service.impl;

import cn.hutool.core.util.StrUtil;
import com.zoulj.msapp.application.service.ItemService;
import com.zoulj.msapp.application.service.OrderService;
import com.zoulj.msapp.application.service.UserService;
import com.zoulj.msapp.domain.model.order.OrderInfoEntity;
import com.zoulj.msapp.domain.model.user.UserInfoEntity;
import com.zoulj.msapp.infrastructure.utils.AppConstant;
import com.zoulj.msapp.infrastructure.config.ClientInfoHolder;
import com.zoulj.msapp.infrastructure.mapper.OrderInfoMapper;
import com.zoulj.msapp.infrastructure.exception.BusinessException;
import com.zoulj.msapp.infrastructure.exception.EmBusinessError;
import com.zoulj.msapp.interfaces.vo.ItemVO;
import com.zoulj.msapp.interfaces.vo.OrderVO;
import com.zoulj.msapp.interfaces.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author leonard
 * @date 2022/9/3
 * @Description 订单活动秒杀
 * 通过url上传过来秒杀活动id，然后下单接口内校验对应id是否属于对应商品且活动已开始
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @Override
    @Transactional
    public OrderVO createOrder(Long itemId, Long promoId, Integer amount) throws BusinessException {

        //用户是否登录,并获取用户信息
        UserInfoEntity userInfoEntity = checkUserState();
        ItemVO itemVO = itemService.getItemById(itemId);
        checkParameters(userInfoEntity.getId(), itemVO, promoId, amount);

        //2.落单减库存(采用前者)和支付减库存
        itemService.decreaseStock(itemId,amount);

        //3.订单入库
        OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
        orderInfoEntity.setUserId(userInfoEntity.getId());
        orderInfoEntity.setItemId(itemId);
        orderInfoEntity.setAmount(amount);
        orderInfoEntity.setPromoId(promoId);
        orderInfoEntity.setItemPrice((promoId != null ? itemVO.getPromoItemPrice() : itemVO.getPrice()));
        orderInfoEntity.setOrderPrice(orderInfoEntity.getItemPrice().multiply(new BigDecimal(amount)));
        orderInfoEntity.setId(generateOrderId());
        orderInfoMapper.insert(orderInfoEntity);

        //加上商品销量
        itemService.increaseSales(itemId,amount);

        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(orderInfoEntity,orderVO);
        return orderVO;
    }



    private UserInfoEntity checkUserState() throws BusinessException {
        UserInfoEntity clientInfo = ClientInfoHolder.getClientInfo();
        if (clientInfo == null) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        }
        return clientInfo;
    }

    /**
     * @param userId
     * @param itemId
     * @param promoId
     * @param amount
     * @throws BusinessException http://localhost:8090/item/get?id=763404919783817216
     *                           {
     *                           "status": "success",
     *                           "data": {
     *                           "id": 763404919783817200,
     *                           "title": "联想拯救者Y700",
     *                           "price": 2599.2,
     *                           "stock": 1000,
     *                           "description": "联想拯救者Y700 8.8英寸游戏平板 骁龙870 2.5k 120Hz 100%DCI-P3色域 游戏视野模式 双X轴线性马达 12G+256G",
     *                           "sales": 1000,
     *                           "imgUrl": "https://img13.360buyimg.com/n1/s450x450_jfs/t1/120483/2/30805/76004/630ddc3cE21c1e940/ac5321959771f718.jpg.avif",
     *                           "promo": {
     *                           "id": null,
     *                           "status": 0,
     *                           "promoName": null,
     *                           "startDate": null,
     *                           "endDate": null,
     *                           "itemId": null,
     *                           "promoItemPrice": null
     *                           },
     *                           "idStr": "763404919783817216",
     *                           "startDate": "1000-01-01 00:00:00",
     *                           "promoId": "763404919825760256",
     *                           "promoItemPrice": 2299.1,
     *                           "promoStatus": 3
     *                           }
     *                           }
     */
    private void checkParameters(String userId, ItemVO itemVO, Long promoId, Integer amount) throws BusinessException {
        //下单的商品是否存在，用户是否合法，购买数量是否正确
        if (itemVO == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "商品信息不存在");
        }
        UserVo userVo = userService.getUserById(userId);
        if (userVo == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "用户信息不存在");
        }
        if (amount <= 0 || amount > 99) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "数量信息不存在");
        }
        //校验活动信息
        if (promoId != null) {
            if (!StrUtil.equals(String.valueOf(promoId), itemVO.getPromoId())) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "活动信息不正确");
            } else if (itemVO.getPromoStatus() != AppConstant.PROMOTE_PROCESS) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "活动信息未开始");
            }
        }
    }

    private String generateOrderId() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }
}
