package cn.anlucky.system.controller;

import cn.anlucky.system.pojo.Users;
import cn.anlucky.system.service.LoginService;
import cn.anlucky.system.utils.Sa;
import cn.anlucky.system.vo.LoginVo;
import cn.anlucky.system.vo.UpdatePasswordVo;
import cn.anlucky.system.vo.UserInfoVo;
import cn.anlucky.vo.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class LoginController {


    private final LoginService userLoginService;

    /**
     * 登录
     *
     * @param users
     * @return
     */
    @PostMapping("/login")
    @RequestMapping("/login")
    public R login(@RequestBody Users users) {

        LoginVo userLoginVo = userLoginService.login(users);

        return R.ok("登录成功", userLoginVo);
    }

    /**
     * 注册
     *
     * @param users
     * @return
     */
    @PostMapping("/register")
    public R register(@RequestBody Users users) {

        Users registerUsers = userLoginService.register(users);

        return R.ok("注册成功", registerUsers);
    }


    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping("/info")
    public R getUserInfo() {

        UserInfoVo userInfo = userLoginService.getUserInfo(Sa.getLoginId());

        return R.ok("获取用户信息成功", userInfo);
    }


    /**
     * 修改密码
     *
     * @param updatePasswordVo
     * @return
     */
    @PostMapping("/updatePassword")
    public R updatePassword(@RequestBody UpdatePasswordVo updatePasswordVo) {

        userLoginService.updatePassword(Sa.getLoginId(), updatePasswordVo.getOldPassword(), updatePasswordVo.getNewPassword());

        return R.ok("修改密码成功");
    }

    /**
     * 退出登录
     *
     * @return
     */
    @GetMapping("/logout")
    public R logout() {

        Sa.logout();

        return R.ok("退出登录成功");
    }


}
