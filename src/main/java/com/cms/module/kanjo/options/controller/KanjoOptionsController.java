package com.cms.module.kanjo.options.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cms.module.kanjo.options.dto.KanjoCategoryRow;
import com.cms.module.kanjo.options.service.KanjoOptionsService;
import com.cms.module.kanjo.options.vo.CascaderNode;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/kanjo/options")
@RequiredArgsConstructor
public class KanjoOptionsController {

    private final KanjoOptionsService service;

    /** 一次性整棵树（小数据量/常用） */
    @GetMapping("/tree")
    public List<CascaderNode> tree(@RequestParam(required = false) Long companyId,
                                   @RequestParam(defaultValue = "ja") String locale) {
        return service.getTree(companyId);
    }

    /** 平铺列表（调试） */
    @GetMapping("/flat")
    public List<KanjoCategoryRow> flat(@RequestParam(required = false) Long companyId) {
        return service.getFlat(companyId);
    }

    /* ===== 懒加载路由（保留，已注释） =====
    @GetMapping("/lazy/root")
    public List<CascaderNode> rootLazy(@RequestParam(required = false) Long companyId,) {
        return service.getRootLazy(companyId);
    }

    @GetMapping("/lazy/children")
    public List<CascaderNode> childrenLazy(@RequestParam(required = false) Long companyId,
                                           @RequestParam String parentCode,) {
        return service.getChildrenLazy(companyId, parentCode);
    }
    ===== end 懒加载 ===== */
}
