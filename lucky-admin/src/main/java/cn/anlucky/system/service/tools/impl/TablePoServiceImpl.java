package cn.anlucky.system.service.tools.impl;

import cn.anlucky.system.gen.MybatisPlusGenerationConfig;
import cn.anlucky.system.mapper.TablePoMapper;
import cn.anlucky.system.pojo.tools.TablePo;
import cn.anlucky.system.service.tools.TablePoService;
import cn.anlucky.system.utils.StringUtils;
import cn.anlucky.system.utils.VelocityUtils;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Service
public class TablePoServiceImpl implements TablePoService {

    @Autowired
    private TablePoMapper tablePoMapper;


    /**
     * 获取所有表信息
     *
     * @return
     */
    @Override
    public List<TablePo> showTableSatus(TablePo tablePo) {
        List<TablePo> tablePos = tablePoMapper.showTableSatus(tablePo);
        for (int i = 0; i < tablePos.size(); i++) {
            // 添加Entity字段，将数据库表名转换为实体名称
            tablePos.get(i).setEntity(StringUtils.convertToCamelCase(tablePos.get(i).getName()));
        }
        return tablePos;
    }

    /**
     * 预览代码
     *
     * @param tableName
     * @return
     */
    @Override
    public Map<String, String> previewCode(String tableName) {
        MybatisPlusGenerationConfig config = new MybatisPlusGenerationConfig();
        return config.previewCode(tableName);
    }

    /**
     * @param tableName
     * @return
     */
    @Override
    public byte[] downloadCode(String tableName) {
        MybatisPlusGenerationConfig config = new MybatisPlusGenerationConfig();
        try {
            return config.downloadCode(tableName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
