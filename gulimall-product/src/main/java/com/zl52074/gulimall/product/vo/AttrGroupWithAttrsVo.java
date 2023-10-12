package com.zl52074.gulimall.product.vo;

import com.zl52074.gulimall.product.entity.AttrEntity;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: zl52074
 * @time: 2023/10/11 17:04
 */
@Data
public class AttrGroupWithAttrsVo {
    private Long attrGroupId;
    /**
     * 组名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    private Long catelogId;

    //属性
    List<AttrEntity> attrs;
}
