package cn.anlucky.system.gen;

/**
 * 代码生成器
 */
public class MySQLGenerationCode {
    public static void main(String[] args) {
        // 代码生成器配置
        CodeGenerationConfig codeGenerationConfig = new CodeGenerationConfig();
        // 配置表名
        String tableName = codeGenerationConfig.getTableName(); // 数据库表名称
        // MybatisPlus代码生成器配置
        MybatisPlusGenerationConfig config = new MybatisPlusGenerationConfig();
        // 代码生成
        config.generationCode(tableName);
    }
}
