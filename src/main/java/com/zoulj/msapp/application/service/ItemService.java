package com.zoulj.msapp.application.service;

import com.zoulj.msapp.infrastructure.exception.BusinessException;
import com.zoulj.msapp.interfaces.command.ItemCommand;
import com.zoulj.msapp.interfaces.vo.ItemVO;
import com.zoulj.msapp.interfaces.vo.PageResult;

import java.util.List;

public interface ItemService {

    //创建商品
    ItemVO createItem(ItemCommand itemModel) throws BusinessException;

    //商品列表浏览
    PageResult<ItemVO> listItem(String title, Integer pageNo, Integer pageSize);

    //商品详情浏览
    ItemVO getItemById(Long id);

    //库存扣减
    boolean decreaseStock(Long itemId, Integer amount) throws BusinessException;

    //商品销量增加
    void increaseSales(Long itemId, Integer amount) throws BusinessException;
}
