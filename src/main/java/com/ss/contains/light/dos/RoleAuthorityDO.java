package com.ss.contains.light.dos;

import com.ss.contains.light.base.DOBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

/**
 * 角色、权限关联表DO
 *
 * @author NotEdibleSalt
 */

@Data
@Table("role_authority")
@NoArgsConstructor
@AllArgsConstructor
public class RoleAuthorityDO extends DOBase implements Serializable {

    /**
     * 角色id
     */
    private String roleId;


    /**
     * 权限id
     */
    private String authorityId;

}
