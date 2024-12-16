package cn.anlucky.system.gen;

public class MySQLGenerationCode {
    public static void main(String[] args) {

        CodeGenerationConfig codeGenerationConfig = new CodeGenerationConfig();

        String tableName = codeGenerationConfig.getTableName(); // 数据库表名称

        MybatisPlusGenerationConfig config = new MybatisPlusGenerationConfig();
        // 代码生成
        config.generationCode(tableName);

    }
}
