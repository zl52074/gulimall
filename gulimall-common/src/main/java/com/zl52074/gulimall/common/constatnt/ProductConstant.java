package com.zl52074.gulimall.common.constatnt;

/**
 * @description:
 * @author: zl52074
 * @time: 2023/10/11 9:11
 */
public class ProductConstant {
    public enum AttrEnum {
        ATTR_TYPE_BASE(1,"基本属性"),
        ATTR_TYPE_SALE(0,"销售属性");

        private int code;

        private String msg;

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        AttrEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

    }
}
