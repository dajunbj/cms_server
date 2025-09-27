package com.cms.module.kanjo.options.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cms.module.kanjo.options.dto.KanjoCategoryRow;
import com.cms.module.kanjo.options.mapper.KanjoOptionsMapper;
import com.cms.module.kanjo.options.vo.CascaderNode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KanjoOptionsService {

    private final KanjoOptionsMapper mapper;

    /** 一次性返回整棵树（当前启用） */
    public List<CascaderNode> getTree(Long companyId) {
        // 若无视图可改为：mapper.selectEffectiveByCompanyInline(companyId)
        List<KanjoCategoryRow> rows = mapper.selectEffectiveByCompany(companyId);

        Map<String, CascaderNode> byCode = new LinkedHashMap<>();
        for (KanjoCategoryRow r : rows) {
            CascaderNode n = byCode.computeIfAbsent(r.getCode(), k -> new CascaderNode());
            n.setCode(r.getCode());
            n.setParentCode(r.getParentCode());
            n.setSortNo(Optional.ofNullable(r.getSortNo()).orElse(1000));
            String label = r.getNameJa();
            n.setLabel(label);
            n.setValue(r.getCode());
            n.setIsLeaf(null); // 非懒加载模式，不用标注
        }

        List<CascaderNode> roots = new ArrayList<>();
        for (CascaderNode n : byCode.values()) {
            if (!StringUtils.hasText(n.getParentCode())) {
                roots.add(n);
            } else {
                CascaderNode p = byCode.get(n.getParentCode());
                if (p != null) p.getChildren().add(n);
                else roots.add(n); // 容错：父缺失则提升为根
            }
        }

        sortRecursively(roots);
        return roots;
    }

    /** 平铺（调试/导出用） */
    public List<KanjoCategoryRow> getFlat(Long companyId) {
        return mapper.selectEffectiveByCompany(companyId);
    }

    private void sortRecursively(List<CascaderNode> list) {
        if (list == null) return;
        list.sort(Comparator
                .comparing(CascaderNode::getSortNo, Comparator.nullsLast(Integer::compareTo))
                .thenComparing(CascaderNode::getLabel, Comparator.nullsLast(String::compareTo)));
        for (CascaderNode n : list) {
            sortRecursively(n.getChildren());
        }
    }


    /* ===== 懒加载（保留原型，已注释） =====
    public List<CascaderNode> getRootLazy(Long companyId, String locale) {
        List<KanjoCategoryRow> rows = mapper.selectRoots(companyId);
        return toLazyNodes(rows, companyId, locale);
    }

    public List<CascaderNode> getChildrenLazy(Long companyId, String parentCode, String locale) {
        List<KanjoCategoryRow> rows = mapper.selectChildrenByParentCode(companyId, parentCode);
        return toLazyNodes(rows, companyId, locale);
    }

    private List<CascaderNode> toLazyNodes(List<KanjoCategoryRow> rows, Long companyId, String locale) {
        List<CascaderNode> list = new ArrayList<>();
        for (KanjoCategoryRow r : rows) {
            CascaderNode n = new CascaderNode();
            n.setCode(r.getCode());
            n.setParentCode(r.getParentCode());
            n.setSortNo(Optional.ofNullable(r.getSortNo()).orElse(1000));
            String label = r.getNameJa();
            n.setLabel(label);
            n.setValue(r.getCode());
            boolean hasChildren = mapper.existsChildren(companyId, r.getCode()) != null
                    && mapper.existsChildren(companyId, r.getCode()) > 0;
            n.setIsLeaf(!hasChildren);
            list.add(n);
        }
        list.sort(Comparator.comparing(CascaderNode::getSortNo)
                .thenComparing(CascaderNode::getLabel, Comparator.nullsLast(String::compareTo)));
        return list;
    }
    ===== end 懒加载 ===== */
}
