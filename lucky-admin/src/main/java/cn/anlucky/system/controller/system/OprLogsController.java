package cn.anlucky.system.controller.system;

import cn.anlucky.system.annotation.Log;
import cn.anlucky.system.base.controller.BaseController;
import cn.anlucky.system.enums.BusinessType;
import cn.anlucky.system.exception.CustomException;
import cn.anlucky.system.page.vo.PageDataVo;
import cn.anlucky.system.pojo.system.OprLogs;
import cn.anlucky.system.service.system.OprLogsService;
import cn.anlucky.vo.R;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


/**
 * 操作日志记录 前端控制器
 *
 * @author yifan.du
 * @since 2024-12-03 14:46:47
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/oprLogs")
public class OprLogsController extends BaseController {

    private final OprLogsService oprLogsService;

    /**
    * 条件分页查询
    * @param oprLogs
    * @return
    */
    @SaCheckPermission("system::logs::oprLogs::list")
    @PostMapping("/pageByParams")
    public R listByPage(@RequestBody OprLogs oprLogs) {
        startPage();
        List<OprLogs> list = oprLogsService.pageByParams(oprLogs);
        PageDataVo tableData = getTableData(list);
        return R.ok(tableData);
    }

    /**
    * 批量删除和删除
    * @param ids
    * @return
    */
    @SaCheckPermission("system::logs::oprLogs::delete")
    @Log(title = "操作日志记录", businessType = BusinessType.DELETE)
    @GetMapping("/delete/{ids}")
    public R deleteByIds(@PathVariable(name = "ids") Serializable[] ids) {
        if (ids.length <= 0){
            throw new CustomException("请选择要删除的数据");
        }
        oprLogsService.removeBatchByIds(Arrays.asList(ids));
        return R.ok("删除成功");
    }

}
