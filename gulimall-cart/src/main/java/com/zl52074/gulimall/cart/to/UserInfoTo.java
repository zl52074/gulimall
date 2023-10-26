package com.zl52074.gulimall.cart.to;

import lombok.Data;



@Data
public class UserInfoTo {


    private Long userId;

    /**
     * 临时用户key
     */
    private String userKey;

    /**
     * 是否有临时用户key
     */
    private Boolean hasTempUser = false;

}
