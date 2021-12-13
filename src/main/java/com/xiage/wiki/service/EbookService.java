package com.xiage.wiki.service;

import com.xiage.wiki.domain.Ebook;
import com.xiage.wiki.domain.EbookExample;
import com.xiage.wiki.mapper.EbookMapper;
import com.xiage.wiki.req.EbookReq;
import com.xiage.wiki.resp.EbookResp;
import com.xiage.wiki.util.CopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {

    @Resource
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq req){
        /** 相当于创建where条件 */
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        criteria.andNameLike("%" + req.getName() + "%");
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

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
        List<EbookResp> list = CopyUtil.copyList(ebookList, EbookResp.class);

        return list;
    }
}
