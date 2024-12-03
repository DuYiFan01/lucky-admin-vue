package cn.anlucky.system.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "UserRoles对象", description = "用户角色关联表")
public class UserRoles implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    @TableId("user_id")
    private Long userId;

    @ApiModelProperty("角色ID")
    @TableField("role_id")
    private Long roleId;
}
