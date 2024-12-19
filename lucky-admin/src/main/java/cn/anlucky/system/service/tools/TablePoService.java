package cn.anlucky.system.service.tools;

import cn.anlucky.system.pojo.tools.GenPo;
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
     * @param genPo
     * @return
     */
    public Map<String, String> previewCode(GenPo genPo);

    /**
     * 下载代码
     * @param genPo
     * @return
     */
    public byte[] downloadCode(GenPo genPo);


    /**
     * 保存代码生成包名，模块名信息到缓存中
     * @return
     */
    public void saveGenPo(GenPo genPo);

    /**
     * 从缓存中读取当前用户代码生成的包名和模块名，没有则使用默认配置
     * @return
     */
    public GenPo getGenPo();



}
