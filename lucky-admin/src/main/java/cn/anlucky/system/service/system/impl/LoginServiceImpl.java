package cn.anlucky.system.service.system.impl;

import cn.anlucky.system.exception.CustomException;
import cn.anlucky.system.mapper.UsersMapper;
import cn.anlucky.system.pojo.system.LoginLog;
import cn.anlucky.system.pojo.system.Roles;
import cn.anlucky.system.pojo.system.UserRoles;
import cn.anlucky.system.pojo.system.Users;
import cn.anlucky.system.service.system.LoginLogService;
import cn.anlucky.system.service.system.LoginService;
import cn.anlucky.system.service.system.MenusService;
import cn.anlucky.system.service.system.UserRolesService;
import cn.anlucky.system.utils.*;
import cn.anlucky.system.vo.LoginVo;
import cn.anlucky.system.vo.RouterVo;
import cn.anlucky.system.vo.UserInfoVo;
import cn.anlucky.utils.SaTokenDaoUtils;
import cn.dev33.satoken.stp.SaLoginModel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.LoginException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl extends ServiceImpl<UsersMapper, Users> implements LoginService {

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private MenusService menusServiceImpl;

    @Autowired
    private UserRolesService userRolesService;
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

        String username = users.getUsername();
        String ip = IpUtils.getIpAddr();
        String ipAddr = AddressUtils.getRealAddressByIP(ip);
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        // 获取操作系统
        String os = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(username);
        loginLog.setIp(ip);
        loginLog.setIpAddr(ipAddr);
        loginLog.setBrowser(browser);
        loginLog.setOs(os);

        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Users::getUsername, users.getUsername())
                .last("limit 1");
        Users one = this.getOne(wrapper);

        if (one == null){
            throw new CustomException("用户名不存在");
        }
        boolean matches = PasswordEncode.matches(users.getPassword(), one.getPassword());

        if (!matches) {
            throw new CustomException("用户名或密码错误");
        }

        SaLoginModel saLoginModel = Sa.setLoginParams("username", one.getUsername());
        Sa.login(one.getId(),saLoginModel);
        loginLog.setStatus("0");
        loginLog.setMsg("登录成功");
        loginLogService.saveLoginLog(loginLog);
        return new LoginVo(one.getId(), one.getUsername(), Sa.getToken());
    }

    /**
     * 用户注册
     *
     * @param users
     * @return
     */
    @Transactional
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
        String password = PasswordEncode.encode(users.getPassword());
        users.setPassword(password);
        this.save(users);
        // 注册成功添加默认角色
        UserRoles userRoles = new UserRoles();
        userRoles.setUserId(users.getId());
        // 角色ID 为 2 是普通用户的权限，相关角色参考为 user_role 表
        userRoles.setRoleId(2L);
        userRolesService.save(userRoles);
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
        // 设置当前用户的角色列表
        List<Roles> userRoleList = this.getRolesByLoginId(loginId);
        userInfoVo.setRoles(userRoleList.stream().map(Roles::getName).toList());
        // 设置当前用户的按钮权限
        ArrayList<String> userPermissionList = new ArrayList<>();
        userRoleList.stream().forEach(role -> {
            List<String> permissions = this.getPermissionsByRoleId(role.getId());
            userPermissionList.addAll(permissions);
        });
        userInfoVo.setPermissions(userPermissionList);
        userInfoVo.setId(loginId);
        userInfoVo.setUsername(Sa.getLoginUserName());
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

        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        // 通过ID和旧密码去更新一个用户的密码，若更新成功则该用户输入的旧密码是正确的，否则就是错误的
        wrapper.eq(Users::getId, loginId);
        Users users = this.getOne(wrapper);
        if (!PasswordEncode.matches(oldPassword, users.getPassword())){
            throw new CustomException("旧密码输入错误");
        }
        LambdaUpdateWrapper<Users> userUpdate = new LambdaUpdateWrapper<>();
        userUpdate.eq(Users::getId, loginId)
                .set(Users::getPassword, PasswordEncode.encode(newPassword));
        this.baseMapper.update(userUpdate);
        Sa.logout();
    }

    /**
     * 根据用户id获取用户角色名称列表
     *
     * @param loginId
     * @return
     */
    @Override
    public List<Roles> getRolesByLoginId(String loginId) {
        if (loginId == null){
            throw new CustomException("用户id不能为空");
        }
        List<Roles> roleList = (List<Roles>) SaTokenDaoUtils.getObjectKey(SaTokenDaoUtils.ROLES_CACHE + loginId);
        if (roleList == null){
            roleList = this.baseMapper.getRoleList(loginId);
            SaTokenDaoUtils.setObjectKey(SaTokenDaoUtils.ROLES_CACHE + loginId, roleList, SaTokenDaoUtils.DAY_TIMEOUT);
        }
        return roleList;
    }

    /**
     * 根据角色id获取权限列表
     *
     * @param roleId
     * @return
     */
    @Override
    public List<String> getPermissionsByRoleId(Long roleId) {
        if (roleId == null){
            throw new CustomException("角色id不能为空");
        }
        List<String> permissionList = (List<String>) SaTokenDaoUtils.getObjectKey(SaTokenDaoUtils.PERMISSIONS_CACHE + roleId);
        if (permissionList == null){
            permissionList = this.baseMapper.getPermissionList(roleId);
            SaTokenDaoUtils.setObjectKey(SaTokenDaoUtils.PERMISSIONS_CACHE + roleId, permissionList, SaTokenDaoUtils.DAY_TIMEOUT);
        }
        return permissionList;
    }


}
