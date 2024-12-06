package cn.anlucky.system.service.impl;

import cn.anlucky.system.exception.CustomException;
import cn.anlucky.system.mapper.MenusMapper;
import cn.anlucky.system.mapper.UsersMapper;
import cn.anlucky.system.pojo.Users;
import cn.anlucky.system.service.LoginService;
import cn.anlucky.system.service.MenusService;
import cn.anlucky.system.utils.Sa;
import cn.anlucky.system.vo.LoginVo;
import cn.anlucky.system.vo.RouterVo;
import cn.anlucky.system.vo.UserInfoVo;
import cn.dev33.satoken.stp.SaLoginModel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl extends ServiceImpl<UsersMapper, Users> implements LoginService {

    @Autowired
    private MenusMapper menusMapper;

    @Autowired
    private MenusService menusServiceImpl;

    /**
     *
     * @description 用户登录
     * @param users
     * @return
     */
    @Override
    public LoginVo login(Users users) {


        if (users == null) {
            throw new CustomException("用户名或密码不能为空");
        }
        if (users.getUsername() == null || users.getPassword() == null) {
            throw new CustomException("用户名或密码不能为空");
        }
        if (users.getUsername().trim().equals("") || users.getPassword().trim().equals("")) {
            throw new CustomException("用户名或密码不能为空");
        }


        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Users::getUsername, users.getUsername())
                .eq(Users::getPassword, users.getPassword())
                .last("limit 1");

        users = this.getOne(wrapper);

        if (users != null) {
            SaLoginModel saLoginModel = Sa.setLoginParams("username", users.getUsername());
            Sa.login(users.getId(),saLoginModel);
            return new LoginVo(users.getId(), users.getUsername(), Sa.getToken());
        }
        throw new CustomException("用户名或密码错误");
    }

    /**
     * 用户注册
     *
     * @param users
     * @return
     */
    @Override
    public Users register(Users users) {
        if (users == null) {
            throw new CustomException("用户名或密码不能为空");
        }
        if (users.getUsername() == null || users.getPassword() == null || users.getEmail() == null) {
            throw new CustomException("用户名、密码、邮箱不能为空");
        }
        if (users.getUsername().trim().equals("") || users.getPassword().trim().equals("") || users.getEmail().trim().equals("")) {
            throw new CustomException("用户名、密码、邮箱不能为空");
        }

        // 用户名不能重复
        if (this.getOne(new LambdaQueryWrapper<Users>().eq(Users::getUsername, users.getUsername())) != null) {
            throw new CustomException("用户名已存在");
        }
        this.save(users);
        return users;
    }

    /**
     * 根据用户id获取用户信息
     *
     * @param loginId
     * @return
     */
    @Override
    public UserInfoVo getUserInfo(String loginId) {

        if (loginId == null){
            throw new CustomException("用户id不能为空");
        }

        UserInfoVo userInfoVo = new UserInfoVo();
        // 设置当前用户的按钮权限
        List<String> userPermissionList = this.baseMapper.getPermissionList(loginId);
        userInfoVo.setPermissions(userPermissionList);
        // 设置当前用户的角色列表
        List<String> roleList = this.baseMapper.getRoleList(loginId);
        userInfoVo.setRoles(roleList);

        List<String> userRoleList = this.getUserRoleList(loginId);
        userInfoVo.setRoles(userRoleList);

        userInfoVo.setId(loginId);

        Users users = this.getById(loginId);
        userInfoVo.setUsername(users.getUsername());

        List<RouterVo> routerTree = menusServiceImpl.getRouterTree();
        userInfoVo.setRouters(routerTree);

        return userInfoVo;
    }

    /**
     * 修改密码
     *
     * @param loginId
     * @param oldPassword
     * @param newPassword
     */
    @Override
    public void updatePassword(String loginId, String oldPassword, String newPassword) {

        if (loginId == null || oldPassword == null || newPassword == null){
            throw new CustomException("参数不能为空");
        }

        LambdaUpdateWrapper<Users> wrapper = new LambdaUpdateWrapper<>();
        // 通过ID和旧密码去更新一个用户的密码，若更新成功则该用户输入的旧密码是正确的，否则就是错误的
        wrapper.eq(Users::getId, loginId)
                .eq(Users::getPassword, oldPassword)
                .set(Users::getPassword, newPassword);

        int i = this.baseMapper.update(wrapper);

        if (i == 0) {
            throw new CustomException("旧密码输入错误");
        }
        Sa.logout();
    }

    /**
     * 根据用户id获取用户角色列表
     *
     * @param loginId
     * @return
     */
    @Override
    public List<String> getUserRoleList(String loginId) {
        if (loginId == null){
            throw new CustomException("用户id不能为空");
        }

        return this.baseMapper.getRoleList(loginId);
    }




}
