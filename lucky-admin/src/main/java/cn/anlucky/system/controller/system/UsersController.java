package cn.anlucky.system.controller.system;


import cn.anlucky.system.annotation.Log;
import cn.anlucky.system.base.controller.BaseController;
import cn.anlucky.system.enums.BusinessType;
import cn.anlucky.system.exception.CustomException;
import cn.anlucky.system.page.vo.PageDataVo;
import cn.anlucky.system.pojo.system.Users;
import cn.anlucky.system.service.system.UsersService;
import cn.anlucky.vo.R;
import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 用户表 前端控制器
 *
 * @author yifan.du
 * @since 2024-12-04 11:23:54
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/users")
public class UsersController extends BaseController {

    private final UsersService usersService;

    /**
     * 查询所有
     * @return
     */
    @SaCheckPermission("system::users::list")
    @PostMapping("/list")
    public R list() {
        List<Users> list = usersService.list();
        return R.ok(list);
    }

    /**
    * 条件分页查询
    * @param users
    * @return
    */
    @SaCheckPermission("system::users::list")
    @PostMapping("/pageByParams")
    public R listByPage(@RequestBody Users users) {
        startPage();
        List<Users> list = usersService.pageByParams(users);
        PageDataVo tableData = getTableData(list);
        return R.ok(tableData);
    }

    /**
    * id查询一个
    * @param id
    * @return
    */
    @SaCheckPermission("system::users::list")
    @GetMapping("/get/{id}")
    public R getById(@PathVariable(name = "id") Serializable id) {
        Users users = usersService.getById(id);
        return R.ok(users);
    }

    /**
    * 新增
    * @param users
    * @return
    */
    @SaCheckPermission("system::users::insert")
    @Log(title = "用户表", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public R save(@RequestBody Users users) {
        if(usersService.getById(users.getId())!=null){
            throw new CustomException("ID已存在");
        }
        usersService.save(users);
        return R.ok("添加成功");
    }

    /**
    * 修改所有字段为新值
    * @param users
    * @return
    */
    @SaCheckPermission("system::users::update")
    @Log(title = "用户表", businessType = BusinessType.UPDATE)
    @PostMapping("/updateAllById")
    public R updateAllById(@RequestBody Users users) {
        usersService.updateAllById(users);
        return R.ok("修改成功");
    }

    /**
    * 修改指定字段为新值
    * @param users
    * @return
    */
    @SaCheckPermission("system::users::update")
    @Log(title = "用户表", businessType = BusinessType.UPDATE)
    @PostMapping("/updateSpecifyById")
    public R updateSpecifyById(@RequestBody Users users) {
        usersService.updateSpecifyById(users);
        return R.ok("修改成功");
    }

    /**
    * 批量删除和删除
    * @param ids
    * @return
    */
    @SaCheckPermission("system::users::delete")
    @Log(title = "用户表", businessType = BusinessType.DELETE)
    @GetMapping("/delete/{ids}")
    public R deleteByIds(@PathVariable(name = "ids") Serializable[] ids) {
        if (ids.length <= 0){
            throw new CustomException("请选择要删除的数据");
        }
        usersService.removeBatchByIds(Arrays.asList(ids));
        return R.ok("删除成功");
    }

}
