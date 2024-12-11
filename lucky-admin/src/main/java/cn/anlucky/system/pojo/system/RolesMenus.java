package cn.anlucky.system.pojo.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "RolesMenus", description = "角色和菜单关联表")
public class RolesMenus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "角色ID")
    @TableId("role_id")
    private Long roleId;

    @Schema(description = "菜单ID")
    @TableField("menu_id")
    private Long menuId;
}
