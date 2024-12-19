package cn.anlucky.system.service.tools.impl;

import cn.anlucky.system.exception.CustomException;
import cn.anlucky.system.gen.CodeGenerationConfig;
import cn.anlucky.system.gen.MybatisPlusGenerationConfig;
import cn.anlucky.system.mapper.TablePoMapper;
import cn.anlucky.system.pojo.tools.GenPo;
import cn.anlucky.system.pojo.tools.TablePo;
import cn.anlucky.system.service.tools.TablePoService;
import cn.anlucky.system.utils.Sa;
import cn.anlucky.system.utils.StringUtils;
import cn.anlucky.utils.SaTokenDaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;


@Service
public class TablePoServiceImpl implements TablePoService {

    @Autowired
    private TablePoMapper tablePoMapper;
    @Autowired
    private MybatisPlusGenerationConfig generationConfig;


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
     * @param genPo
     * @return
     */
    @Override
    public Map<String, String> previewCode(GenPo genPo) {
        if (genPo == null){
            throw new CustomException("参数不能为空");
        }
        if (StringUtils.isEmpty(genPo.getTableName())){
            throw new CustomException("表名不能为空");
        }
        if (StringUtils.isEmpty(genPo.getPackageName())){
            genPo.setPackageName(CodeGenerationConfig.create().getPackageName());
        }
        if (StringUtils.isEmpty(genPo.getMouldName())){
            genPo.setMouldName(CodeGenerationConfig.create().getMouldName());
        }
        saveGenPo(genPo);
        return generationConfig.previewCode(genPo.getTableName(),genPo.getPackageName(),genPo.getMouldName());
    }

    /**
     * @param genPo
     * @return
     */
    @Override
    public byte[] downloadCode(GenPo genPo) {
        if (genPo == null){
            throw new CustomException("参数不能为空");
        }
        if (StringUtils.isEmpty(genPo.getTableName())){
            throw new CustomException("表名不能为空");
        }
        if (StringUtils.isEmpty(genPo.getPackageName())){
            genPo.setPackageName(CodeGenerationConfig.create().getPackageName());
        }
        if (StringUtils.isEmpty(genPo.getMouldName())){
            genPo.setMouldName(CodeGenerationConfig.create().getMouldName());
        }
        try {
            saveGenPo(genPo);
            return generationConfig.downloadCode(genPo.getTableName(),genPo.getPackageName(),genPo.getMouldName());
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    /**
     * 保存代码生成包名，模块名信息到缓存中
     *
     * @param genPo
     * @return
     */
    @Override
    public void saveGenPo(GenPo genPo) {
        String loginId = Sa.getLoginId();
        SaTokenDaoUtils.setObjectKey(SaTokenDaoUtils.GENERATION_PACKAGE_CACHE+loginId,genPo);
    }

    /**
     * 从缓存中读取当前用户代码生成的包名和模块名，没有则使用默认配置
     *
     * @return
     */
    @Override
    public GenPo getGenPo() {
        String loginId = Sa.getLoginId();
        GenPo genPo = (GenPo) SaTokenDaoUtils.getObjectKey(SaTokenDaoUtils.GENERATION_PACKAGE_CACHE + loginId);
        if (genPo == null){
            // 如果缓存中没有，则取默认配置
            genPo = new GenPo();
            genPo.setPackageName(CodeGenerationConfig.create().getPackageName());
            genPo.setMouldName(CodeGenerationConfig.create().getMouldName());
        }
        return genPo;
    }


}
