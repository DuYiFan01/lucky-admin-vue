package cn.anlucky.system.gen;

import cn.anlucky.system.base.controller.BaseController;
import cn.anlucky.system.exception.CustomException;
import cn.anlucky.system.utils.VelocityUtils;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * MybatisPlus代码生成配置
 */
public class MybatisPlusGenerationConfig {
    private static CodeGenerationConfig config;

    public MybatisPlusGenerationConfig() {
        config = new CodeGenerationConfig();
    }

    /**
     * 获取数据源配置
     *
     * @return
     */
    private DataSourceConfig.Builder getDataSourceConfigBuilder() {
        String dbUrl = config.getDbUrl();
        String dbUserName = config.getDbUserName();
        String dbPassword = config.getDbPassword();
        return new DataSourceConfig.Builder(dbUrl, dbUserName, dbPassword)
                .schema("mybatis-plus") // 数据库 schema(部分数据库适用)
                .keyWordsHandler(new MySqlKeyWordsHandler()); // 数据库关键字处理器
    }

    /**
     * 获取全局配置
     *
     * @return
     */
    private GlobalConfig.Builder getGlobalConfigBuilder() {
        String outputDirectory = config.getOutputDirectory();
        String author = config.getAuthor();
        return new GlobalConfig.Builder()
                .disableOpenDir() // 允许自动打开输出目录
                .outputDir(outputDirectory) // 设置输出目录
                .author(author) // 设置作者名
                .enableSpringdoc() // 开启 Swagger 模式
                .dateType(DateType.TIME_PACK) // 设置时间类型策略
                .commentDate("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取包配置
     * @return
     */
    private PackageConfig.Builder getPackageConfigBuilder() {
        String packagePath = config.getPackagePath();
        String packageName = config.getPackageName();
        return new PackageConfig.Builder()
                .parent(packagePath) // 设置父包名
                .moduleName(packageName) // 设置父包模块名
                .entity("pojo") // 设置 Entity 包名
                .service("service") // 设置 Service 包名
                .serviceImpl("service.impl") // 设置 Service Impl 包名
                .mapper("mapper") // 设置 Mapper 包名
                .xml("mapper") // 设置 Mapper XML 包名
                .controller("controller"); // 设置 Controller 包名
    }

    /**
     * 获取策略配置
     * @param tableName
     * @return
     */
    private StrategyConfig getStrategyConfigBuilder(String tableName) {
        String createTimeField = config.getCreateTimeField();
        String updateTimeField = config.getUpdateTimeField();
        String deleteFlagField = config.getDeleteFlagField();
        String createByField = config.getCreateByField();
        String updateByField = config.getUpdateByField();
        return new StrategyConfig.Builder()
                .addInclude(tableName) // 设置要生成的表名
                .enableCapitalMode() // 开启大写命名
                .enableSkipView() // 开启跳过视图
                .disableSqlFilter() // 禁用 SQL 过滤
                .entityBuilder() // 实体策略
                .logicDeleteColumnName(deleteFlagField)
                .enableFileOverride()
                .enableLombok()
                .enableTableFieldAnnotation()
                .naming(NamingStrategy.underline_to_camel)
                .columnNaming(NamingStrategy.underline_to_camel)
                .addTableFills(
                        new Column(createTimeField, FieldFill.INSERT),
                        new Column(updateTimeField, FieldFill.INSERT_UPDATE),
                        new Column(createByField, FieldFill.INSERT),
                        new Column(updateByField, FieldFill.INSERT_UPDATE)
                )
                .formatFileName("%s")
                .javaTemplate("/templates/java/entity.java")
                // .disable() // 禁用实体类生成
                .serviceBuilder() // service 策略
                .enableFileOverride()
                .superServiceClass(ConstVal.SUPER_SERVICE_CLASS)
                .superServiceImplClass(ConstVal.SUPER_SERVICE_IMPL_CLASS)
                .formatServiceFileName("%sService")
                .formatServiceImplFileName("%sServiceImp")
                .serviceTemplate("/templates/java/service.java")
                .serviceImplTemplate("/templates/java/serviceImpl.java")
                // .disableService() // 禁用 Service 层生成
                .mapperBuilder() // mapper 策略
                .enableFileOverride()
                .superClass(ConstVal.SUPER_MAPPER_CLASS)
                .enableBaseResultMap()
                .enableBaseColumnList()
                .formatMapperFileName("%sMapper")
                .formatXmlFileName("%sMapper")
                .mapperTemplate("/templates/java/mapper.java")
                .mapperXmlTemplate("/templates/xml/mapper.xml")
                .controllerBuilder() // controller 策略
                .superClass(BaseController.class)
                .enableFileOverride()
                .enableHyphenStyle()
                .enableRestStyle()
                .formatFileName("%sController")
                .template("/templates/java/controller.java")
                .build();
    }

    /**
     * 获取注入配置
     * @return
     */
    private InjectionConfig.Builder getInjectionConfigBuilder(String tableName) {
        CustomFile vue = new CustomFile.Builder()
                .fileName("/index.vue")
                .templatePath("/templates/vue/index.vue.vm")
                .packageName("vue")
                .enableFileOverride().build();
        CustomFile js = new CustomFile.Builder()
                .fileName("/" + tableName + ".js")
                .templatePath("/templates/js/index.js.vm")
                .packageName("js")
                .enableFileOverride().build();
        CustomFile sql = new CustomFile.Builder()
                .fileName("/" + tableName + ".sql")
                .templatePath("/templates/sql/menus.sql.vm")
                .packageName("sql")
                .enableFileOverride().build();
        List<CustomFile> customFiles = new ArrayList<>();
        customFiles.add(vue);
        customFiles.add(js);
        customFiles.add(sql);
        return new InjectionConfig.Builder()
                .customFile(customFiles)
                .beforeOutputFile((tableInfo, objectMap)->{
                    // 可以在这里添加自定义逻辑，如修改 objectMap 中的配置
                });
    }

    /**
     * 获取模板引擎
     * @return
     */
    private AbstractTemplateEngine getTemplateEngine() {
        return new VelocityTemplateEngine() ;
    }

    /**
     * 获取配置构建器
     * @param tableName
     * @return
     */
    public ConfigBuilder getConfigBuilder(String tableName) {
        ConfigBuilder configBuilder = new ConfigBuilder(
                this.getPackageConfigBuilder().build(),
                this.getDataSourceConfigBuilder().build(),
                this.getStrategyConfigBuilder(tableName),
                null,
                this.getGlobalConfigBuilder().build(),
                this.getInjectionConfigBuilder(tableName).build());
        return configBuilder;
    }



    /**
     * 指定表代码生成
     * @param tableName
     */
    public void generationCode(String tableName) {
        new AutoGenerator(this.getDataSourceConfigBuilder().build())
                .global(this.getGlobalConfigBuilder().build())
                .packageInfo(this.getPackageConfigBuilder().build())
                .strategy(this.getStrategyConfigBuilder(tableName))
                .injection(this.getInjectionConfigBuilder(tableName).build())
                .execute(this.getTemplateEngine());
    }

    /**
     * 预览代码
     * @param tableName
     * @return
     */
    public Map<String,String> previewCode(String tableName) {
        // 获取配置
        ConfigBuilder configBuilder = new ConfigBuilder(
                this.getPackageConfigBuilder().build(),
                this.getDataSourceConfigBuilder().build(),
                this.getStrategyConfigBuilder(tableName),
                null,
                this.getGlobalConfigBuilder().build(),
                this.getInjectionConfigBuilder(tableName).build());
        List<TableInfo> tableInfoList = configBuilder.getTableInfoList();
        TableInfo tableInfo = tableInfoList.get(0);
        Map<String, Object> objectMap = getObjectMap(configBuilder,tableInfo);
        VelocityContext context = new VelocityContext(objectMap);
        VelocityEngine velocityEngine = init(configBuilder);
        List<String> templatePathList = VelocityUtils.getTemplateList();
        // 创建模板文件返回给前端
        Map<String, String> map = new LinkedHashMap<>();
        for (String templatePath : templatePathList) {
            Template template = velocityEngine.getTemplate(templatePath, "UTF-8");
            String name = template.getName();
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            String string = writer.toString();
            map.put(name, string);
        }
        return map;
    }

    public static void main(String[] args)  throws Exception{
        MybatisPlusGenerationConfig generationConfig = new MybatisPlusGenerationConfig();
        // generationConfig.previewCode("users");
        generationConfig.generationCode("users",null);
    }

    public byte[] downloadCode(String tableName) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        generationCode(tableName, zip);
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    public void generationCode(String tableName, ZipOutputStream zip) {
        // 获取配置
        ConfigBuilder configBuilder = new ConfigBuilder(
                this.getPackageConfigBuilder().build(),
                this.getDataSourceConfigBuilder().build(),
                this.getStrategyConfigBuilder(tableName),
                null,
                this.getGlobalConfigBuilder().build(),
                this.getInjectionConfigBuilder(tableName).build());
        String packagePath = configBuilder.getPackageConfig().getParent().replace(".", "/") + "/";
        String moduleName = configBuilder.getPackageConfig().getModuleName();
        List<TableInfo> tableInfoList = configBuilder.getTableInfoList();
        TableInfo tableInfo = tableInfoList.get(0);
        String templateEndType = ".vm"; // 模板引擎模板结尾
        // 实体
        String entityName = tableInfo.getEntityName();
        String entityPath = configBuilder.getPackageConfig().getEntity().replace(".","/");
        String entityTemplatePath = configBuilder.getStrategyConfig().entity().getJavaTemplate() + templateEndType;
        // Mapper
        String mapperName = tableInfo.getMapperName();
        String mapperPath = configBuilder.getPackageConfig().getMapper().replace(".","/");;
        String mapperTemplatePath = configBuilder.getStrategyConfig().mapper().getMapperTemplatePath() + templateEndType;
        // Mapper XML
        String xmlName = tableInfo.getXmlName();
        String xmlPath = configBuilder.getPackageConfig().getXml().replace(".","/");;
        String xmlTemplatePath = configBuilder.getStrategyConfig().mapper().getMapperXmlTemplatePath() + templateEndType;
        // Service
        String serviceName = tableInfo.getServiceName();
        String servicePath =configBuilder.getPackageConfig().getService().replace(".","/");;
        String serviceTemplatePath = configBuilder.getStrategyConfig().service().getServiceTemplate() + templateEndType;
        // Service Impl
        String serviceImplName = tableInfo.getServiceImplName();
        String serviceImplPath = configBuilder.getPackageConfig().getServiceImpl().replace(".","/");;
        String serviceImplTemplatePath = configBuilder.getStrategyConfig().service().getServiceImplTemplate() + templateEndType;
        // controller
        String controllerName = tableInfo.getControllerName();
        String controllerPath = configBuilder.getPackageConfig().getController().replace(".","/");;
        String controllerTemplatePath = configBuilder.getStrategyConfig().controller().getTemplatePath() + templateEndType;

        // 自定义模板
        List<CustomFile> customFiles = configBuilder.getInjectionConfig().getCustomFiles();


        Map<String, Object> objectMap = getObjectMap(configBuilder,tableInfo);
        VelocityContext context = new VelocityContext(objectMap);
        VelocityEngine velocityEngine = init(configBuilder);


        // 添加到zip
        try {
            Template template = velocityEngine.getTemplate(entityTemplatePath, "UTF-8");
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            zip.putNextEntry(new ZipEntry(packagePath + entityPath+ "/"+ entityName + ".java"));
            IOUtils.write(writer.toString(), zip);

            template = velocityEngine.getTemplate(mapperTemplatePath, "UTF-8");
            writer = new StringWriter();
            template.merge(context, writer);
            zip.putNextEntry(new ZipEntry(packagePath + mapperPath+ "/"+ mapperName + ".java"));
            IOUtils.write(writer.toString(), zip);

            template = velocityEngine.getTemplate(xmlTemplatePath, "UTF-8");
            writer = new StringWriter();
            template.merge(context, writer);
            zip.putNextEntry(new ZipEntry(packagePath + xmlPath+ "/"+ xmlName + ".xml"));
            IOUtils.write(writer.toString(), zip);

            template = velocityEngine.getTemplate(serviceTemplatePath, "UTF-8");
            writer = new StringWriter();
            template.merge(context, writer);
            zip.putNextEntry(new ZipEntry(packagePath + servicePath+ "/"+ serviceName + ".java"));
            IOUtils.write(writer.toString(), zip);

            template = velocityEngine.getTemplate(serviceImplTemplatePath, "UTF-8");
            writer = new StringWriter();
            template.merge(context, writer);
            zip.putNextEntry(new ZipEntry(packagePath + serviceImplPath+ "/"+ serviceImplName + ".java"));
            IOUtils.write(writer.toString(), zip);


            template = velocityEngine.getTemplate(controllerTemplatePath, "UTF-8");
            writer = new StringWriter();
            template.merge(context, writer);
            zip.putNextEntry(new ZipEntry(packagePath + controllerPath+ "/"+ controllerName + ".java"));
            IOUtils.write(writer.toString(), zip);

            // 自定义文件
            for (int i = 0; i < customFiles.size(); i++) {
                CustomFile customFile = customFiles.get(i);
                template = velocityEngine.getTemplate(customFile.getTemplatePath(), "UTF-8");
                writer = new StringWriter();
                template.merge(context, writer);
                zip.putNextEntry(new ZipEntry(customFile.getPackageName() +"/" + customFile.getFileName()));
                IOUtils.write(writer.toString(), zip);
            }

            IOUtils.closeQuietly(writer);
            zip.flush();
            zip.closeEntry();
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }

    }





    private @NotNull Map<String, Object> getObjectMap(@NotNull ConfigBuilder config, @NotNull TableInfo tableInfo) {
        StrategyConfig strategyConfig = config.getStrategyConfig();
        Map<String, Object> controllerData = strategyConfig.controller().renderData(tableInfo);
        Map<String, Object> objectMap = new HashMap(controllerData);
        Map<String, Object> mapperData = strategyConfig.mapper().renderData(tableInfo);
        objectMap.putAll(mapperData);
        Map<String, Object> serviceData = strategyConfig.service().renderData(tableInfo);
        objectMap.putAll(serviceData);
        Map<String, Object> entityData = strategyConfig.entity().renderData(tableInfo);
        objectMap.putAll(entityData);
        objectMap.put("config", config);
        objectMap.put("package", config.getPackageConfig().getPackageInfo());
        GlobalConfig globalConfig = config.getGlobalConfig();
        objectMap.put("author", globalConfig.getAuthor());
        objectMap.put("kotlin", globalConfig.isKotlin());
        objectMap.put("swagger", globalConfig.isSwagger());
        objectMap.put("springdoc", globalConfig.isSpringdoc());
        objectMap.put("date", globalConfig.getCommentDate());
        String schemaName = "";
        if (strategyConfig.isEnableSchema()) {
            schemaName = config.getDataSourceConfig().getSchemaName();
            if (StringUtils.isNotBlank(schemaName)) {
                schemaName = schemaName + ".";
                tableInfo.setConvert(true);
            }
        }

        objectMap.put("schemaName", schemaName);
        objectMap.put("table", tableInfo);
        objectMap.put("entity", tableInfo.getEntityName());
        return objectMap;
    }

    public @NotNull VelocityEngine init(@NotNull ConfigBuilder configBuilder) {
            Properties p = new Properties();
            p.setProperty("UTF-8", ConstVal.UTF8);
            p.setProperty("resource.default_encoding", ConstVal.UTF8);
            if (configBuilder.getTemplateLoadWay().isFile()) {
                p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
                p.setProperty("resource.loader.file.path", "");
                p.setProperty("file.resource.loader.unicode", "true");
            } else {
                p.setProperty("resource.loader", TemplateLoadWay.STRING.getValue());
            }
            return new VelocityEngine(p);
    }




}
