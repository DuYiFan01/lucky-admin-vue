package cn.anlucky.system.controller.system;

import cn.anlucky.system.annotation.Log;
import cn.anlucky.system.base.controller.BaseController;
import cn.anlucky.system.enums.BusinessType;
import cn.anlucky.system.exception.CustomException;
import cn.anlucky.system.page.vo.PageDataVo;
import cn.anlucky.system.pojo.system.Roles;
import cn.anlucky.system.service.system.RolesService;
import cn.anlucky.system.vo.AuthRoleVo;
import cn.anlucky.system.vo.AuthUserVo;
import cn.anlucky.system.vo.SaveGrantUserVo;
import cn.anlucky.system.vo.SaveGrantVo;
import cn.anlucky.vo.R;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


/**
 * 角色表 前端控制器
 *
 * @author yifan.du
 * @since 2024-12-03 14:37:12
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/roles")
public class RolesController extends BaseController {

    private final RolesService rolesService;

    /**
    * 条件分页查询
    * @param roles
    * @return
    */
    @SaCheckPermission("system::role::list")
    @PostMapping("/pageByParams")
    public R listByPage(@RequestBody Roles roles) {
        startPage();
        List<Roles> list = rolesService.pageByParams(roles);
        PageDataVo tableData = getTableData(list);
        return R.ok(tableData);
    }

    /**
    * id查询一个
    * @param id
    * @return
    */
    @SaCheckPermission("system::role::list")
    @GetMapping("/get/{id}")
    public R getById(@PathVariable(name = "id") Serializable id) {
        Roles roles = rolesService.getById(id);
        return R.ok(roles);
    }

    /**
    * 新增
    * @param roles
    * @return
    */
    @Log(title = "角色表", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    @SaCheckPermission("system::role::insert")
    public R save(@RequestBody Roles roles) {
        if(rolesService.getById(roles.getId())!=null){
            throw new CustomException("ID已存在");
        }
        rolesService.saveRoleAndRoleMenus(roles);
        return R.ok("添加成功");
    }

    /**
    * 修改所有字段为新值
    * @param roles
    * @return
    */
    @Log(title = "角色表", businessType = BusinessType.UPDATE)
    @PostMapping("/updateAllById")
    @SaCheckPermission("system::role::update")
    public R updateAllById(@RequestBody Roles roles) {
        rolesService.updateAllById(roles);
        return R.ok("修改成功");
    }

    /**
    * 批量删除和删除
    * @param ids
    * @return
    */
    @Log(title = "角色表", businessType = BusinessType.DELETE)
    @GetMapping("/delete/{ids}")
    @SaCheckPermission("system::role::delete")
    public R deleteByIds(@PathVariable(name = "ids") Long[] ids) {
        if (ids.length <= 0){
            throw new CustomException("请选择要删除的数据");
        }
        rolesService.deleteRoelsBatch(Arrays.asList(ids));
        return R.ok("删除成功");
    }

    /**
     * 查询当前角色分配的用户列表
     * @param roleId
     * @return
     */
    @GetMapping("/getGrantByRoleId/{roleId}")
    @SaCheckPermission("system::role::grant")
    public R getGrantByRoleId(@PathVariable(name = "roleId") Long roleId) {
        AuthUserVo authUserVo = rolesService.getUsersByRoleId(roleId);
        return R.ok(authUserVo);
    }

    /**
     * 查询当前用户所拥有的角色
     * @param userId
     * @return
     */
    @SaCheckPermission("system::users::grant")
    @GetMapping("/getGrantByUserId/{userId}")
    public R getGrantByUserId(@PathVariable(name = "userId") Long userId) {
        AuthRoleVo authUserVo = rolesService.getRolesByUserId(userId);
        return R.ok(authUserVo);
    }


    /**
     * 为角色分配用户
     * @param saveGrantVo
     * @return
     */
    @PostMapping("/saveGrant")
    @SaCheckPermission("system::role::grant")
    public R saveGrant(@RequestBody SaveGrantVo saveGrantVo) {
        rolesService.saveGrant(saveGrantVo);
        return R.ok("添加成功");
    }

    /**
     * 为用户分配角色
     * @param saveGrantUserVo
     * @return
     */
    @SaCheckPermission("system::users::grant")
    @PostMapping("/saveGrantByUser")
    public R saveGrantByUser(@RequestBody SaveGrantUserVo saveGrantUserVo) {
        rolesService.saveGrantByUser(saveGrantUserVo);
        return R.ok("添加成功");
    }

}
