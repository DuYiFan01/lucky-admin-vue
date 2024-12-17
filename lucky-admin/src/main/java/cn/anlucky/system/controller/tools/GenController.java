package cn.anlucky.system.controller.tools;

import cn.anlucky.system.annotation.Log;
import cn.anlucky.system.base.controller.BaseController;
import cn.anlucky.system.enums.BusinessType;
import cn.anlucky.system.page.vo.PageDataVo;
import cn.anlucky.system.pojo.tools.TablePo;
import cn.anlucky.system.service.tools.TablePoService;
import cn.anlucky.vo.R;
import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/tools/gen")
@RequiredArgsConstructor
public class GenController extends BaseController {

    private final TablePoService tablePoService;

    /**
     * 获取所有表信息
     *
     * @return
     */
    @SaCheckPermission("tools::gen::list")
    @PostMapping("/tableList")
    public R tableList(@RequestBody TablePo tablePo) {
        startPage();
        List<TablePo> list = tablePoService.showTableSatus(tablePo);
        PageDataVo tableData = getTableData(list);
        return R.ok(tableData);
    }

    /**
     * 预览生成的代码
     *
     * @param tableName
     * @return
     */
    @SaCheckPermission("tools::gen::list")
    @GetMapping("/previewCode/{tableName}")
    public R previewCode(@PathVariable String tableName) {
        return R.ok(tablePoService.previewCode(tableName));
    }


    /**
     * 下载代码
     * @param response
     * @param tableName
     * @throws IOException
     */
    @SaCheckPermission("tools::gen::list")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/downloadCode/{tableName}")
    public void downloadCode(HttpServletResponse response, @PathVariable String tableName) throws IOException {
        byte[] data = tablePoService.downloadCode(tableName);
        genCode(response, data);
    }

    /**
     * 生成zip文件
     */
    private void genCode(HttpServletResponse response, byte[] data) throws IOException {
        response.reset();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename=\"Lucky.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }

}
