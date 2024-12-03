package cn.anlucky.system.controller;

import cn.anlucky.system.annotation.Log;
import cn.anlucky.system.base.controller.BaseController;
import cn.anlucky.system.enums.BusinessType;
import cn.anlucky.system.exception.CustomException;
import cn.anlucky.system.page.vo.PageDataVo;
import cn.anlucky.system.pojo.Roles;
import cn.anlucky.system.service.RolesService;
import cn.anlucky.vo.R;
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
     * 查询所有
     * @return
     */
    @PostMapping("/list")
    public R list() {
        List<Roles> list = rolesService.list();
        return R.ok(list);
    }

    /**
    * 条件分页查询
    * @param roles
    * @return
    */
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
    public R save(@RequestBody Roles roles) {
        if(rolesService.getById(roles.getId())!=null){
            throw new CustomException("ID已存在");
        }
        rolesService.save(roles);
        return R.ok("添加成功");
    }

    /**
    * 修改所有字段为新值
    * @param roles
    * @return
    */
    @Log(title = "角色表", businessType = BusinessType.UPDATE)
    @PostMapping("/updateAllById")
    public R updateAllById(@RequestBody Roles roles) {
        rolesService.updateAllById(roles);
        return R.ok("修改成功");
    }

    /**
    * 修改指定字段为新值
    * @param roles
    * @return
    */
    @Log(title = "角色表", businessType = BusinessType.UPDATE)
    @PostMapping("/updateSpecifyById")
    public R updateSpecifyById(@RequestBody Roles roles) {
        rolesService.updateSpecifyById(roles);
        return R.ok("修改成功");
    }

    /**
    * 批量删除和删除
    * @param ids
    * @return
    */
    @Log(title = "角色表", businessType = BusinessType.DELETE)
    @GetMapping("/delete/{ids}")
    public R deleteByIds(@PathVariable(name = "ids") Serializable[] ids) {
        if (ids.length <= 0){
            throw new CustomException("请选择要删除的数据");
        }
        rolesService.removeBatchByIds(Arrays.asList(ids));
        return R.ok("删除成功");
    }

}
