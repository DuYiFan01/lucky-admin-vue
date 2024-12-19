package cn.anlucky.system.gen;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 代码生成器
 */
public class MySQLGenerationCode {

    public static void main(String[] args) {
        // 在这里运行代码生成会报错，因为MybatisPlusGenerationConfig是交由SpringBoot 管理的，这里运行之后无法注入数据库连接的DataSource
        // 解决方案：
        // 在Test中运行代码生成器
        MybatisPlusGenerationConfig mybatisPlusGenerationConfig = new MybatisPlusGenerationConfig();
        String tableName = "users";
        String packageName = "cn.anlucky.system";
        String mouldName = "mysql";
        mybatisPlusGenerationConfig.generationCode(tableName, packageName, mouldName);
    }


}
