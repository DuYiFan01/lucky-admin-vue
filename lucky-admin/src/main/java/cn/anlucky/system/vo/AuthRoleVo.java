package cn.anlucky.system.vo;

import cn.anlucky.system.pojo.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 当前用户已经拥有的角色
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRoleVo {
    /**
     * 角色列表
     */
    private List<Roles> roles;
    /**
     * 已经授权的用户id
     */
    private List<Long> userIds;

}
