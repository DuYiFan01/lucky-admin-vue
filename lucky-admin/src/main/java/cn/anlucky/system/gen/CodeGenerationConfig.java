package cn.anlucky.system.gen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 代码生成配置类
 * 该类用于配置代码生成过程中的各种参数，如数据库连接信息、包名设置以及输出目录等
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeGenerationConfig {

    private static CodeGenerationConfig codeGenerationConfig;

    public static CodeGenerationConfig create() {
        if (codeGenerationConfig == null) {
            codeGenerationConfig = new CodeGenerationConfig();
            codeGenerationConfig.setAuthor("yifan.du");
            codeGenerationConfig.setOutputDir("D:/Desktop/luckyGenerator/2");
            codeGenerationConfig.setPackageName("cn.anlucky");
            codeGenerationConfig.setMouldName("system");
            codeGenerationConfig.setCreateTimeField("create_time");
            codeGenerationConfig.setUpdateTimeField("update_time");
            codeGenerationConfig.setCreateByField("create_by");
            codeGenerationConfig.setUpdateByField("update_by");
            codeGenerationConfig.setDeleteFlagField("del_flag");
            codeGenerationConfig.setEntityPackage("pojo");
            codeGenerationConfig.setMapperPackage("mapper");
            codeGenerationConfig.setMapperXmlPackage("mapper.xml");
            codeGenerationConfig.setServicePackage("service");
            codeGenerationConfig.setServiceImplPackage("service.impl");
            codeGenerationConfig.setControllerPackage("controller");
            codeGenerationConfig.setEntityTemplatePath("/templates/java/entity.java");
            codeGenerationConfig.setMapperTemplatePath("/templates/java/mapper.java");
            codeGenerationConfig.setMapperXmlTemplatePath("/templates/xml/mapper.xml");
            codeGenerationConfig.setServiceTemplatePath("/templates/java/service.java");
            codeGenerationConfig.setServiceImplTemplatePath("/templates/java/serviceImpl.java");
            codeGenerationConfig.setControllerTemplatePath("/templates/java/controller.java");
        }
        return codeGenerationConfig;
    }


    /**
     * 作者
     */
    private String author;
    /**
     * 输出目录
     */
    private String outputDir;
    /**
     * 包名
     */
    private String packageName;
    /**
     * 模板名称
     */
    private String mouldName;
    /**
     * 自动填充创建时间字段名称
     */
    private String createTimeField;
    /**
     * 自动填充修改时间字段名称
     */
    private String updateTimeField;
    /**
     * 自动填充创建人字段名称
     */
    private String createByField;
    /**
     * 自动填充修改人字段名称
     */
    private String updateByField;
    /**
     * 逻辑删除字段名称
     */
    private String deleteFlagField;

    /**
     * 实体类所在包名
     */
    private String entityPackage;
    /**
     * mapper接口所在包名
     */
    private String mapperPackage;
    /**
     * mapper.xml文件所在包名
     */
    private String mapperXmlPackage;
    /**
     * service所在包名
     */
    private String servicePackage;
    /**
     * service实现类所在包名
     */
    private String serviceImplPackage;
    /**
     * controller所在包名
     */
    private String controllerPackage;
    /**
     * 实体模板路径
     */
    private String entityTemplatePath;
    /**
     * mapper模板路径
     */
    private String mapperTemplatePath;
    /**
     * mapper.xml模板路径
     */
    private String mapperXmlTemplatePath;
    /**
     * service模板路径
     */
    private String serviceTemplatePath;
    /**
     * serviceImpl模板路径
     */
    private String serviceImplTemplatePath;
    /**
     * controller模板路径
     */
    private String controllerTemplatePath;


}

