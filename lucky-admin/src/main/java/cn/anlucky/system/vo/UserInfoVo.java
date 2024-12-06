package cn.anlucky.system.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVo {
    /**
     * UID
     */
    private String id;
    /**
     * UserName
     */
    private String username;
    /**
     * 角色列表
     */
    private List<String> roles;
    /**
     * 可访问路由列表
     */
    private List<RouterVo> routers;
    /**
     * 权限列表
     */
    private List<String> permissions;

}
