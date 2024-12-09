package cn.anlucky.system.controller.system;


import cn.anlucky.system.annotation.Log;
import cn.anlucky.system.base.controller.BaseController;
import cn.anlucky.system.enums.BusinessType;
import cn.anlucky.system.exception.CustomException;
import cn.anlucky.system.page.vo.PageDataVo;
import cn.anlucky.system.pojo.system.LoginLog;
import cn.anlucky.system.service.system.LoginLogService;
import cn.anlucky.vo.R;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


/**
 * 用户登录日志 前端控制器
 *
 * @author yifan.du
 * @since 2024-12-03 14:49:27
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/loginLog")
public class LoginLogController extends BaseController {

    private final LoginLogService loginLogService;


    /**
    * 条件分页查询
    * @param loginLog
    * @return
    */
    @SaCheckPermission("system::logs::loginLog::list")
    @PostMapping("/pageByParams")
    public R listByPage(@RequestBody LoginLog loginLog) {
        startPage();
        List<LoginLog> list = loginLogService.pageByParams(loginLog);
        PageDataVo tableData = getTableData(list);
        return R.ok(tableData);
    }

    /**
    * 批量删除和删除
    * @param ids
    * @return
    */
    @SaCheckPermission("system::logs::loginLog::list")
    @Log(title = "用户登录日志", businessType = BusinessType.DELETE)
    @GetMapping("/delete/{ids}")
    public R deleteByIds(@PathVariable(name = "ids") Serializable[] ids) {
        if (ids.length <= 0){
            throw new CustomException("请选择要删除的数据");
        }
        loginLogService.removeBatchByIds(Arrays.asList(ids));
        return R.ok("删除成功");
    }

}
