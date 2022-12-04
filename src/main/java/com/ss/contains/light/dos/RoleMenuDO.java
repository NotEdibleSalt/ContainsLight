package com.ss.contains.light.dos;

import com.ss.contains.light.base.DOBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

/**
 * 角色、菜单关联表DO
 *
 * @author NotEdibleSalt
 */

@Data
@Table("role_menu")
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenuDO extends DOBase implements Serializable {


    /**
     * 主键
     */


    /**
     * 角色id
     */
    private String roleId;


    /**
     * 菜单id
     */
    private String menuId;


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
