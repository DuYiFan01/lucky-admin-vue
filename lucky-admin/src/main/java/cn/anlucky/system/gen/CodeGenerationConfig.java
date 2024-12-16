package cn.anlucky.system.gen;

import cn.anlucky.system.exception.CustomException;
import cn.anlucky.system.utils.PropertiesUtil;
import java.util.Properties;

/*
 * 代码生成配置类
 * 该类用于配置代码生成过程中的各种参数，如数据库连接信息、包名设置以及输出目录等
 */
public class CodeGenerationConfig {
    private static Properties properties;
    public CodeGenerationConfig() {
        properties = PropertiesUtil.getProperties("CodeGeneration.properties");
        if (properties == null){
            throw new CustomException("配置文件CodeGeneration.properties未找到");
        }
    }
    /**
     * 获取作者
     * @return 作者信息
     */
    public String getAuthor() {
        return getPropertyValue("author");
    }

    /**
     * 获取数据库URL
     * @return 数据库URL地址
     */
    public String getDbUrl() {
        return getPropertyValue("mysql_db_url");
    }

    /**
     * 获取数据库用户名
     * @return 数据库用户名
     */
    public String getDbUserName() {
        return getPropertyValue("mysql_db_username");
    }

    /**
     * 获取数据库密码
     * @return 数据库密码
     */
    public String getDbPassword() {
        return getPropertyValue("mysql_db_password");
    }

    /**
     * 获取表名
     * @return 数据库表名
     */
    public String getTableName() {
        return getPropertyValue("table_names");
    }

    /**
     * 获取包名
     * @return Java包名
     */
    public String getPackageName() {
        return getPropertyValue("package_name");
    }

    /**
     * 获取包路径
     * @return Java包路径
     */
    public String getPackagePath() {
        return getPropertyValue("package_path");
    }

    /**
     * 获取输出目录
     * @return 输出目录路径
     */
    public String getOutputDirectory() {
        return getPropertyValue("output_directory");
    }

    /**
     * 获取创建时间字段
     * @return 创建时间字段名
     */
    public String getCreateTimeField() {
        return getPropertyValue("create_time_field");
    }

    /**
     * 获取更新时间字段
     * @return 更新时间字段名
     */
    public String getUpdateTimeField() {
        return getPropertyValue("update_time_field");
    }

    /**
     * 获取创建者字段
     * @return 创建者字段名
     */
    public String getCreateByField() {
        return getPropertyValue("create_by_field");
    }

    /**
     * 获取更新者字段
     * @return 更新者字段名
     */
    public String getUpdateByField() {
        return getPropertyValue("update_by_field");
    }

    /**
     * 获取删除标志字段
     * @return 删除标志字段名
     */
    public String getDeleteFlagField() {
        return getPropertyValue("delete_flag_field");
    }

    private static String getPropertyValue(String key) {
        Object value = properties.get(key);
        return value != null ? value.toString() : "";
    }

}

