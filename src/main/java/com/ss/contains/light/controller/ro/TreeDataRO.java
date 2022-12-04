package com.ss.contains.light.controller.ro;

import cn.hutool.core.util.StrUtil;
import com.ss.contains.light.common.exception.Ex;
import lombok.Data;

import java.util.List;

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

    public TreeDataRO(String key, String title, List<TreeDataRO> children){
        if (StrUtil.isBlank(key) || StrUtil.isBlank(title)){
            throw new Ex("key 和 title 不能为空");
        }
        this.key = key;
        this.title = title;
        this.children = children;
    }

    private String key;

    private String title;

    private List<TreeDataRO> children;

}
