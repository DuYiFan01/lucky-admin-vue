package cn.anlucky.system.controller;

import cn.anlucky.system.annotation.Log;
import cn.anlucky.system.base.controller.BaseController;
import cn.anlucky.system.enums.BusinessType;
import cn.anlucky.system.exception.CustomException;
import cn.anlucky.system.page.vo.PageDataVo;
import cn.anlucky.system.pojo.UserRoles;
import cn.anlucky.system.service.UserRolesService;
import cn.anlucky.vo.R;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


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
