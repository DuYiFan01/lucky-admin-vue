package cn.anlucky.system.pojo.system;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "Menus", description = "菜单表")
public class Menus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "路由名称")
    @TableField("`name`")
    private String name;

    @Schema(description = "菜单名称")
    @TableField("title")
    private String title;

    @Schema(description = "父菜单ID")
    @TableField("parent_id")
    private Long parentId;

    @Schema(description = "显示顺序")
    @TableField("order_num")
    private Integer orderNum;

    @Schema(description = "路由地址")
    @TableField("`path`")
    private String path;

    @Schema(description = "组件路径")
    @TableField("`component`")
    private String component;

    @Schema(description = "路由参数")
    @TableField("`query`")
    private String query;

    @Schema(description = "是否为外链（1是 0否）")
    @TableField("is_frame")
    private Integer isFrame;

    @Schema(description = "是否缓存（1缓存 0不缓存）")
    @TableField("is_cache")
    private Integer isCache;

    @Schema(description = "菜单类型（M目录 C菜单 F按钮）")
    @TableField("menu_type")
    private String menuType;

    @Schema(description = "菜单状态（1显示 0隐藏）")
    @TableField("`visible`")
    private Integer visible;

    @Schema(description = "权限标识")
    @TableField("roles")
    private String roles;

    @Schema(description = "菜单图标")
    @TableField("icon")
    private String icon;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "创建人")
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "更新人")
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;

    @Schema(description = "用户ID")
    @TableField(exist = false)
    private String uid;

    @Schema(description = "子菜单")
    @TableField(exist = false)
    private List<Menus> children;
}
