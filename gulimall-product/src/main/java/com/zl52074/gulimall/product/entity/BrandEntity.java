package com.zl52074.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import com.zl52074.gulimall.common.valid.AddGroup;
import com.zl52074.gulimall.common.valid.ListValue;
import com.zl52074.gulimall.common.valid.UpdateGroup;
import com.zl52074.gulimall.common.valid.UpdateStatusGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

/**
 * 品牌
 *
 * @author zl52074
 * @email
 * @date 2023-10-05 07:25:36
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
	@NotNull(message="id不能为空",groups = {UpdateGroup.class, UpdateStatusGroup.class})
	@Null(message="id必须为空",groups = {AddGroup.class})
	@TableId
	private Long brandId;
	/**
	 * 品牌名
	 */
	@NotBlank(message="名称不能为空",groups = {AddGroup.class, UpdateGroup.class})
	private String name;
	/**
	 * 品牌logo地址
	 */
	@URL(message="logo地址不合法",groups = {AddGroup.class, UpdateGroup.class})
	@NotBlank(message="logo不能为空",groups = {AddGroup.class, UpdateGroup.class})
	private String logo;
	/**
	 * 介绍
	 */
	@NotBlank(message="描述不能为空",groups = {AddGroup.class, UpdateGroup.class})
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	@ListValue(values = {0,1},message="显示状态必须为0或1",groups = {AddGroup.class, UpdateGroup.class, UpdateStatusGroup.class})
	@NotNull(message="是否显示不能为空",groups = {AddGroup.class, UpdateGroup.class, UpdateStatusGroup.class})
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	@Pattern(regexp="^[a-zA-Z]$",message="检索首字母必须为一个字母",groups = {AddGroup.class, UpdateGroup.class})
	@NotBlank(message="检索首字母不能为空",groups = {AddGroup.class, UpdateGroup.class})
	private String firstLetter;
	/**
	 * 排序
	 */
	@NotNull(message="排序不能为空",groups = {AddGroup.class, UpdateGroup.class})
	private Integer sort;

}
