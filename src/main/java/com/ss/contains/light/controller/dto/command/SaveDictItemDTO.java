package com.ss.contains.light.controller.dto.command;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 保存字典项DTO
 *
 * @version 1.0
 * @date 2022-5-3
 */
@Data
public class SaveDictItemDTO {


    /**
     * 值
     */
    @NotBlank(message = "值不能为空")
    private String value;


    /**
     * 标签
     */
    @NotBlank(message = "标签不能为空")
    private String label;


    /**
     * 描述
     */
    private String description;


    /**
     * 排序（升序）
     */
    private Integer sort = 0;
}
