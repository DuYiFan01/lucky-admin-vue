package cn.anlucky.system.service.system;

import cn.anlucky.system.pojo.system.Users;
import cn.anlucky.system.vo.LoginVo;
import cn.anlucky.system.vo.UserInfoVo;

import java.util.List;

public interface LoginService {


    /**
     * 用户登录
     * @param users
     * @return
     */
    public LoginVo login(Users users);


    /**
     * 用户注册
     * @param users
     * @return
     */
    public Users register(Users users);

    /**
     * 根据用户id获取用户信息
     * @param loginId
     * @return
     */
    public UserInfoVo getUserInfo(String loginId);

    /**
     * 修改密码
     * @param loginId
     * @param oldPassword
     * @param newPassword
     */
    public void updatePassword(String loginId, String oldPassword, String newPassword);


    /**
     * 根据用户id获取用户角色列表
     * @param loginId
     * @return
     */
    public List<String> getUserRoleList(String loginId);


}
