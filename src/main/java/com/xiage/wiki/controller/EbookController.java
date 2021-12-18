package com.xiage.wiki.controller;

import com.xiage.wiki.req.EbookQueryReq;
import com.xiage.wiki.req.EbookSaveReq;
import com.xiage.wiki.resp.CommonResp;
import com.xiage.wiki.resp.EbookQueryResp;
import com.xiage.wiki.resp.PageResp;
import com.xiage.wiki.service.EbookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(EbookQueryReq req) {
        CommonResp<PageResp<EbookQueryResp>> resp = new CommonResp<>();
        PageResp<EbookQueryResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@RequestBody EbookSaveReq req) {
        CommonResp resp = new CommonResp<>();
        ebookService.save(req);
        return resp;
    }
}
