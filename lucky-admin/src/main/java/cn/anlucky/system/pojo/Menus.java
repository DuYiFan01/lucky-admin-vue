package cn.anlucky.system.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * 菜单表
 *
 * @author yifan.du
 * @since 2024-12-03 14:43:01
 */
@Data
@TableName("menus")
@ApiModel(value = "Menus对象", description = "菜单表")
public class Menus implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("菜单ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("路由名称")
    @TableField("`name`")
    private String name;

    @ApiModelProperty("菜单名称")
    @TableField("title")
    private String title;

    @ApiModelProperty("父菜单ID")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty("显示顺序")
    @TableField("order_num")
    private Integer orderNum;

    @ApiModelProperty("路由地址")
    @TableField("`path`")
    private String path;

    @ApiModelProperty("组件路径")
    @TableField("`component`")
    private String component;

    @ApiModelProperty("路由参数")
    @TableField("`query`")
    private String query;

    @ApiModelProperty("是否为外链（1是 0否）")
    @TableField("is_frame")
    private Integer isFrame;

    @ApiModelProperty("是否缓存（1缓存 0不缓存）")
    @TableField("is_cache")
    private Integer isCache;

    @ApiModelProperty("菜单类型（M目录 C菜单 F按钮）")
    @TableField("menu_type")
    private String menuType;

    @ApiModelProperty("菜单状态（1显示 0隐藏）")
    @TableField("`visible`")
    private Integer visible;

    @ApiModelProperty("权限标识")
    @TableField("roles")
    private String roles;

    @ApiModelProperty("菜单图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("创建人")
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("更新人")
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;
}
