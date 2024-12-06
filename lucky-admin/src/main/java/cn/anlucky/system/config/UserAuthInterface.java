package cn.anlucky.system.config;


import cn.anlucky.system.mapper.UsersMapper;
import cn.dev33.satoken.stp.StpInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class UserAuthInterface implements StpInterface {

    private final UsersMapper usersMapper;

    // todo:https://sa-token.cc/doc.html#/fun/jur-cache
    // todo:权限放入redis中读取
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> permissionList = usersMapper.getPermissionList(loginId.toString());
        return permissionList;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> roleList = usersMapper.getRoleList(loginId.toString());
        return roleList;
    }
}
