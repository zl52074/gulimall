package com.zl52074.gulimall.ware.vo;

import lombok.Data;

import java.util.List;



@Data
public class MergeVo {

    private Long purchaseId;

    /*
    采购需求id
     */
    private List<Long> items;

}
