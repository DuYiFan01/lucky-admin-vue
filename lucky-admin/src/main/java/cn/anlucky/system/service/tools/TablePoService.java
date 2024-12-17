package cn.anlucky.system.service.tools;

import cn.anlucky.system.pojo.tools.TablePo;

import java.util.List;
import java.util.Map;

public interface TablePoService {
    /**
     * 获取所有表信息
     * @return
     */
    public List<TablePo> showTableSatus(TablePo tablePo);


    /**
     * 预览代码
     * @param tableName
     * @return
     */
    public Map<String, String> previewCode(String tableName);


    public byte[] downloadCode(String tableName);




}
