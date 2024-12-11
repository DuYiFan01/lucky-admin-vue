package cn.anlucky.system.controller.system;

import cn.anlucky.system.pojo.system.Users;
import cn.anlucky.system.service.system.LoginService;
import cn.anlucky.system.utils.Sa;
import cn.anlucky.system.vo.LoginVo;
import cn.anlucky.system.vo.UpdatePasswordVo;
import cn.anlucky.system.vo.UserInfoVo;
import cn.anlucky.vo.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "系统登录")
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
    @Operation(summary = "登录")
    @PostMapping("/login")
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
    @Operation(summary = "注册")
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
    @Operation(summary = "获取用户信息")
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
    @Operation(summary = "修改密码")
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
    @Operation(summary = "退出登录")
    @GetMapping("/logout")
    public R logout() {

        Sa.logout();

        return R.ok("退出登录成功");
    }


}
