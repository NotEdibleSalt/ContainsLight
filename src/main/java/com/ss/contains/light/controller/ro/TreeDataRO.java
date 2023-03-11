package com.ss.contains.light.controller.ro;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.ss.contains.light.common.exception.Ex;
import com.ss.contains.light.dos.MenuDO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 树结构
 *
 * @author NotEdibleSalt
 */
@Data
public class TreeDataRO {

    public TreeDataRO(String key, String title){
        if (StrUtil.isBlank(key) || StrUtil.isBlank(title)){
            throw new Ex("key 和 title 不能为空");
        }
        this.key = key;
        this.title = title;
    }

    public TreeDataRO(MenuDO menuDO){
        if (Objects.isNull(menuDO)){
            throw new Ex("暂无该菜单");
        }
        this.key = menuDO.getId();
        this.title = menuDO.getName();
        this.value = menuDO.getId();
        this.routePath = menuDO.getRoutePath();
    }

    private String key;

    private String title;

    private String value;

    private String routePath;

    private List<TreeDataRO> children;

    public static List<TreeDataRO> from(List<MenuDO> menuDOList){

        Map<String, List<MenuDO>> menuDOGroup = menuDOList.stream()
                .filter(menuDO -> StrUtil.isNotBlank(menuDO.getParentId()))
                .collect(Collectors.groupingBy(MenuDO::getParentId));

        return menuDOList
                .stream()
                .filter(menuDO -> !menuDOGroup.containsKey(menuDO.getParentId()))
                .sorted(Comparator.comparing(MenuDO::getSortNumber))
                .map(TreeDataRO::new)
                .map(treeDataRO -> spanningTree(treeDataRO, menuDOGroup))
                .collect(Collectors.toList());

    }

    public static TreeDataRO spanningTree(TreeDataRO treeDataRO, Map<String, List<MenuDO>> menuDOGroup){

        List<MenuDO> menuDOList = menuDOGroup.get(treeDataRO.getKey());
        if (ObjectUtil.isEmpty(menuDOList)){
            return treeDataRO;
        }

        List<TreeDataRO> treeDataROS = menuDOList
                .stream()
                .sorted(Comparator.comparing(MenuDO::getSortNumber))
                .map(TreeDataRO::new)
                .map(menuDO -> spanningTree(menuDO, menuDOGroup))
                .toList();

        treeDataRO.setChildren(treeDataROS);

        return treeDataRO;
    }

}
