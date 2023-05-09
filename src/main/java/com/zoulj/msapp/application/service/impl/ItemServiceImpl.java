package com.zoulj.msapp.application.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zoulj.msapp.application.service.ItemService;
import com.zoulj.msapp.domain.model.product.ItemEntity;
import com.zoulj.msapp.domain.model.promote.PromoEntity;
import com.zoulj.msapp.domain.model.resource.ItemStockEntity;
import com.zoulj.msapp.infrastructure.utils.AppConstant;
import com.zoulj.msapp.infrastructure.mapper.ItemMapper;
import com.zoulj.msapp.infrastructure.mapper.ItemStockMapper;
import com.zoulj.msapp.infrastructure.mapper.PromoMapper;
import com.zoulj.msapp.infrastructure.exception.BusinessException;
import com.zoulj.msapp.infrastructure.utils.RedisLock;
import com.zoulj.msapp.infrastructure.utils.SnowFlakeUtil;
import com.zoulj.msapp.interfaces.command.ItemCommand;
import com.zoulj.msapp.interfaces.vo.ItemVO;
import com.zoulj.msapp.interfaces.vo.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    @Resource
    private ItemMapper itemMapper;

    @Resource
    private ItemStockMapper itemStockMapper;

    @Resource
    private PromoMapper promoMapper;

    @Resource
    private RedisTemplate redisTemplate;


    /**
     * 事务传播 - Propagation
     *      REQUIRED: 使用当前的事务，如果当前没有事务，则自己新建一个事务，子方法是必须运行在一个事务中的；
     *                如果当前存在事务，则加入这个事务，成为一个整体。
     *                举例：领导没饭吃，我有钱，我会自己买了自己吃；领导有的吃，会分给你一起吃。
     *      SUPPORTS: 如果当前有事务，则使用事务；如果当前没有事务，则不使用事务。
     *                举例：领导没饭吃，我也没饭吃；领导有饭吃，我也有饭吃。
     *      MANDATORY: 该传播属性强制必须存在一个事务，如果不存在，则抛出异常
     *                 举例：领导必须管饭，不管饭没饭吃，我就不乐意了，就不干了（抛出异常）
     *      REQUIRES_NEW: 如果当前有事务，则挂起该事务，并且自己创建一个新的事务给自己使用；
     *                    如果当前没有事务，则同 REQUIRED
     *                    举例：领导有饭吃，我偏不要，我自己买了自己吃
     *      NOT_SUPPORTED: 如果当前有事务，则把事务挂起，自己不适用事务去运行数据库操作
     *                     举例：领导有饭吃，分一点给你，我太忙了，放一边，我不吃
     *      NEVER: 如果当前有事务存在，则抛出异常
     *             举例：领导有饭给你吃，我不想吃，我热爱工作，我抛出异常
     *      NESTED: 如果当前有事务，则开启子事务（嵌套事务），嵌套事务是独立提交或者回滚；
     *              如果当前没有事务，则同 REQUIRED。
     *              但是如果主事务提交，则会携带子事务一起提交。
     *              如果主事务回滚，则子事务会一起回滚。相反，子事务异常，则父事务可以回滚或不回滚。
     *              举例：领导决策不对，老板怪罪，领导带着小弟一同受罪。小弟出了差错，领导可以推卸责任。
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
    public ItemVO createItem(ItemCommand itemCommand) throws BusinessException {

        ItemEntity itemEntity = convert2ItemEntity(itemCommand);
        if (null != itemEntity) {
            itemEntity.setId(SnowFlakeUtil.getInstance().nextId());
            itemMapper.insert(itemEntity);
        }

        ItemStockEntity itemStockEntity = convert2ItemStockEntity(itemCommand, itemEntity);
        itemStockMapper.insert(itemStockEntity);

        ItemCommand.Promo promo = itemCommand.getPromo();
        if (promo.getStatus() != 0
                && StrUtil.isNotEmpty(promo.getPromoName())
                && promo.getPromoItemPrice() != null
                && promo.getStartDate() != null
                && promo.getEndDate() != null) {
            promo.setId(SnowFlakeUtil.getInstance().nextId());
            PromoEntity promoEntity = new PromoEntity();
            BeanUtils.copyProperties(promo, promoEntity);
            promoEntity.setItemId(itemEntity.getId());
            promoMapper.insert(promoEntity);
        }

//        if(true){//test Transactional
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
//        }

        return getItemById(itemEntity.getId());
    }

    @Override
    public PageResult<ItemVO> listItem(String title, Integer pageCurrent, Integer pageSize) {
        PageResult<ItemVO> pageResult = new PageResult<>();
        QueryWrapper<ItemEntity> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotEmpty(title)) {
            queryWrapper.like("title", title);
        }

        IPage<ItemEntity> page = new Page<>(pageCurrent, pageSize);
        IPage<ItemEntity> mapIPage = itemMapper.selectPage(page, queryWrapper);
        List<ItemEntity> records = mapIPage.getRecords();

        if (CollectionUtil.isNotEmpty(records)) {
            List<ItemVO> itemVOS = records.stream()
                    .map(itemEntity -> getItemById(itemEntity.getId()))
                    .collect(Collectors.toList());
            pageResult.setData(itemVOS);
        }
        pageResult.setPageCurrent(mapIPage.getCurrent());
        pageResult.setPages(mapIPage.getPages());
        pageResult.setTotal(mapIPage.getTotal());
        pageResult.setStatus("success");
        return pageResult;
    }

    @Override
    public ItemVO getItemById(Long id) {
        ItemEntity itemEntity = itemMapper.selectById(id);
        QueryWrapper<ItemStockEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("item_id", id);
        ItemStockEntity itemStockEntity = itemStockMapper.selectOne(queryWrapper);
        QueryWrapper<PromoEntity> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("item_id", id);
        PromoEntity promoEntity = promoMapper.selectOne(queryWrapper2);
        return convert2ItemVO(itemEntity, itemStockEntity, promoEntity);
    }


    @Transactional
    @Override
    public void decreaseStock(Long itemId, Integer amount) throws BusinessException {

        try (RedisLock redisLock = new RedisLock(redisTemplate, "product:" + itemId, 30)) {
            if (redisLock.getLock()) {
                itemStockMapper.decreaseStock(itemId,amount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Override
    public void increaseSales(Long itemId, Integer amount) throws BusinessException {
        try (RedisLock redisLock = new RedisLock(redisTemplate, "product:" + itemId, 30)) {
            if (redisLock.getLock()) {
                itemMapper.increaseSales(itemId,amount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ItemEntity convert2ItemEntity(ItemCommand itemCommand) {
        if (itemCommand == null) {
            return null;
        }
        ItemEntity entity = new ItemEntity();
        BeanUtils.copyProperties(itemCommand, entity);
        entity.setPrice(itemCommand.getPrice());
        return entity;
    }

    private ItemStockEntity convert2ItemStockEntity(ItemCommand itemCommand, ItemEntity itemEntity) {
        if (itemCommand == null || itemEntity == null) {
            return null;
        }
        ItemStockEntity entity = new ItemStockEntity();
        entity.setId(SnowFlakeUtil.getInstance().nextId());
        entity.setItemId(itemEntity.getId());
        entity.setStock(itemCommand.getStock());
        return entity;
    }

    private ItemVO convert2ItemVO(ItemEntity itemEntity, ItemStockEntity itemStockEntity, PromoEntity promoEntity) {
        ItemVO itemVO = new ItemVO();
        if (itemEntity != null) {
            BeanUtils.copyProperties(itemEntity, itemVO);
            //tip: Long 类型的雪花算法序列会超长,再给个String类型的idStr代表item_id
            itemVO.setIdStr(String.valueOf(itemEntity.getId()));
        }
        if (itemStockEntity != null) {
            itemVO.setStock(itemStockEntity.getStock());
        }
        if (promoEntity != null) {
            itemVO.setStartDate(DateUtil.format(promoEntity.getStartDate(), "yyyy-MM-dd HH:mm:ss"));
            itemVO.setEndDate(DateUtil.format(promoEntity.getEndDate(), "yyyy-MM-dd HH:mm:ss"));
            itemVO.setPromoId(String.valueOf(promoEntity.getId()));
            itemVO.setPromoName(promoEntity.getPromoName());
            itemVO.setPromoItemPrice(promoEntity.getPromoItemPrice());
            itemVO.setPromoStatus(getPromoStatus(promoEntity));
        } else {
            itemVO.setPromoStatus(AppConstant.PROMOTE_ZERO);
        }
        return itemVO;
    }

    private int getPromoStatus(PromoEntity promoEntity) {
        long now = new Date().getTime() / 1000;
        long start = promoEntity.getStartDate().getTime() / 1000;
        long end = promoEntity.getEndDate().getTime() / 1000;
        if (now < start) {
            return AppConstant.PROMOTE_WAIT;
        } else if (now > end) {
            return AppConstant.PROMOTE_END;
        }
        return AppConstant.PROMOTE_PROCESS;
    }
}
