package cn.anlucky.system.controller;


import cn.anlucky.system.annotation.Log;
import cn.anlucky.system.base.controller.BaseController;
import cn.anlucky.system.enums.BusinessType;
import cn.anlucky.system.exception.CustomException;
import cn.anlucky.system.page.vo.PageDataVo;
import cn.anlucky.system.pojo.Menus;
import cn.anlucky.system.service.MenusService;
import cn.anlucky.vo.R;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


/**
 * 菜单表 前端控制器
 *
 * @author yifan.du
 * @since 2024-12-03 14:43:01
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/menus")
public class MenusController extends BaseController {

    private final MenusService menusService;

    /**
     * 查询所有
     * @return
     */
    @PostMapping("/list")
    public R list() {
        List<Menus> list = menusService.list();
        return R.ok(list);
    }

    /**
    * 条件分页查询
    * @param menus
    * @return
    */
    @PostMapping("/pageByParams")
    public R listByPage(@RequestBody Menus menus) {
        startPage();
        List<Menus> list = menusService.pageByParams(menus);
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
        Menus menus = menusService.getById(id);
        return R.ok(menus);
    }

    /**
    * 新增
    * @param menus
    * @return
    */
    @Log(title = "菜单表", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public R save(@RequestBody Menus menus) {
        if(menusService.getById(menus.getId())!=null){
            throw new CustomException("ID已存在");
        }
        menusService.save(menus);
        return R.ok("添加成功");
    }

    /**
    * 修改所有字段为新值
    * @param menus
    * @return
    */
    @Log(title = "菜单表", businessType = BusinessType.UPDATE)
    @PostMapping("/updateAllById")
    public R updateAllById(@RequestBody Menus menus) {
        menusService.updateAllById(menus);
        return R.ok("修改成功");
    }

    /**
    * 修改指定字段为新值
    * @param menus
    * @return
    */
    @Log(title = "菜单表", businessType = BusinessType.UPDATE)
    @PostMapping("/updateSpecifyById")
    public R updateSpecifyById(@RequestBody Menus menus) {
        menusService.updateSpecifyById(menus);
        return R.ok("修改成功");
    }

    /**
    * 批量删除和删除
    * @param ids
    * @return
    */
    @Log(title = "菜单表", businessType = BusinessType.DELETE)
    @GetMapping("/delete/{ids}")
    public R deleteByIds(@PathVariable(name = "ids") Serializable[] ids) {
        if (ids.length <= 0){
            throw new CustomException("请选择要删除的数据");
        }
        menusService.removeBatchByIds(Arrays.asList(ids));
        return R.ok("删除成功");
    }

}