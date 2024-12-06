package cn.anlucky.system.mapper;

import cn.anlucky.system.pojo.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 *
 * 用户表 Mapper 接口
 *
 * @author yifan.du
 * @since 2024-12-03 14:16:29
 */
public interface UsersMapper extends BaseMapper<Users> {
    /**
     * 根据登录账号获取权限列表
     * @param loginId
     * @return
     */
    public List<String> getPermissionList(String loginId);

    /**
     * 根据登录账号获取角色列表
     * @param loginId
     * @return
     */
    public List<String> getRoleList(String loginId);

}
