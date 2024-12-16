package cn.anlucky.system.gen;

import cn.anlucky.system.base.controller.BaseController;
import cn.anlucky.system.utils.PropertiesUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;

import java.util.Properties;

/**
 * 代码生成器
 *         MySQLGenerationCode  -- 没有经过测试的代码生成器
 *         MySqlGen             -- 经过测试的代码生成器
 */
public class MySqlGen{
    public static void main(String[] args) throws Exception {

        Properties properties = PropertiesUtil.getProperties("CodeGeneration.properties");

        if (properties != null) {
            String author = properties.get("author").toString();
            String dbUrl = properties.get("mysql_db_url").toString();
            String dbUserName = properties.get("mysql_db_username").toString();
            String dbPassword = properties.get("mysql_db_password").toString();
            String tableName = properties.get("table_names").toString();
            String packageName = properties.get("package_name").toString();
            String packagePath = properties.get("package_path").toString();
            String outputDirectory = properties.get("output_directory").toString();
            String createTimeField = properties.get("create_time_field").toString();
            String updateTimeField = properties.get("update_time_field").toString();
            String createByField = properties.get("create_by_field").toString();
            String updateByField = properties.get("update_by_field").toString();
            String deleteFlagField = properties.get("delete_flag_field").toString();

            // 数据库配置
            DataSourceConfig.Builder dataSourceConfig = new DataSourceConfig.Builder(dbUrl, dbUserName, dbPassword)
                    .schema("mybatis-plus") // 数据库 schema(部分数据库适用)
                    .keyWordsHandler(new MySqlKeyWordsHandler()); // 数据库关键字处理器

            FastAutoGenerator.create(dataSourceConfig)
                    .globalConfig(
                            builder -> builder.disableOpenDir() // 允许自动打开输出目录
                                    .outputDir(outputDirectory) // 设置输出目录
                                    .author(author) // 设置作者名
                                    .enableSpringdoc() // 开启 Swagger 模式
                                    .dateType(DateType.TIME_PACK) // 设置时间类型策略
                                    .commentDate("yyyy-MM-dd HH:mm:ss") // 设置注释日期格式)
                                    .build())
                    .packageConfig(
                            builder -> builder.parent(packagePath) // 设置父包名
                                    .moduleName(packageName) // 设置父包模块名
                                    .entity("pojo") // 设置 Entity 包名
                                    .service("service") // 设置 Service 包名
                                    .serviceImpl("service.impl") // 设置 Service Impl 包名
                                    .mapper("mapper") // 设置 Mapper 包名
                                    .xml("mapper") // 设置 Mapper XML 包名
                                    .controller("controller") // 设置 Controller 包名
                                    .build())
                    .strategyConfig(
                            builder -> builder.addInclude(tableName) // 设置要生成的表名
                                    .enableCapitalMode() // 开启大写命名
                                    .enableSkipView() // 开启跳过视图
                                    .disableSqlFilter() // 禁用 SQL 过滤
                                    .entityBuilder()
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
                                    .logicDeleteColumnName("del_flag")
                                    .formatFileName("%s")
                                    .javaTemplate("/templates/java/entity.java")
                                    // .disable() // 禁用实体类生成
                                    .serviceBuilder()
                                    .enableFileOverride()
                                    .superServiceClass(ConstVal.SUPER_SERVICE_CLASS)
                                    .superServiceImplClass(ConstVal.SUPER_SERVICE_IMPL_CLASS)
                                    .formatServiceFileName("%sService")
                                    .formatServiceImplFileName("%sServiceImp")
                                    .serviceTemplate("/templates/java/service.java")
                                    .serviceImplTemplate("/templates/java/serviceImpl.java")
                                    // .disableService() // 禁用 Service 层生成
                                    .mapperBuilder()
                                    .enableFileOverride()
                                    .superClass(ConstVal.SUPER_MAPPER_CLASS)
                                    .enableBaseResultMap()
                                    .enableBaseColumnList()
                                    .formatMapperFileName("%sMapper")
                                    .formatXmlFileName("%sMapper")
                                    .mapperTemplate("/templates/java/mapper.java")
                                    .mapperXmlTemplate("/templates/xml/mapper.xml")
                                    .controllerBuilder()
                                    .superClass(BaseController.class)
                                    .enableFileOverride()
                                    .enableHyphenStyle()
                                    .enableRestStyle()
                                    .formatFileName("%sController")
                                    .template("/templates/java/controller.java")
                                    .build())
                    .injectionConfig(consumer -> {
                        consumer.customFile(
                                new CustomFile.Builder()
                                        .fileName("/index.vue")
                                        .templatePath("/templates/vue/index.vue.vm")
                                        .packageName("vue")
                                .enableFileOverride().build());
                        consumer.customFile(
                                new CustomFile.Builder()
                                        .fileName("/"+tableName+".js")
                                        .templatePath("/templates/js/index.js.vm")
                                        .packageName("js")
                                        .enableFileOverride().build());
                        consumer.customFile(
                                new CustomFile.Builder()
                                        .fileName("/"+tableName+".sql")
                                        .templatePath("/templates/sql/menus.sql.vm")
                                        .packageName("sql")
                                        .enableFileOverride().build());
                    })
                    .execute();
        }
    }
}
