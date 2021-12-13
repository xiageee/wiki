package com.xiage.wiki.service;

import com.xiage.wiki.domain.Ebook;
import com.xiage.wiki.domain.EbookExample;
import com.xiage.wiki.mapper.EbookMapper;
import com.xiage.wiki.req.EbookReq;
import com.xiage.wiki.resp.EbookResp;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
        List<EbookResp> respList = new ArrayList<>();
        for (Ebook ebook : ebookList) {
            EbookResp ebookResp = new EbookResp();
            BeanUtils.copyProperties(ebook,ebookResp);
            respList.add(ebookResp);
        }

        return respList;
    }
}
