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
import com.zoulj.msapp.infrastructure.common.Constant;
import com.zoulj.msapp.infrastructure.db.dao.ItemDao;
import com.zoulj.msapp.infrastructure.db.dao.ItemStockDao;
import com.zoulj.msapp.infrastructure.db.dao.PromoDao;
import com.zoulj.msapp.infrastructure.exception.BusinessException;
import com.zoulj.msapp.infrastructure.exception.EmBusinessError;
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
    private ItemDao itemDao;

    @Resource
    private ItemStockDao itemStockDao;

    @Resource
    private PromoDao promoDao;

    @Resource
    private RedisTemplate redisTemplate;


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
    public ItemVO createItem(ItemCommand itemCommand) throws BusinessException {

        ItemEntity itemEntity = convert2ItemEntity(itemCommand);
        if (null != itemEntity) {
            itemEntity.setId(SnowFlakeUtil.getInstance().nextId());
            itemDao.insert(itemEntity);
        }

        ItemStockEntity itemStockEntity = convert2ItemStockEntity(itemCommand, itemEntity);
        itemStockDao.insert(itemStockEntity);

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
            promoDao.insert(promoEntity);
        }

//        if(true){//test Transactional
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
//        }

        return getItemById(itemEntity.getId());
    }

    @Override
    public PageResult<ItemVO> listItem(String title, Integer pageNo, Integer pageSize) {
        PageResult<ItemVO> pageResult = new PageResult<>();
        QueryWrapper<ItemEntity> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotEmpty(title)) {
            queryWrapper.like("title", title);
        }

        IPage<ItemEntity> page = new Page<>(pageNo, pageSize);
        IPage<ItemEntity> mapIPage = itemDao.selectPage(page, queryWrapper);
        List<ItemEntity> records = mapIPage.getRecords();

        if (CollectionUtil.isNotEmpty(records)) {
            List<ItemVO> itemVOS = records.stream()
                    .map(itemEntity -> getItemById(itemEntity.getId()))
                    .collect(Collectors.toList());
            pageResult.setList(itemVOS);
        }
        pageResult.setPageNo(mapIPage.getCurrent());
        pageResult.setPageSize(mapIPage.getPages());
        pageResult.setTotal(mapIPage.getTotal());
        return pageResult;
    }

    @Override
    public ItemVO getItemById(Long id) {
        ItemEntity itemEntity = itemDao.selectById(id);
        QueryWrapper<ItemStockEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("item_id", id);
        ItemStockEntity itemStockEntity = itemStockDao.selectOne(queryWrapper);
        QueryWrapper<PromoEntity> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("item_id", id);
        PromoEntity promoEntity = promoDao.selectOne(queryWrapper2);
        return convert2ItemVO(itemEntity, itemStockEntity, promoEntity);
    }


    @Transactional
    @Override
    public void decreaseStock(Long itemId, Integer amount) throws BusinessException {

        try (RedisLock redisLock = new RedisLock(redisTemplate, "product:" + itemId, 30)) {
            if (redisLock.getLock()) {
                itemStockDao.decreaseStock(itemId,amount);
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
                itemDao.increaseSales(itemId,amount);
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
            itemVO.setPromoId(String.valueOf(promoEntity.getId()));
            itemVO.setPromoItemPrice(promoEntity.getPromoItemPrice());
            itemVO.setPromoStatus(getPromoStatus(promoEntity));
        } else {
            itemVO.setPromoStatus(Constant.PROMOTE_ZERO);
        }
        return itemVO;
    }

    private int getPromoStatus(PromoEntity promoEntity) {
        long now = new Date().getTime() / 1000;
        long start = promoEntity.getStartDate().getTime() / 1000;
        long end = promoEntity.getEndDate().getTime() / 1000;
        if (now < start) {
            return Constant.PROMOTE_WAIT;
        } else if (now > end) {
            return Constant.PROMOTE_END;
        }
        return Constant.PROMOTE_PROCESS;
    }
}
