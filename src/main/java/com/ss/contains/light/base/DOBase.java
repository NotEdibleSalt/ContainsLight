package com.ss.contains.light.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.*;

import java.time.LocalDateTime;


/**
 * @author NotEdibleSalt
 * @version 1.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DOBase {

    @Id
    private String id;

    /**
     * 是否删除
     */
    private boolean del = false;

    /**
     * 创建时间
     */
    @CreatedDate
    private LocalDateTime createAt;

    /**
     * 创建人
     */
    @CreatedBy
    private String createBy;

    /**
     * 更新时间
     */
    @LastModifiedDate
    private LocalDateTime updateAt;

    /**
     * 更新人
     */
    @LastModifiedBy
    private String updateBy;

    public void del() {
        this.setDel(true);
    }

    /**
     * 未删除时返回true
     *
     * @return boolean
     */
    public boolean notDel() {
        return !this.del;
    }

}
