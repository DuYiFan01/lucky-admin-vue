package cn.anlucky.system.pojo.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 *
 * 用户角色关联表
 *
 * @author yifan.du
 * @since 2024-12-03 14:39:34
 */
@Data
@TableName("user_roles")
@Schema(name = "UserRoles", description = "用户角色关联表")
public class UserRoles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    @TableId("user_id")
    private Long userId;

    @Schema(description = "角色ID")
    @TableField("role_id")
    private Long roleId;
}
