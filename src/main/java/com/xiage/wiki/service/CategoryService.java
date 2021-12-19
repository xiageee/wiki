package com.xiage.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiage.wiki.domain.Category;
import com.xiage.wiki.domain.CategoryExample;
import com.xiage.wiki.mapper.CategoryMapper;
import com.xiage.wiki.req.CategoryQueryReq;
import com.xiage.wiki.req.CategorySaveReq;
import com.xiage.wiki.resp.CategoryQueryResp;
import com.xiage.wiki.resp.PageResp;
import com.xiage.wiki.util.CopyUtil;
import com.xiage.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<CategoryQueryResp> list(CategoryQueryReq req) {
        /** 相当于创建where条件 */
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        LOG.info("总行数: {}", pageInfo.getTotal());
        LOG.info("总页数: {}", pageInfo.getPages());


        /** 遍历categoryList,将结果转成List<CategoryResp> */
        //List<CategoryResp> respList = new ArrayList<>();
        //for (Category category : categoryList) {
        //    //CategoryResp categoryResp = new CategoryResp();
        //    //BeanUtils.copyProperties(category,categoryResp);
        //    //对象复制
        //    CategoryResp categoryResp = CopyUtil.copy(category, CategoryResp.class);
        //    respList.add(categoryResp);
        //}

        //列表复制
        List<CategoryQueryResp> list = CopyUtil.copyList(categoryList, CategoryQueryResp.class);
        PageResp<CategoryQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);

        return pageResp;
    }

    /** 保存 */
    public void save(CategorySaveReq req) {
        Category category = CopyUtil.copy(req, Category.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            /** 新增 ,雪花算法生成Id*/
            category.setId(snowFlake.nextId());
            categoryMapper.insert(category);
        } else {
            /** 更新 */
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    /** 删除 */
    public void delete(Long id){
        categoryMapper.deleteByPrimaryKey(id);
    }
}
