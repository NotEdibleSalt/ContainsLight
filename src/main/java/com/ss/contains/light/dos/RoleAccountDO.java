package com.ss.contains.light.dos;

import com.ss.contains.light.base.DOBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

/**
 * 角色、账号关联表DO
 *
 * @author NotEdibleSalt
 */

@Data
@Table("role_account")
@NoArgsConstructor
@AllArgsConstructor
public class RoleAccountDO extends DOBase implements Serializable {

    /**
     * 账号id
     */
    private String accountId;


    /**
     * 角色id
     */
    private String roleId;

}
