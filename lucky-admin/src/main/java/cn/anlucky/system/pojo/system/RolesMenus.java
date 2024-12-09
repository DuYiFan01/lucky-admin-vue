package cn.anlucky.system.pojo.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * 角色和菜单关联表
 *
 * @author yifan.du
 * @since 2024-12-03 14:44:49
 */
@Data
@TableName("roles_menus")
@ApiModel(value = "RolesMenus对象", description = "角色和菜单关联表")
public class RolesMenus implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色ID")
    @TableId("role_id")
    private Long roleId;

    @ApiModelProperty("菜单ID")
    @TableField("menu_id")
    private Long menuId;
}
