package cn.anlucky.system.controller.tools;

import cn.anlucky.system.base.controller.BaseController;
import cn.anlucky.system.page.vo.PageDataVo;
import cn.anlucky.system.pojo.tools.TablePo;
import cn.anlucky.system.service.tools.TablePoService;
import cn.anlucky.vo.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tools/gen")
@RequiredArgsConstructor
public class GenController extends BaseController {

    private final TablePoService tablePoService;

    /**
     * 获取所有表信息
     * @return
     */
    @PostMapping("/tableList")
    public R tableList(@RequestBody TablePo tablePo) {
        startPage();
        List<TablePo> list = tablePoService.showTableSatus(tablePo);
        PageDataVo tableData = getTableData(list);
        return R.ok(tableData);
    }

    /**
     * 预览生成的代码
     * @param tableName
     * @return
     */
    @GetMapping("/previewCode/{tableName}")
    public R previewCode(@PathVariable String tableName) {
        return R.ok(tablePoService.previewCode(tableName));
    }


}
