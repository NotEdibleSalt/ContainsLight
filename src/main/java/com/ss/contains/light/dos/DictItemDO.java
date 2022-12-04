package com.ss.contains.light.dos;

import com.ss.contains.light.base.DOBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

/**
 * 字典项DO
 *
 * @author NotEdibleSalt
 * @date 2022-5-2
 */

@Data
@Table("dict_item")
@NoArgsConstructor
@AllArgsConstructor
public class DictItemDO extends DOBase implements Serializable {


    /**
     * 编号
     */


    /**
     * 字典ID
     */
    private String dictId;


    /**
     * 值
     */
    private String value;


    /**
     * 标签
     */
    private String label;


    /**
     * 描述
     */
    private String description;


    /**
     * 排序（升序）
     */
    private Integer sort;


    /**
     * 是否删除
     */


    /**
     * 创建人
     */


    /**
     * 创建时间
     */


    /**
     * 更新人
     */


    /**
     * 更新时间
     */

}
