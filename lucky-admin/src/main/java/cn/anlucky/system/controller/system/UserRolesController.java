package cn.anlucky.system.controller.system;

import cn.anlucky.system.base.controller.BaseController;
import cn.anlucky.system.service.system.UserRolesService;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;


/**
 * 用户角色关联表 前端控制器
 *
 * @author yifan.du
 * @since 2024-12-03 14:39:34
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/user-roles")
public class UserRolesController extends BaseController {

    private final UserRolesService userRolesService;



}
