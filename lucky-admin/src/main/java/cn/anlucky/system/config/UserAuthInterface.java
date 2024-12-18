package cn.anlucky.system.config;


import cn.anlucky.system.mapper.UsersMapper;
import cn.anlucky.system.pojo.system.Roles;
import cn.anlucky.system.service.system.LoginService;
import cn.anlucky.system.service.system.UsersService;
import cn.dev33.satoken.stp.StpInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class UserAuthInterface implements StpInterface {

    private final LoginService loginService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {

        List<Roles> roleList = loginService.getRolesByLoginId(loginId.toString());
        ArrayList<String> permissionList = new ArrayList<>();
        roleList.stream().forEach(role -> {
            List<String> permissions = loginService.getPermissionsByRoleId(role.getId());
            permissionList.addAll(permissions);
        });
        return permissionList;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<Roles> roleList = loginService.getRolesByLoginId(loginId.toString());
        return roleList.stream().map(Roles::getName).toList();
    }
}
