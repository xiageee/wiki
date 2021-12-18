package com.xiage.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiage.wiki.domain.Ebook;
import com.xiage.wiki.domain.EbookExample;
import com.xiage.wiki.mapper.EbookMapper;
import com.xiage.wiki.req.EbookQueryReq;
import com.xiage.wiki.req.EbookSaveReq;
import com.xiage.wiki.resp.EbookQueryResp;
import com.xiage.wiki.resp.PageResp;
import com.xiage.wiki.util.CopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {

    private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);

    @Resource
    private EbookMapper ebookMapper;

    public PageResp<EbookQueryResp> list(EbookQueryReq req) {
        /** 相当于创建where条件 */
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        PageInfo<Ebook> pageInfo = new PageInfo<>(ebookList);
        LOG.info("总行数: {}", pageInfo.getTotal());
        LOG.info("总页数: {}", pageInfo.getPages());


        /** 遍历ebookList,将结果转成List<EbookResp> */
        //List<EbookResp> respList = new ArrayList<>();
        //for (Ebook ebook : ebookList) {
        //    //EbookResp ebookResp = new EbookResp();
        //    //BeanUtils.copyProperties(ebook,ebookResp);
        //    //对象复制
        //    EbookResp ebookResp = CopyUtil.copy(ebook, EbookResp.class);
        //    respList.add(ebookResp);
        //}

        //列表复制
        List<EbookQueryResp> list = CopyUtil.copyList(ebookList, EbookQueryResp.class);
        PageResp<EbookQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);

        return pageResp;
    }

    /** 保存 */
    public void save(EbookSaveReq req) {
        Ebook ebook = CopyUtil.copy(req, Ebook.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            /** 新增 */
            ebookMapper.insert(ebook);
        } else {
            /** 更新 */
            ebookMapper.updateByPrimaryKey(ebook);
        }
    }
}
