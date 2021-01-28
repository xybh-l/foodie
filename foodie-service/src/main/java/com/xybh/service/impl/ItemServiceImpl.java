package com.xybh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xybh.enums.CommentLevel;
import com.xybh.enums.OrderStatusEnum;
import com.xybh.enums.YesOrNo;
import com.xybh.mapper.*;
import com.xybh.mapper.ext.ItemsExtMapper;
import com.xybh.pojo.*;
import com.xybh.pojo.vo.CommentLevelCountVO;
import com.xybh.pojo.vo.ItemsCommentsVO;
import com.xybh.pojo.vo.SearchItemsVO;
import com.xybh.pojo.vo.ShopcartVO;
import com.xybh.service.ItemService;
import com.xybh.service.impl.center.BaseService;
import com.xybh.utils.DesensitizationUtil;
import com.xybh.utils.PagedGridResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 11:52 2021/1/22
 * @Modified:
 */
@Service
public class ItemServiceImpl extends BaseService implements ItemService {

    @Resource
    private ItemsMapper itemsMapper;
    @Resource
    private ItemsImgMapper itemsImgMapper;
    @Resource
    private ItemsSpecMapper itemsSpecMapper;
    @Resource
    private ItemsParamMapper itemsParamMapper;
    @Resource
    private ItemsCommentsMapper itemsCommentsMapper;
    @Resource
    private ItemsExtMapper itemsExtMapper;

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public Items queryItemById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example example = new Example(ItemsImg.class);
        example.createCriteria()
                .andEqualTo("itemId", itemId);
        return itemsImgMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example example = new Example(ItemsSpec.class);
        example.createCriteria()
                .andEqualTo("itemId", itemId);
        return itemsSpecMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public ItemsParam queryItemParamList(String itemId) {
        Example example = new Example(ItemsParam.class);
        example.createCriteria()
                .andEqualTo("itemId", itemId);
        return itemsParamMapper.selectOneByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public CommentLevelCountVO queryCommentsCounts(String itemId) {
        Integer goodCounts = getCommentCounts(itemId, CommentLevel.GOOD.type);
        Integer normalCounts = getCommentCounts(itemId, CommentLevel.NORMAL.type);
        Integer badCounts = getCommentCounts(itemId, CommentLevel.BAD.type);
        Integer totalCount = goodCounts + normalCounts + badCounts;

        CommentLevelCountVO countsVO = new CommentLevelCountVO();
        countsVO.setTotalCounts(totalCount);
        countsVO.setGoodCounts(goodCounts);
        countsVO.setNormalCounts(normalCounts);
        countsVO.setBadCounts(badCounts);
        return countsVO;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    Integer getCommentCounts(String itemId, Integer level) {
        ItemsComments condition = new ItemsComments();
        condition.setItemId(itemId);
        if (level != null) {
            condition.setCommentLevel(level);
        }
        return itemsCommentsMapper.selectCount(condition);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer size) {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("itemId", itemId);
        map.put("level", level);
        PageHelper.startPage(page, size);
        List<ItemsCommentsVO> list = itemsExtMapper.queryItemComments(map);

        for (ItemsCommentsVO vo : list) {
            if (vo.getAnonymous() == 0) {
                vo.setNickname(DesensitizationUtil.commonDisplay(vo.getNickname()));
            }
        }
        return setterPagedGrid(list, page);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public PagedGridResult searchItems(String keyword, String sort, Integer page, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("keywords", keyword);
        map.put("sort", sort);

        PageHelper.startPage(page, pageSize);
        List<SearchItemsVO> list = itemsExtMapper.searchItems(map);
        return setterPagedGrid(list, page);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public PagedGridResult searchItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("catId", catId);
        map.put("sort", sort);

        PageHelper.startPage(page, pageSize);
        List<SearchItemsVO> list = itemsExtMapper.searchItemsByThirdCat(map);
        return setterPagedGrid(list, page);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public String queryItemMainImgById(String itemId) {
        ItemsImg itemsImg = new ItemsImg();
        itemsImg.setItemId(itemId);
        itemsImg.setIsMain(YesOrNo.YES.type);
        ItemsImg result = itemsImgMapper.selectOne(itemsImg);
        return result != null ? result.getUrl() : "";
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public ItemsSpec queryItemSpecById(String specId) {
        return itemsSpecMapper.selectByPrimaryKey(specId);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public List<ShopcartVO> queryItemsBySpecIds(String specIds) {

        String[] ids = specIds.split(",");
        ArrayList<String> specIdList = new ArrayList<>();
        Collections.addAll(specIdList, ids);

        return itemsExtMapper.queryItemsBySpecIds(specIdList);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void decreaseItemSpecStock(String specId, Integer buyCounts) {
//        // 1.查询库存
//        int stock = 10;
//        // 2.判断库存，是否库存足够
//        if(stock - buyCounts < 0){
//            // 提示用户库存不够
//        }

        int result = itemsExtMapper.decreaseItemSpecStock(specId, buyCounts);
        if (result != 1) {
            throw new RuntimeException("订单创建失败，原因：库存不足");
        }
    }
}
