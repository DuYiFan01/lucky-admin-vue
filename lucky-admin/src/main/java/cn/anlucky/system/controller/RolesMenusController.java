package cn.anlucky.system.controller;

import cn.anlucky.system.base.controller.BaseController;

import cn.anlucky.system.service.RolesMenusService;
import cn.anlucky.vo.R;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;


/**
 * 角色和菜单关联表 前端控制器
 *
 * @author yifan.du
 * @since 2024-12-03 14:44:49
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/roles-menus")
public class RolesMenusController extends BaseController {

    private final RolesMenusService rolesMenusService;



}
